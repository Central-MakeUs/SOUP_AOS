package com.makeus.eatoo.src.review.store_map

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
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
import com.google.gson.Gson
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityStoreMapBinding
import com.makeus.eatoo.reverse_geo.ReverseGeoService
import com.makeus.eatoo.reverse_geo.ReverseGeoView
import com.makeus.eatoo.src.home.create_group.CreateGroupActivity.Companion.PERMISSION_REQUEST_CODE
import com.makeus.eatoo.src.home.create_group.model.SearchResultEntity
import com.makeus.eatoo.src.review.create_review.create_review1.CreateReview1Activity
import com.makeus.eatoo.src.review.store_map.adapter.ExistingStoreRVAdapter
import com.makeus.eatoo.src.review.store_map.dialog.RegisterNewStoreDialog
import com.makeus.eatoo.src.review.store_map.model.*
import com.makeus.eatoo.util.getUserIdx
import com.makeus.googlemapsapiprac.model.LocationLatLngEntity
import com.naver.maps.map.overlay.Marker
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons

class StoreMapActivity : BaseActivity<ActivityStoreMapBinding>(ActivityStoreMapBinding::inflate),
    OnMapReadyCallback, NaverMap.OnMapClickListener, Overlay.OnClickListener, View.OnClickListener,
    RegisterNewStoreDialogInterface,
    StoreMapView, ExistingStoreRVAdapter.OnReviewClickListener, ReverseGeoView {


    private lateinit var storeRegisterDialog: RegisterNewStoreDialog
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    private lateinit var locationManager: LocationManager
    private lateinit var myLocationListener: StoreMapActivity.MyLocationListener
    private lateinit var locationLatLngEntity: LocationLatLngEntity

    private var storeLng: Double = 0.0
    private var storeLat: Double = 0.0
    private lateinit var storeAdapter: ExistingStoreRVAdapter
    private var roadAddress: String? = ""
    private var addedMarkerList = arrayListOf<Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requestPermission()
        setOnClickListeners()

    }

    private fun getAllReviewLocation() {
        StoreMapService(this).tryGetAllStore(getUserIdx())
    }

    private fun setOnClickListeners() {
        binding.btnRegisterNewStore.setOnClickListener(this)
        binding.customToolbar.leftIcon.setOnClickListener { finish() }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_register_new_store -> {
                storeRegisterDialog = RegisterNewStoreDialog(this)
                storeRegisterDialog.show()
            }
        }
    }

    /**
      현재 위치 가져오기

   */

    private fun setupMap() {
        val fm = supportFragmentManager
        val mapFragment =
            fm.findFragmentById(R.id.frag_store_location_map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    fm.beginTransaction().add(R.id.frag_store_location_map, it).commit()
                }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        naverMap.maxZoom = 19.0
        naverMap.minZoom = 7.0
        setMyLocationListener()
        naverMap.onMapClickListener = this

        locationSource = FusedLocationSource(this, PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource

        getAllReviewLocation()
    }


    private fun requestPermission() {
        if (::locationManager.isInitialized.not()) {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsEnabled) {
            if (ContextCompat.checkSelfPermission(
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
            } else setupMap()
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
                setupMap()
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
        val cameraUpdate = CameraUpdate.scrollTo(
            LatLng(
                locationLatLngEntity.latitude.toDouble(),
                locationLatLngEntity.longitude.toDouble()
            )
        ).animate(CameraAnimation.Easing)
        if (::naverMap.isInitialized) naverMap.moveCamera(cameraUpdate)
        else setupMap()

        val marker = Marker()
        marker.apply {
            position = LatLng(
                locationLatLngEntity.latitude.toDouble(),
                locationLatLngEntity.longitude.toDouble()
            )
            map = naverMap
            icon = MarkerIcons.BLACK
            iconTintColor = Color.BLACK
        }
        removeLocationListener()
    }

    private fun removeLocationListener() {
        if (::locationManager.isInitialized && ::myLocationListener.isInitialized) {
            locationManager.removeUpdates(myLocationListener)
        }
    }

    /**
        클릭 이벤트
     */

    override fun onMapClick(p0: PointF, p1: LatLng) {

        addedMarkerList.forEach {
            it.map = null
        }

        val cameraUpdate = CameraUpdate.scrollTo(
            LatLng(p1.latitude, p1.longitude)
        ).animate(CameraAnimation.Easing)

        if (::naverMap.isInitialized) naverMap.moveCamera(cameraUpdate)
        else setupMap()

        val marker = Marker()
        marker.apply {
            position = LatLng(p1.latitude, p1.longitude)
            map = naverMap
            icon = MarkerIcons.BLACK
            iconTintColor = Color.RED
        }

        addedMarkerList.add(marker)

        storeLng = p1.longitude
        storeLat = p1.latitude

        StoreMapService(this).tryGetStore(getUserIdx(), p1.longitude, p1.latitude)
    }

    override fun onClick(p0: Overlay): Boolean {
        val tag : String = p0.tag.toString()
        val latitude = tag.split(",")[0].toDouble()
        val longitude = tag.split(",")[1].toDouble()

        val cameraUpdate = CameraUpdate.scrollTo(
            LatLng(latitude, longitude)
        ).animate(CameraAnimation.Easing)

        if (::naverMap.isInitialized) naverMap.moveCamera(cameraUpdate)
        else setupMap()

        storeLat = latitude
        storeLng = longitude

        StoreMapService(this).tryGetStore(getUserIdx(), storeLng, storeLat)

        return true
    }

   /**

   interface click listeners

    */
    override fun onReviewClicked(item: Store) {

       val searchResult = ExistingStoreInfo(
           imgUrl = item.imgUrl,
           storeIdx =  item.storeIdx,
           address = item.address
       )
       val gson = Gson()
       val jsonStoreLocation = gson.toJson(searchResult)

       ApplicationClass.sSharedPreferences.edit()
           .putString("EXISTING_REVIEW_INFO", jsonStoreLocation).apply()

       finish()

    }


    override fun onRegisterNewStoreConfirm() {

        val searchResult = SearchResultEntity(
            buildingName = "가게",
            fullAddress = roadAddress?:"",
            locationLatLng = LocationLatLngEntity(storeLat.toFloat() , storeLng.toFloat() )
        )
        val gson = Gson()
        val jsonStoreLocation = gson.toJson(searchResult)

        ApplicationClass.sSharedPreferences.edit()
            .putString("STORE_MAP_LOCATION", jsonStoreLocation).apply()

        finish()

    }

    /**

    server result

     */

    override fun onGetAllStoreSuccess(response: AllStoreResponse) {
        if(::naverMap.isInitialized){
            response.result.forEach {
                val marker = Marker()
                marker.apply {
                    position = LatLng(it.latitude, it.longitude)
                    map = naverMap
                    icon = MarkerIcons.BLACK
                    iconTintColor = Color.RED
                    tag ="${position.latitude},${position.longitude}"
                    onClickListener = this@StoreMapActivity
                }
            }
        }else {
            setupMap()
        }

    }

    override fun onGetAllStoreFail(message: String?) {
        showCustomToast(message ?: "통신오류가 발생했습니다.")
    }

    override fun onGetStoreSuccess(response: StoreResponse) {
        //recyclerview 로 등록 스토어 보여주기.
        binding.rvExistingStore.isVisible = true
        binding.btnRegisterNewStore.isVisible = false
        storeAdapter = ExistingStoreRVAdapter(this, response.result, this)
        binding.rvExistingStore.apply {
            adapter = storeAdapter
            layoutManager =
                LinearLayoutManager(this@StoreMapActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onGetStoreFail(message: String?, code: Int) {
        binding.rvExistingStore.isVisible = false
        binding.btnRegisterNewStore.isVisible = true
        if (code == 2010) {
            ReverseGeoService(this).tryGetAddress(storeLat, storeLng)
        }
    }

    override fun onGetAddressSuccess(response: KakaoAddressResponse?) {
        //도로명 주소 변환! 도로명 주소가 없다면 지번 주소로 설정.
      if(response?.meta?.total_count != 0){
          roadAddress = if (response?.documents?.get(0)?.road_address == null) {
              response?.documents?.get(0)?.address?.address_name
          } else {
              response.documents?.get(0).road_address.address_name
          }
      }
    }

    override fun onGetAddressFail(message: String?) {
        startActivity(Intent(this, CreateReview1Activity::class.java))
    }




}