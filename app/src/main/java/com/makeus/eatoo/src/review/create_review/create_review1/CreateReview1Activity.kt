package com.makeus.eatoo.src.review.create_review.create_review1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityCreateReviewBinding
import com.makeus.eatoo.src.home.create_group.model.SearchResultEntity
import com.makeus.eatoo.src.review.create_review.create_review2.CreateReview2Activity
import com.makeus.eatoo.src.review.create_review.model.ExistingStoreReviewResponse
import com.makeus.eatoo.src.review.store_location.StoreLocationActivity
import com.makeus.eatoo.src.review.store_map.StoreMapActivity
import com.makeus.eatoo.src.review.store_map.model.ExistingStoreInfo
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.glideUtil
import com.makeus.eatoo.util.roundAll
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.util.MarkerIcons

class CreateReview1Activity :
    BaseActivity<ActivityCreateReviewBinding>(ActivityCreateReviewBinding::inflate),
    View.OnClickListener, com.naver.maps.map.OnMapReadyCallback, RadioGroup.OnCheckedChangeListener,
    CreateReview1View {


    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private var mapShowing = false

    private lateinit var naverMap: NaverMap
    private var categoryIdx = 0
    private var storeIdx = -1

    override fun onResume() {
        super.onResume()

        getExtras()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        closeMap()
        setupMap()
        setViewListeners()
        registerBr()

    }

    private val reviewBr = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if(p1?.action.equals("finish_review1")) {
                this@CreateReview1Activity.finish()
            }
        }

    }

    private fun registerBr() {
        val filter = IntentFilter("finish_review1")
        LocalBroadcastManager.getInstance(this).registerReceiver(reviewBr, filter)
    }

    private fun unregisterBr() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(reviewBr)
    }

    override fun onDestroy() {
        unregisterBr()
        super.onDestroy()
    }

    /**

    유입 경로 처리

     */

    private fun getExtras() {

        var searchResult: SearchResultEntity? = null
        var reviewInfo: ExistingStoreInfo? = null
        val gson = Gson()

        val jsonFromCurrentLocation =
            ApplicationClass.sSharedPreferences.getString("STORE_MAP_LOCATION", "")
        val jsonFromLocationSearch =
            ApplicationClass.sSharedPreferences.getString("REVIEW_LOCATION_SEARCH", "")

        if (jsonFromCurrentLocation != "" || jsonFromLocationSearch != "") { //위치 또는 지도 들렸다 온 경우.
            if (jsonFromCurrentLocation != "") {
                searchResult =
                    gson.fromJson(jsonFromCurrentLocation, SearchResultEntity::class.java)
            } else if (jsonFromLocationSearch != "") {
                searchResult = gson.fromJson(jsonFromLocationSearch, SearchResultEntity::class.java)
            }
            setSearchLocation(searchResult!!)
        }

        //기존 등록 가게 통해 온 경우.
        val existingReviewInfo =
            ApplicationClass.sSharedPreferences.getString("EXISTING_REVIEW_INFO", "")
        if (existingReviewInfo != "") {
            reviewInfo =
                gson.fromJson(existingReviewInfo, ExistingStoreInfo::class.java)
            setExistingReviewInfo(reviewInfo!!)

        }
    }

    private fun setSearchLocation(searchResult: SearchResultEntity) {

        binding.tvSearchLocation.text = searchResult.fullAddress
        lat = searchResult.locationLatLng.latitude.toDouble()
        lng = searchResult.locationLatLng.longitude.toDouble()
        if (lat > 0 && lng > 0) {
            mapShowing = true
            setupMap()
        }
        ApplicationClass.sSharedPreferences.edit().putString("STORE_MAP_LOCATION", null)
            .apply()
        ApplicationClass.sSharedPreferences.edit().putString("REVIEW_LOCATION_SEARCH", null)
            .apply()
    }

    private fun setExistingReviewInfo(reviewInfo: ExistingStoreInfo) {
        storeIdx = reviewInfo.storeIdx
        getStoreInfo(storeIdx)
        binding.tvSearchLocation.text = reviewInfo.address
        binding.ivReview1Img.isVisible = true
        glideUtil(
            this,
            reviewInfo.imgUrl,
            roundAll(binding.ivReview1Img, 5)
        )
        binding.ivReview1Img.setAlpha(400)
        ApplicationClass.sSharedPreferences.edit().putString("EXISTING_REVIEW_INFO", null)
            .apply()
    }

    private fun getStoreInfo(storeIdx: Int) {
        showLoadingDialog(this)
        CreateReview1Service(this).tryGetStoreInfo(getUserIdx(), storeIdx)
    }

    /**

    view

     */

    private fun setViewListeners() {
        binding.flCreateReview.setOnClickListener(this)
        binding.llContainerReviewMap.setOnClickListener(this)
        binding.rlStoreLocationReview1.setOnClickListener(this)
        binding.btnCreateReviewNext.setOnClickListener(this)
        binding.rlStoreName.setOnClickListener(this)
        binding.rdGroup.setOnCheckedChangeListener(this)
        binding.ivStoreNameDelete.setOnClickListener(this)
        setCancelIcon()
        binding.customToolbar.leftIcon.setOnClickListener { finish() }

    }

    private fun setCancelIcon() {
        binding.etStoreName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.ivStoreNameDelete.isVisible = p0.toString().isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_container_review_map -> {
                startActivity(Intent(this, StoreMapActivity::class.java))
            }
            R.id.btn_create_review_next -> {
                validityTest()
            }
            R.id.rl_store_location_review1 -> {
                startActivity(Intent(this, StoreLocationActivity::class.java))
            }
            R.id.iv_store_name_delete -> binding.etStoreName.text.clear()
        }
    }

    /**

    review 2로 이동

     */

    private fun validityTest() {

        var storeLocation = ""
        if (binding.tvSearchLocation.text == resources.getString(R.string.input_store_location)
            || binding.tvSearchLocation.text.isEmpty()
        ) {
            showCustomToast("가게 위치를 입력해주세요")
            return
        } else storeLocation = binding.tvSearchLocation.text.toString()

        var storeName = ""
        if (binding.etStoreName.text.isEmpty()) {
            showCustomToast("가게명을 입력해주세요")
            return
        } else storeName = binding.etStoreName.text.toString()

        if (categoryIdx == 0) {
            showCustomToast("카테고리를 입력해주세요")
            return
        }

        if (binding.btnCreateReviewNext.background.constantState == ContextCompat.getDrawable(
                this@CreateReview1Activity, R.drawable.login_btn_background
            )?.constantState
        ) {

            val intent = Intent(this, CreateReview2Activity::class.java)
            intent.apply {
                putExtra("address", storeLocation)
                putExtra("lat", lat)
                putExtra("lng", lng)
                putExtra("store_name", storeName)
                putExtra("categoryIdx", categoryIdx)
                putExtra("storeIdx", storeIdx)
            }
            startActivity(intent)
        }
    }

    /**

    지도 보이기

     */

    private fun setupMap() {
        val fm = supportFragmentManager
        val mapFragment =
            fm.findFragmentById(R.id.frag_create_review_map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    fm.beginTransaction().add(R.id.frag_current_location_map, it).commit()
                }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        if (mapShowing) {
            val markerPosition = LatLng(lat, lng)
            val cameraUpdate = CameraUpdate.scrollTo(markerPosition).animate(CameraAnimation.Easing)

            if (::naverMap.isInitialized) naverMap.moveCamera(cameraUpdate)
            else setupMap()

            val marker = Marker()
            marker.apply {
                position = markerPosition
                setMap(naverMap)
                icon = MarkerIcons.BLACK
                iconTintColor = Color.RED
            }
            showMap()
        }
    }

    private fun showMap() {
        binding.llContainerReviewMap.isVisible = false
        binding.ivReview1Img.isVisible = false
        mapShowing = true
    }

    private fun closeMap() {
        binding.llContainerReviewMap.isVisible = true
        binding.ivReview1Img.isVisible = false
        mapShowing = false
    }

    /**

    카테고리 인덱스

     */

    private fun setCategoryIdx(storeCategoryIdx: Int) {
        when (storeCategoryIdx) {
            1 -> binding.rdBtnKo.isChecked = true
            2 -> binding.rdBtnCh.isChecked = true
            3 -> binding.rdBtnJp.isChecked = true
            4 -> binding.rdBtnWestern.isChecked = true
            5 -> binding.rdBtnStreet.isChecked = true
            6 -> binding.rdBtnAsian.isChecked = true
            7 -> binding.rdBtnDesert.isChecked = true
            8 -> binding.rdBtnEtc.isChecked = true
        }
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when (p1) {
            R.id.rd_btn_ko -> categoryIdx = 1
            R.id.rd_btn_ch -> categoryIdx = 2
            R.id.rd_btn_jp -> categoryIdx = 3
            R.id.rd_btn_western -> categoryIdx = 4
            R.id.rd_btn_street -> categoryIdx = 5
            R.id.rd_btn_asian -> categoryIdx = 6
            R.id.rd_btn_desert -> categoryIdx = 7
            R.id.rd_btn_etc -> categoryIdx = 8
        }
        if (binding.etStoreName.text.isNotEmpty() &&
            binding.tvSearchLocation.text != resources.getString(R.string.input_store_location)
            && binding.tvSearchLocation.text.isNotEmpty()
        ) {
            binding.btnCreateReviewNext.background = ContextCompat.getDrawable(
                this@CreateReview1Activity,
                R.drawable.login_btn_background
            )
            binding.btnCreateReviewNext.setTextColor(
                ContextCompat.getColor(
                    this@CreateReview1Activity,
                    R.color.white
                )
            )
        } else {
            binding.btnCreateReviewNext.background = ContextCompat.getDrawable(
                this@CreateReview1Activity,
                R.drawable.background_review_next
            )
        }
    }

    /**

    server result

     */

    override fun onGetStoreInfoSuccess(response: ExistingStoreReviewResponse) {
        dismissLoadingDialog()
        binding.etStoreName.setText(response.result.storeName)
        setCategoryIdx(response.result.storeCategoryIdx)
    }

    override fun onGetStoreInfoFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message ?: resources.getString(R.string.failed_connection))
    }


}