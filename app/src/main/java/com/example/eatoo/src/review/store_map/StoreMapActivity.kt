package com.example.eatoo.src.review.store_map

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityStoreMapBinding
import com.example.eatoo.src.home.create_group.CreateGroupActivity
import com.example.googlemapsapiprac.model.LocationLatLngEntity
import com.example.googlemapsapiprac.model.SearchResultEntity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class StoreMapActivity : BaseActivity<ActivityStoreMapBinding>(ActivityStoreMapBinding::inflate),
    OnMapReadyCallback, GoogleMap.OnMapClickListener {

    val CAMERA_ZOOM_LEVEL = 17f
    val PERMISSION_REQUEST_CODE = 101

    private lateinit var map: GoogleMap
    private var currentSelectMarker: Marker? = null
    private lateinit var locationManager: LocationManager
    private lateinit var myLocationListener: StoreMapActivity.MyLocationListener
    private lateinit var locationLatLngEntity: LocationLatLngEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //현재 위치 맵 띄우기!!!!!!!!!!!!!!
        requestPermission()
    }

    private fun setupGoogleMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.frag_store_location_map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        setMyLocationListener() //나중에는 서버로 대체?
        this.map.setOnMapClickListener(this)
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
//            title( locationLatLngEntity.latitude.toString())
//            snippet(locationLatLngEntity.longitude.toString())
        }
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                positionLatLng,
                CreateGroupActivity.CAMERA_ZOOM_LEVEL
            )
        )

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
        }
        map.clear()
        map.addMarker(markerOptions)
    }


}