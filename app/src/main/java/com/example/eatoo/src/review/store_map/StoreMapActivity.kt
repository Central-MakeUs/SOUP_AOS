package com.example.eatoo.src.review.store_map

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityStoreMapBinding
import com.example.eatoo.src.home.create_group.CreateGroupActivity
import com.example.eatoo.src.home.create_group.CreateGroupActivity.Companion.PERMISSION_REQUEST_CODE
import com.example.eatoo.src.review.create_review.CreateReviewActivity
import com.example.eatoo.src.review.store_map.adapter.ExistingStoreRVAdapter
import com.example.eatoo.src.review.store_map.model.StoreResponse
import com.example.eatoo.util.dialog.RegisterNewStoreDialog
import com.example.eatoo.util.getUserIdx
import com.example.googlemapsapiprac.model.LocationLatLngEntity
import com.example.eatoo.src.home.create_group.model.address.AddressInfoResponse
import com.example.eatoo.src.review.store_map.model.KakaoAddressResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class StoreMapActivity : BaseActivity<ActivityStoreMapBinding>(ActivityStoreMapBinding::inflate),
    OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnMarkerClickListener, View.OnClickListener, RegisterNewStoreDialogInterface , StoreMapView{



    private lateinit var storeRegisterDialog : RegisterNewStoreDialog

    private lateinit var map: GoogleMap
    private var currentSelectMarker: Marker? = null
    private lateinit var locationManager: LocationManager
    private lateinit var myLocationListener: StoreMapActivity.MyLocationListener
    private lateinit var locationLatLngEntity: LocationLatLngEntity

    private var storeLng : Double = 0.0
    private  var storeLat : Double = 0.0
    private lateinit var storeAdapter : ExistingStoreRVAdapter
    private var roadAddress : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //현재 위치 맵 띄우기!!!!!!!!!!!!!!
        requestPermission()
        bindView()
    }

    private fun bindView() {
        binding.btnRegisterNewStore.setOnClickListener(this)
    }

    private fun setupGoogleMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.frag_store_location_map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        showLoadingDialog(this)
        this.map = map

        //테스트
        val markeroptions = MarkerOptions().apply {
            position(LatLng(37.27427291870117, 127.15801239013672))
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
            LatLng(37.27427291870117, 127.15801239013672),
            CreateGroupActivity.CAMERA_ZOOM_LEVEL
        ))
        dismissLoadingDialog()
        map.addMarker(markeroptions)
//        setMyLocationListener() //나중에는 서버로 대체?
        this.map.setOnMapClickListener(this)
        this.map.setOnMarkerClickListener(this)

//        currentSelectMarker = setupMarker(searchResult)
//        currentSelectMarker?.showInfoWindow()
    }

    private fun setupMarker(locationLatLngEntity: LocationLatLngEntity): Marker? { //검색한 위도경도
//        latitude = searchResult.locationLatLng.latitude.toDouble()
//        longitude = searchResult.locationLatLng.longitude.toDouble()

        val positionLatLng = LatLng(
             locationLatLngEntity.latitude.toDouble(),
             locationLatLngEntity.longitude.toDouble()
        )
        val markerOptions = MarkerOptions().apply {
            position(positionLatLng)
            title( locationLatLngEntity.latitude.toString())
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
        showCustomToast("request permission")
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
            } else  setupGoogleMap() //권한 있음.
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
//        loadReverseGeoInfo(locationLatLngEntity)
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
        val markerOptions = MarkerOptions().apply {
            position(latlng)
            title("hello world!")
        }
        map.clear()
        map.addMarker(markerOptions)

        storeLng = latlng.longitude
        storeLat = latlng.latitude

        Log.d("storeMapactivity", "lng : ${latlng.longitude}, lat : ${latlng.latitude}")
        StoreMapService(this).tryGetStore(getUserIdx(), latlng.longitude, latlng.latitude )
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        //클릭확인
        marker.title?.let {
            showCustomToast(it)
        }
        storeLng = marker.position.longitude
        storeLat = marker.position.latitude

        Log.d("storeMapactivity", "lng : $storeLng, lat : $storeLat")

        StoreMapService(this).tryGetStore(getUserIdx(), storeLng, storeLat )

        return false
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_register_new_store -> {
                storeRegisterDialog = RegisterNewStoreDialog(this)
                storeRegisterDialog.show()
            }
        }
    }

    override fun onRegisterNewStoreConfirm() {
        //서버 통신 성공하면 화면이동.
        //code -> 2010 존재 하지 않는 스토어 : 지도를 보여준다.
        //result 가 있으면 recyclerview 로 보여준다.

        //정말로 기존 등록이 없는지 다시 확인.
//        StoreMapService(this).tryGetStore(getUserIdx(), storeLng, storeLat )

        val intent = Intent(this, CreateReviewActivity::class.java)
        intent.apply {
            putExtra("address", roadAddress)
            putExtra("lat", storeLat)
            putExtra("lng", storeLng)
        }
        startActivity(intent)

    }

    override fun onGetStoreSuccess(response: StoreResponse) {
        //recyclerview 로 등록 스토어 보여주기.
        binding.btnRegisterNewStore.isVisible = false
        Log.d("storeMapactivity", response.toString())
        storeAdapter = ExistingStoreRVAdapter(this, response.result)
        binding.rvExistingStore.apply {
            adapter = storeAdapter
            layoutManager = LinearLayoutManager(this@StoreMapActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onGetStoreFail(message: String?, code: Int) {
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        binding.btnRegisterNewStore.isVisible = true
        if(code == 2010){
            StoreMapService(this).tryGetAddress(storeLat, storeLng)
        }
    }

    override fun onGetAddressSuccess(response: KakaoAddressResponse?) {
        //도로명 주소 변환!
        Log.d("storeMapactivity", response.toString())
        roadAddress = response?.documents?.get(0)?.road_address?.address_name
    }

    override fun onGetAddressFail(message: String?) {
        startActivity(Intent(this, CreateReviewActivity::class.java))
    }


}