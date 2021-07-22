package com.makeus.eatoo.src.review.store_map

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityStoreMapBinding
import com.makeus.eatoo.reverse_geo.ReverseGeoService
import com.makeus.eatoo.reverse_geo.ReverseGeoView
import com.makeus.eatoo.src.home.create_group.CreateGroupActivity
import com.makeus.eatoo.src.home.create_group.CreateGroupActivity.Companion.PERMISSION_REQUEST_CODE
import com.makeus.eatoo.src.review.create_review.create_review1.CreateReview1Activity
import com.makeus.eatoo.src.review.store_map.adapter.ExistingStoreRVAdapter
import com.makeus.eatoo.src.review.store_map.model.StoreResponse
import com.makeus.eatoo.src.review.store_map.dialog.RegisterNewStoreDialog
import com.makeus.eatoo.src.review.store_map.model.AllStoreResponse
import com.makeus.eatoo.util.getUserIdx
import com.makeus.googlemapsapiprac.model.LocationLatLngEntity
import com.makeus.eatoo.src.review.store_map.model.KakaoAddressResponse
import com.makeus.eatoo.src.review.store_map.model.Store
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class StoreMapActivity : BaseActivity<ActivityStoreMapBinding>(ActivityStoreMapBinding::inflate),
    OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnMarkerClickListener, View.OnClickListener, RegisterNewStoreDialogInterface,
    StoreMapView, ExistingStoreRVAdapter.OnReviewClickListener, ReverseGeoView {


    private lateinit var storeRegisterDialog: RegisterNewStoreDialog

    private lateinit var map: GoogleMap
    private var currentSelectMarker: Marker? = null
    private lateinit var locationManager: LocationManager
    private lateinit var myLocationListener: StoreMapActivity.MyLocationListener
    private lateinit var locationLatLngEntity: LocationLatLngEntity

    private var storeLng: Double = 0.0
    private var storeLat: Double = 0.0
    private lateinit var storeAdapter: ExistingStoreRVAdapter
    private var roadAddress: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermission()
        bindView()
        getAllReviewLocation()
    }

    private fun getAllReviewLocation() {
        StoreMapService(this).tryGetAllStore(getUserIdx())
    }

    private fun bindView() {
        binding.btnRegisterNewStore.setOnClickListener(this)
        binding.customToolbar.leftIcon.setOnClickListener { finish() }
    }

    private fun setupGoogleMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.frag_store_location_map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        showLoadingDialog(this)
        this.map = map

        setMyLocationListener()
        this.map.setOnMapClickListener(this)
        this.map.setOnMarkerClickListener(this)

    }

    private fun setupMarker(locationLatLngEntity: LocationLatLngEntity): Marker? { //검색한 위도경도

        val positionLatLng = LatLng(
            locationLatLngEntity.latitude.toDouble(),
            locationLatLngEntity.longitude.toDouble()
        )
        val markerOptions = MarkerOptions().apply {
            position(positionLatLng)
            title(locationLatLngEntity.latitude.toString())
            snippet(locationLatLngEntity.longitude.toString())
        }
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                positionLatLng,
                CreateGroupActivity.CAMERA_ZOOM_LEVEL
            )
        )
        dismissLoadingDialog()
        return map.addMarker(markerOptions)
    }

    /*
        현재 위치 가져오기
     */
    private fun requestPermission() {
//        showCustomToast("request permission")
        if (::locationManager.isInitialized.not()) {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsEnabled) {
            if (ContextCompat.checkSelfPermission(  //권한 없는 경우
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    PERMISSION_REQUEST_CODE
                )
            } else setupGoogleMap() //권한 있음.
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                setupGoogleMap()
            } else {
                Toast.makeText(this, "권한을 받지 못 했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setMyLocationListener() {
        val minTime = 1500L //현재 위치를 가져오는데 걸리는 시간 1.5초
        val minDistance = 100f //최소 거리 허용 단위.

        if (::myLocationListener.isInitialized.not()) {
            myLocationListener = MyLocationListener()
        }
        with(locationManager) { //현재 내 위치 가져오기
            requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime, minDistance, myLocationListener
            )
            requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime, minDistance, myLocationListener //여기서 콜백을 받아서 처리한다.
            )
        }
    }

    inner class MyLocationListener : LocationListener { //현재 위치에 대한 콜백을 받는다.
        override fun onLocationChanged(location: Location) {
            locationLatLngEntity = LocationLatLngEntity(
                location.latitude.toFloat(),
                location.longitude.toFloat()
            )
            onCurrentLocationChanged(locationLatLngEntity)
        }

    }

    private fun onCurrentLocationChanged(locationLatLngEntity: LocationLatLngEntity) {
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    locationLatLngEntity.latitude.toDouble(),
                    locationLatLngEntity.longitude.toDouble()
                ),
                CreateGroupActivity.CAMERA_ZOOM_LEVEL
            )
        )
        setupMarker(locationLatLngEntity)
        removeLocationListener()
    }

    private fun removeLocationListener() {
        if (::locationManager.isInitialized && ::myLocationListener.isInitialized) {
            locationManager.removeUpdates(myLocationListener)
        }
    }
    /*
        맵 터치 마커
     */

    override fun onMapClick(latlng: LatLng) {
        val markerOptions = MarkerOptions().position(latlng)

        map.addMarker(markerOptions)

        storeLng = latlng.longitude
        storeLat = latlng.latitude

        StoreMapService(this).tryGetStore(getUserIdx(), latlng.longitude, latlng.latitude)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        storeLng = marker.position.longitude
        storeLat = marker.position.latitude

        StoreMapService(this).tryGetStore(getUserIdx(), storeLng, storeLat)

        return false
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_register_new_store -> {
                storeRegisterDialog = RegisterNewStoreDialog(this)
                storeRegisterDialog.show()
            }
        }
    }

    override fun onRegisterNewStoreConfirm() {

        val intent = Intent(this, CreateReview1Activity::class.java)
        intent.apply {
            putExtra("address", roadAddress)
            putExtra("lat", storeLat)
            putExtra("lng", storeLng)
        }
        startActivity(intent)
        finish()

    }

    override fun onGetStoreSuccess(response: StoreResponse) {
        //recyclerview 로 등록 스토어 보여주기.
        binding.btnRegisterNewStore.isVisible = false
        storeAdapter = ExistingStoreRVAdapter(this, response.result, this)
        binding.rvExistingStore.apply {
            adapter = storeAdapter
            layoutManager =
                LinearLayoutManager(this@StoreMapActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onGetStoreFail(message: String?, code: Int) {
        binding.btnRegisterNewStore.isVisible = true
        if (code == 2010) {
            ReverseGeoService(this).tryGetAddress(storeLat, storeLng)
        }
    }

    override fun onGetAddressSuccess(response: KakaoAddressResponse?) {
        //도로명 주소 변환! 도로명 주소가 없다면 지번 주소로 설정.
        roadAddress = if (response?.documents?.get(0)?.road_address == null) {
            response?.documents?.get(0)?.address?.address_name
        } else {
            response.documents[0].road_address.address_name
        }

    }

    override fun onGetAddressFail(message: String?) {
        startActivity(Intent(this, CreateReview1Activity::class.java))
    }

    override fun onGetAllStoreSuccess(response: AllStoreResponse) {
        response.result.forEach {
            val markerOptions = MarkerOptions().apply {
                position(LatLng(it.latitude, it.longitude))
                title(it.name)
            }
            map.addMarker(markerOptions)
        }
    }

    override fun onGetAllStoreFail(message: String?) {
        showCustomToast(message ?: "통신오류가 발생했습니다.")
    }

    //adapter click listener
    override fun onReviewClicked(item: Store) {
        val intent = Intent(this, CreateReview1Activity::class.java)
        intent.apply {
            putExtra("storeIdx", item.storeIdx)
            putExtra("imgUrl", item.imgUrl)
            putExtra("address", item.address)
        }

        startActivity(intent)
    }


}