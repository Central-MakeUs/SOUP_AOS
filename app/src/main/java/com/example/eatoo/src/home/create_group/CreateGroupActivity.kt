package com.example.eatoo.src.home.create_group

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityCreateGroupBinding
import com.example.eatoo.src.home.create_group.group_location.GroupLocationActivity
import com.example.eatoo.src.home.group.GroupActivity
import com.example.googlemapsapiprac.model.LocationLatLngEntity
import com.example.googlemapsapiprac.model.SearchResultEntity
import com.example.googlemapsapiprac.response.address.AddressInfoResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class CreateGroupActivity :
    BaseActivity<ActivityCreateGroupBinding>(ActivityCreateGroupBinding::inflate),
    OnMapReadyCallback, CreateGroupView {

    private lateinit var map: GoogleMap
    private lateinit var searchResult: SearchResultEntity
    private var currentSelectMarker: Marker? = null
    private lateinit var locationManager: LocationManager
    private lateinit var myLocationListener: MyLocationListener
    private lateinit var locationLatLngEntity: LocationLatLngEntity
    val PERMISSION_REQUEST_CODE = 101
    var mapShowing = false

    companion object {
        val SEARCH_RESULT_EXTRA_KEY: String = "SEARCH_RESULT_EXTRA_KEY"
        val CAMERA_ZOOM_LEVEL = 17f
        const val PERMISSION_REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableMap()
        closeMap()

        searchLocation()
        getCurrentLocation()

        registerGroup()


        val intentResult = intent.getParcelableExtra<SearchResultEntity>(SEARCH_RESULT_EXTRA_KEY)
        if (intentResult != null) { //위치를 검색해서 다시 돌아온 경우
            searchResult = intentResult
            setAddressTv(intentResult.fullAddress)
            mapShowing = true
            setupGoogleMap()
        }

    }

    private fun registerGroup() {
        binding.llMakegroupRegister.setOnClickListener {
            startActivity(Intent(this, GroupActivity::class.java))
        }
    }

    private fun setAddressTv(fullAddress: String) {
        binding.tvSearchLocation.text = fullAddress
    }

    private fun enableMap() {
        setupGoogleMap()
    }

    private fun showMap() {
        binding.llContainerMap.isVisible = false
        mapShowing = true
        binding.btnChangeLocation.isVisible = true
    }

    private fun closeMap() {
        binding.llContainerMap.isVisible = true
        mapShowing = false
        binding.btnChangeLocation.isVisible = false
    }

    private fun searchLocation() {
        binding.tvSearchLocation.setOnClickListener {
            startActivity(Intent(this, GroupLocationActivity::class.java))
        }
        binding.btnChangeLocation.setOnClickListener {
            if(mapShowing) startActivity(Intent(this, GroupLocationActivity::class.java))
        }

    }

    private fun getCurrentLocation() = with(binding) {
        if (!mapShowing) { //첫 방문
            llContainerMap.setOnClickListener {
                requestPermission()
            }
        }
    }

    private fun setupGoogleMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.frag_create_group_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(map: GoogleMap) { //구글맵 객체 //아래 내용 없으면 세계지도 나옴.
        this.map = map
        if (mapShowing) {
            currentSelectMarker = setupMarker(searchResult)
            currentSelectMarker?.showInfoWindow()
            showMap()
        }
    }

    private fun setupMarker(searchResult: SearchResultEntity): Marker? { //검색한 위도경도
        val positionLatLng = LatLng(
            searchResult.locationLatLng.latitude.toDouble(),
            searchResult.locationLatLng.longitude.toDouble()
        )
        val markerOptions = MarkerOptions().apply {
            position(positionLatLng)
            title(searchResult.buildingName)
            snippet(searchResult.fullAddress)
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng,
           CAMERA_ZOOM_LEVEL
        ))

        return map.addMarker(markerOptions)
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
            } else setMyLocationListener()
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
                setMyLocationListener()
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
               CAMERA_ZOOM_LEVEL
            )
        )
        loadReverseGeoInfo(locationLatLngEntity)
        removeLocationListener()
    }

    private fun loadReverseGeoInfo(locationLatLngEntity: LocationLatLngEntity) {
       CreateGroupService(this).tryGetCurrentAddress(
            locationLatLngEntity.latitude.toDouble(),
            locationLatLngEntity.longitude.toDouble()
        )
    }

    private fun removeLocationListener() {
        if (::locationManager.isInitialized && ::myLocationListener.isInitialized) {
            locationManager.removeUpdates(myLocationListener)
        }
    }

    override fun onGetCurrentAddressSuccess(response: AddressInfoResponse) {
        currentSelectMarker = setupMarker(
            SearchResultEntity(
                fullAddress = response.addressInfo.fullAddress ?: "주소정보 없음",
                buildingName = "내 위치",
                locationLatLng = locationLatLngEntity
            )
        )
        setAddressTv(response.addressInfo.fullAddress ?: "주소정보 없음")
        showMap()
        mapShowing = true
        currentSelectMarker?.showInfoWindow()
    }

    override fun onGetCurrentAddressFail(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}