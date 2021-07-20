package com.example.eatoo.src.home.create_group.group_location.current_location

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityCurrentLocationBinding
import com.example.eatoo.reverse_geo.ReverseGeoService
import com.example.eatoo.reverse_geo.ReverseGeoView
import com.example.eatoo.src.home.create_group.CreateGroupActivity
import com.example.eatoo.src.home.create_group.CreateGroupService
import com.example.eatoo.src.home.create_group.model.SearchResultEntity
import com.example.eatoo.src.review.store_map.StoreMapService
import com.example.eatoo.src.review.store_map.model.Document
import com.example.eatoo.src.review.store_map.model.KakaoAddressResponse
import com.example.eatoo.util.getUserIdx
import com.example.googlemapsapiprac.model.LocationLatLngEntity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson

class CurrentLocationActivity
    : BaseActivity<ActivityCurrentLocationBinding>(ActivityCurrentLocationBinding::inflate),
    ReverseGeoView, OnMapReadyCallback, GoogleMap.OnMapClickListener, View.OnClickListener {

    private lateinit var map: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var myLocationListener: CurrentLocationActivity.MyLocationListener
    private lateinit var locationLatLngEntity: LocationLatLngEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setMyLocationListener()
        setupGoogleMap()
        binding.customToolbar.leftIcon.setOnClickListener { finish() }
        binding.btnRegisterGroupLocation.setOnClickListener(this)

    }

    private fun setupGoogleMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.frag_current_location_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(map: GoogleMap) { //구글맵 객체 //아래 내용 없으면 세계지도 나옴.
        this.map = map
        this.map.setOnMapClickListener(this)
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

        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    locationLatLngEntity.latitude.toDouble(),
                    locationLatLngEntity.longitude.toDouble()
                ),
                CreateGroupActivity.CAMERA_ZOOM_LEVEL
            )
        )
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

    private fun setupMarker(title: String): Marker? { //검색한 위도경도

        val positionLatLng = LatLng(
            locationLatLngEntity.latitude.toDouble(),
            locationLatLngEntity.longitude.toDouble()
        )
        val markerOptions = MarkerOptions().apply {
            position(positionLatLng)
            title(title)
        }
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                positionLatLng,
                CreateGroupActivity.CAMERA_ZOOM_LEVEL
            )
        )

        return map.addMarker(markerOptions)
    }

    override fun onGetAddressSuccess(response: KakaoAddressResponse?) {
        dismissLoadingDialog()

        setLocationSummaryLayout(response?.documents?.get(0))


        val currentSelectMarker = setupMarker("내 위치")
        currentSelectMarker?.showInfoWindow()
    }

    private fun setLocationSummaryLayout(response: Document?) {

        binding.clCurrentLocationAddress.isVisible = true
        binding.btnRegisterGroupLocation.isVisible = true

        binding.tvCurrentAddress.text = response?.address?.address_name
        if (response?.road_address != null) {
            binding.tvRoadAddress.text = response.road_address.address_name
        }

    }

    override fun onGetAddressFail(message: String?) {
        dismissLoadingDialog()
    }

    override fun onMapClick(latlng: LatLng) {
        locationLatLngEntity =
            LocationLatLngEntity(latlng.latitude.toFloat(), latlng.longitude.toFloat())
        map.clear()
        val markerOptions = MarkerOptions().apply {
            position(latlng)
        }
        map.addMarker(markerOptions)

        ReverseGeoService(this).tryGetAddress(latlng.latitude, latlng.longitude)
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
}
