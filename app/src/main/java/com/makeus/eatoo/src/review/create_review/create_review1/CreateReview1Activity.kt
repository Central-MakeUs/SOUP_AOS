package com.makeus.eatoo.src.review.create_review.create_review1

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityCreateReviewBinding
import com.makeus.eatoo.src.home.create_group.CreateGroupActivity
import com.makeus.eatoo.src.review.create_review.create_review2.CreateReview2Activity
import com.makeus.eatoo.src.review.create_review.model.ExistingStoreReviewResponse
import com.makeus.eatoo.src.review.store_location.StoreLocationActivity
import com.makeus.eatoo.src.review.store_map.StoreMapActivity
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.glideUtil
import com.makeus.eatoo.util.roundAll
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CreateReview1Activity :  BaseActivity<ActivityCreateReviewBinding>(ActivityCreateReviewBinding::inflate),
View.OnClickListener, OnMapReadyCallback, RadioGroup.OnCheckedChangeListener, CreateReview1View {

    private var lat : Double = 0.0
    private var lng : Double = 0.0
    private var roadAddress = ""
    private var mapShowing = false

    private lateinit var map: GoogleMap
    private var categoryIdx = 0
    private var storeIdx = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        closeMap()
        setupGoogleMap()
        setViewListeners()
        getIntentExtras()
    }

    private fun getIntentExtras() {
        storeIdx = intent.getIntExtra("storeIdx", -1)

        if(storeIdx != -1) {
            showLoadingDialog(this)
            getStoreInfo(storeIdx)
            binding.ivReview1Img.isVisible = true
            glideUtil(this, intent.getStringExtra("imgUrl")?:"", roundAll(binding.ivReview1Img, 5f))

        }else {
            lat = intent.getDoubleExtra("lat", -1.0)
            lng = intent.getDoubleExtra("lng", -1.0)
            if(lat >0 && lng >0) {
                mapShowing = true
                setupGoogleMap()
            }
        }
        roadAddress = intent.getStringExtra("address").toString()
        if(roadAddress != "null") binding.tvSearchLocation.text = roadAddress
        Log.d("createReviewAct", "roadaddress : $roadAddress, lat : $lat, lng : $lng")

    }

    private fun getStoreInfo(storeIdx : Int) {

        CreateReview1Service(this).tryGetStoreInfo(getUserIdx(), storeIdx)
    }

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
        binding.etStoreName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.ivStoreNameDelete.isVisible = p0.toString().isNotEmpty()
            }
            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.ll_container_review_map -> {
                startActivity(Intent(this, StoreMapActivity::class.java))
                finish()
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

    private fun validityTest() {

        var storeLocation = ""
        if(binding.tvSearchLocation.text == resources.getString(R.string.input_store_location)
            || binding.tvSearchLocation.text.isEmpty()){
            showCustomToast("가게 위치를 입력해주세요")
            return
        }else storeLocation = binding.tvSearchLocation.text.toString()

        var storeName = ""
        if(binding.etStoreName.text.isEmpty()){
            showCustomToast("가게명을 입력해주세요")
            return
        }else storeName = binding.etStoreName.text.toString()

        if(categoryIdx == 0){
            showCustomToast("카테고리를 입력해주세요")
            return
        }

        if( binding.btnCreateReviewNext.background.constantState==ContextCompat.getDrawable(
                this@CreateReview1Activity, R.drawable.login_btn_background)?.constantState) {

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

    private fun setupGoogleMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.frag_create_review_map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
       this.map = map
        if(mapShowing) {
            val markerPosition = LatLng(lat, lng)
            val markerOptions = MarkerOptions().apply {
                position(markerPosition)
            }
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    markerPosition,
                    CreateGroupActivity.CAMERA_ZOOM_LEVEL
            ))
            map.addMarker(markerOptions)?.showInfoWindow()
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

    override fun onGetStoreInfoSuccess(response: ExistingStoreReviewResponse) {
        dismissLoadingDialog()
        binding.etStoreName.setText(response.result.storeName)
        setCategoryIdx(response.result.storeCategoryIdx)
    }

    override fun onGetStoreInfoFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:"통신오류가 발생했습니다.")
    }

    private fun setCategoryIdx(storeCategoryIdx: Int) {
        when(storeCategoryIdx){
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
}