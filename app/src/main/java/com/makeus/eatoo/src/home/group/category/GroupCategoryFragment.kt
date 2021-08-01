package com.makeus.eatoo.src.home.group.category

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
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.databinding.FragmentGroupCategoryBinding
import com.makeus.eatoo.src.home.create_group.CreateGroupActivity
import com.makeus.eatoo.src.home.group.category.category_map.CategoryMapService
import com.makeus.eatoo.src.home.group.category.category_map.CategoryMapView
import com.makeus.eatoo.src.home.group.category.category_map.model.CategoryMapResponse
import com.makeus.eatoo.util.getUserIdx
import com.makeus.googlemapsapiprac.model.LocationLatLngEntity
import com.naver.maps.map.overlay.Marker
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.google.android.gms.maps.model.MarkerOptions
import com.makeus.eatoo.like.LikeService
import com.makeus.eatoo.like.LikeView
import com.makeus.eatoo.src.home.group.GroupActivity
import com.makeus.eatoo.src.home.group.category.category_detail.CategoryStoreDetailActivity
import com.makeus.eatoo.src.home.group.category.category_map.OnListClickListener
import com.makeus.eatoo.src.home.group.category.category_map.adapter.CategoryStoreRVAdapter
import com.makeus.eatoo.src.home.group.category.category_map.model.CategoryMapStoreInfo
import com.makeus.eatoo.src.home.group.category.dialog.StoreToMateSuggestDialog
import com.makeus.eatoo.src.home.group.category.dialog.StoreToMateSuggestDialogInterface
import com.makeus.eatoo.src.home.group.groupmatesuggestion.MateSuggestionActivity
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
//import net.daum.mf.map.api.MapView


class GroupCategoryFragment(val listener: OnListClickListener) :
    BaseFragment<FragmentGroupCategoryBinding>(
        FragmentGroupCategoryBinding::bind,
        R.layout.fragment_group_category
    ),
    CategoryMapView, View.OnClickListener, OnMapReadyCallback, Overlay.OnClickListener,
    CategoryStoreRVAdapter.OnStoreClickListener, LikeView,
    StoreToMateSuggestDialogInterface {


    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    private lateinit var locationManager: LocationManager
    private lateinit var myLocationListener: GroupCategoryFragment.MyLocationListener
    private lateinit var locationLatLngEntity: LocationLatLngEntity

    private lateinit var storeReviewList: ArrayList<CategoryMapStoreInfo>
    private lateinit var storeRVAdapter: CategoryStoreRVAdapter

    private var storeMarkerList = arrayListOf<Marker>()


    override fun onResume() {
        super.onResume()

        requestPermission()
    }

    override fun onStop() {
        super.onStop()
        Log.d("groupcategory", "onstop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("groupCategory", "ondestroy")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val kakaoMap = MapView(activity)

        setOnClickListeners()
//        binding.rlMapView.addView(kakaoMap)
    }

    private fun setOnClickListeners() {
        binding.llCategoryList.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ll_category_list -> {
                listener.onListClick()
            }
        }
    }

    private fun getCategoryMapStore() {
        CategoryMapService(this).tryGetCategoryMapStore(getUserIdx())

    }

    /*

    현재 위치 권한 요청

     */

    private fun requestPermission() {
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
                    activity as GroupActivity,
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    CreateGroupActivity.PERMISSION_REQUEST_CODE
                )
            } else setUpMap() //권한 있음.
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
                setUpMap()
            } else {
                Toast.makeText(requireContext(), "권한을 받지 못 했습니다.", Toast.LENGTH_SHORT).show()
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
        else setUpMap()

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

    /*

    지도 보이기

     */

    private fun setUpMap() {

        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.frag_group_category) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.frag_group_category, it).commit()
            }
        mapFragment?.getMapAsync(this)



    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        naverMap.maxZoom = 19.0
        naverMap.minZoom = 10.0
        setMyLocationListener()

//        val uiSetting = naverMap.uiSettings
//        uiSetting.isLocationButtonEnabled = true


        locationSource = FusedLocationSource(this, CreateGroupActivity.PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource

        getCategoryMapStore()

    }

    /*

    클릭 이벤트

     */

    override fun onClick(p0: Overlay): Boolean {
        val storeName = p0.tag.toString().split(",")[2]
        val storeList = storeReviewList.filter { it.name == storeName }
        storeRVAdapter = CategoryStoreRVAdapter(requireContext(), storeList, this)
        binding.rvGroupCategory.apply {
            adapter = storeRVAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        storeRVAdapter.notifyDataSetChanged()
        return false
    }

    /*

    interface

     */

    override fun onStoreClicked(storeIdx: Int) {
        val intent = Intent(requireContext(), CategoryStoreDetailActivity::class.java)
        intent.apply {
            putExtra("storeIdx", storeIdx)
        }
        startActivity(intent)

    }

    override fun onStoreLongClicked(storeName: String, storeImg: String) {
        val dialog = StoreToMateSuggestDialog(requireContext(), this, storeName, storeImg)
        dialog.show()
    }

    override fun onUnLikeClicked(storeIdx: Int) {
        LikeService(this).tryPatchLike(getUserIdx(), storeIdx)
    }

    override fun onLikeClicked(storeIdx: Int) {
        LikeService(this).tryPostLike(getUserIdx(), storeIdx)
    }

    override fun onGotoMateSuggestClicked(storeName: String, storeImg: String) {
        val intent = Intent(requireContext(), MateSuggestionActivity::class.java)
        intent.apply {
            putExtra("storeName", storeName)
            putExtra("storeImg", storeImg)
        }
        startActivity(intent)
    }


    /*

    server result

     */

    override fun onGetCategoryMapStoreSuccess(response: CategoryMapResponse) {
        Log.d("groupCategoryFrag", response.toString())

        if (::naverMap.isInitialized) {
            storeReviewList = response.result.getStoresRes
            if(storeMarkerList.isNotEmpty()) storeMarkerList.forEach { it.map = null }
            response.result.getStoresRes.forEach {
                val marker = Marker()
                marker.apply {
                    position = LatLng(it.latitude, it.longitude)
                    map = naverMap
                    icon = MarkerIcons.BLACK
                    iconTintColor = Color.RED
                    tag = "${position.latitude},${position.longitude},${it.name}"
                    onClickListener = this@GroupCategoryFragment
                }
                storeMarkerList.add(marker)
            }
        } else setUpMap()

    }

    override fun onGetCategoryMapStoreFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message ?: resources.getString(R.string.failed_connection))
    }

    override fun onPostLikeSuccess() {
        getCategoryMapStore()
    }

    override fun onPostLikeFail(message: String?) {
        showCustomToast(message ?: resources.getString(R.string.failed_connection))
    }

    override fun onPatchLikeSuccess() {
        getCategoryMapStore()
    }

    override fun onPatchLikeFail(message: String?) {
        showCustomToast(message ?: resources.getString(R.string.failed_connection))
    }


}