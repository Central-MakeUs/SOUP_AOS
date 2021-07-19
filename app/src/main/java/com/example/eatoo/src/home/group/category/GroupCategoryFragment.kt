package com.example.eatoo.src.home.group.category

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.eatoo.R
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentGroupCategoryBinding
import com.example.eatoo.src.home.create_group.CreateGroupActivity
import com.example.eatoo.src.home.group.category.category_list.GroupCategoryListFragment
import com.example.eatoo.src.home.group.category.category_map.CategoryMapService
import com.example.eatoo.src.home.group.category.category_map.CategoryMapView
import com.example.eatoo.src.home.group.category.category_map.model.CategoryMapResponse
import com.example.eatoo.src.main.MainActivity
import com.example.eatoo.util.getUserIdx
import com.example.googlemapsapiprac.model.LocationLatLngEntity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class GroupCategoryFragment
    : BaseFragment<FragmentGroupCategoryBinding>(
    FragmentGroupCategoryBinding::bind,
    R.layout.fragment_group_category
),
CategoryMapView, View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private lateinit var map: GoogleMap
    private lateinit var locationManager: LocationManager //내 위치 가져오기
    private lateinit var myLocationListener: GroupCategoryFragment.MyLocationListener
    private lateinit var locationLatLngEntity: LocationLatLngEntity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermission()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.llCategoryList.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.ll_category_list -> {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .add(R.id.nav_host, GroupCategoryListFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }
    }

    private fun getCategoryMapStore() {
        context?.let {
            showLoadingDialog(it)
            CategoryMapService(this).tryGetCategoryMapStore(getUserIdx())
        }

    }

    ///////permission

    private fun requestPermission() {
        showCustomToast("request permission")
            if (::locationManager.isInitialized.not()) {
                locationManager =
                    activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            }
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (isGpsEnabled) {
                if (ContextCompat.checkSelfPermission(  //권한 없는 경우
                        requireContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        activity as MainActivity,
                        arrayOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        CreateGroupActivity.PERMISSION_REQUEST_CODE
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
            if (requestCode == CreateGroupActivity.PERMISSION_REQUEST_CODE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    setupGoogleMap()
                } else {
                    Toast.makeText(requireContext(), "권한을 받지 못 했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    ///////google map
    private fun setupGoogleMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.frag_group_category) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {

            showLoadingDialog(requireContext())
            map = p0
            setMyLocationListener() //내 위치로 이동.
            map.setOnMarkerClickListener(this)

    }

    private fun setupCurrentMarker(locationLatLngEntity: LocationLatLngEntity) { //검색한 위도경도

        val positionLatLng = LatLng(
            locationLatLngEntity.latitude.toDouble(),
            locationLatLngEntity.longitude.toDouble()
        )
        val markerOptions = MarkerOptions().apply {
            position(positionLatLng)
            title("현 위치")
            snippet(locationLatLngEntity.longitude.toString()) //////////재설정 필요
        }
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                positionLatLng,
                CreateGroupActivity.CAMERA_ZOOM_LEVEL
            )
        )
        dismissLoadingDialog()
        map.addMarker(markerOptions)
        getCategoryMapStore()
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        p0.title?.let {
            showCustomToast(it)
        }
        //adapter? 필요.
        return false
    }

    //////current location
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
        setupCurrentMarker(locationLatLngEntity)
        removeLocationListener()
    }

    private fun removeLocationListener() {
        if (::locationManager.isInitialized && ::myLocationListener.isInitialized) {
            locationManager.removeUpdates(myLocationListener)
        }
    }



    ///////server result

    override fun onGetCategoryMapStoreSuccess(response: CategoryMapResponse) {
        dismissLoadingDialog()
        //add marker.
        response.result.getStoresRes.forEach {
            val markerOptions = MarkerOptions().apply {
                position(LatLng(it.latitude, it.longitude))
                title(it.name)
                snippet(it.address)
            }
            map.addMarker(markerOptions)
        }

    }

    override fun onGetCategoryMapStoreFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message ?: resources.getString(R.string.failed_connection))
    }



}