package com.makeus.eatoo.src.home.create_group.group_location.current_location

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityCurrentLocationBinding
import com.makeus.eatoo.reverse_geo.ReverseGeoService
import com.makeus.eatoo.reverse_geo.ReverseGeoView
import com.naver.maps.map.overlay.Marker
import com.makeus.eatoo.src.home.create_group.model.SearchResultEntity
import com.makeus.eatoo.src.review.store_map.model.Document
import com.makeus.eatoo.src.review.store_map.model.KakaoAddressResponse
import com.makeus.googlemapsapiprac.model.LocationLatLngEntity
import com.google.gson.Gson
import com.makeus.eatoo.src.home.create_group.CreateGroupActivity.Companion.PERMISSION_REQUEST_CODE
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons

class CurrentLocationActivity
    : BaseActivity<ActivityCurrentLocationBinding>(ActivityCurrentLocationBinding::inflate),
    ReverseGeoView, OnMapReadyCallback, View.OnClickListener {

    private lateinit var naverMap : NaverMap
    private lateinit var locationSource: FusedLocationSource

    private lateinit var locationManager: LocationManager
    private lateinit var myLocationListener: CurrentLocationActivity.MyLocationListener
    private lateinit var locationLatLngEntity: LocationLatLngEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupMap()
        setMyLocationListener()

        binding.customToolbar.leftIcon.setOnClickListener { finish() }
        binding.btnRegisterGroupLocation.setOnClickListener(this)

    }

    private fun setupMap() {
        val fm = supportFragmentManager
        val mapFragment =
            fm.findFragmentById(R.id.frag_current_location_map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    fm.beginTransaction().add(R.id.frag_current_location_map, it).commit()
                }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        naverMap.maxZoom = 19.0
        naverMap.minZoom = 10.0

        locationSource = FusedLocationSource(this, PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource

    }

    @SuppressLint("MissingPermission")
    private fun setMyLocationListener() {
        val minTime = 1500L //현재 위치를 가져오는데 걸리는 시간 1.5초
        val minDistance = 100f //최소 거리 허용 단위.

        if (::myLocationListener.isInitialized.not()) {
            myLocationListener = MyLocationListener()
        }
        if (::locationManager.isInitialized.not()) {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        with(locationManager) { //현재 내 위치 가져오기
            requestLocationUpdates(
                android.location.LocationManager.GPS_PROVIDER,
                minTime, minDistance, myLocationListener
            )
            requestLocationUpdates(
                android.location.LocationManager.NETWORK_PROVIDER,
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
        if(::naverMap.isInitialized) naverMap.moveCamera(cameraUpdate)
        else setupMap()

        val marker = Marker()
        marker.apply {  //태그 달 수 있음.
            position = com.naver.maps.geometry.LatLng(
                locationLatLngEntity.latitude.toDouble(),
                locationLatLngEntity.longitude.toDouble()
            )
            map = naverMap
            icon = MarkerIcons.BLACK
            iconTintColor = Color.BLACK
        }

        loadReverseGeoInfo(
            locationLatLngEntity.latitude.toDouble(),
            locationLatLngEntity.longitude.toDouble()
        )
        removeLocationListener()
    }

    private fun loadReverseGeoInfo(lat: Double, lng: Double) {
        showLoadingDialog(this)
        ReverseGeoService(this).tryGetAddress(lat, lng)
    }

    private fun removeLocationListener() {
        if (::locationManager.isInitialized && ::myLocationListener.isInitialized) {
            locationManager.removeUpdates(myLocationListener)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_register_group_location -> goBackToCreateGroup()
        }
    }

    private fun goBackToCreateGroup() {
        val searchResult = SearchResultEntity(
            buildingName = "현 위치",
            fullAddress = if (binding.tvRoadAddress.text.isEmpty()) binding.tvCurrentAddress.text.toString() else binding.tvRoadAddress.text.toString(),
            locationLatLng = locationLatLngEntity
        )
        val gson = Gson()
        val jsonCurrentLocation = gson.toJson(searchResult)

        ApplicationClass.sSharedPreferences.edit()
            .putString("CURRENT_LOCATION", jsonCurrentLocation).apply()

        finish()
    }

    private fun setLocationSummaryLayout(response: Document?) {

        binding.clCurrentLocationAddress.isVisible = true
        binding.btnRegisterGroupLocation.isVisible = true

        binding.tvCurrentAddress.text = response?.address?.address_name
        if (response?.road_address != null) {
            binding.tvRoadAddress.text = response.road_address.address_name
        }

    }


    //////////server result

    override fun onGetAddressSuccess(response: KakaoAddressResponse?) {
        dismissLoadingDialog()
        setLocationSummaryLayout(response?.documents?.get(0))
    }

    override fun onGetAddressFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }


}
