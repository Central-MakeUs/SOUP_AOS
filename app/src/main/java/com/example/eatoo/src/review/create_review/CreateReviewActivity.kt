package com.example.eatoo.src.review.create_review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import androidx.core.view.isVisible
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityCreateReviewBinding
import com.example.eatoo.databinding.ActivityMyReviewBinding
import com.example.eatoo.src.home.create_group.CreateGroupActivity
import com.example.eatoo.src.review.store_location.StoreLocationActivity
import com.example.eatoo.src.review.store_map.StoreMapActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CreateReviewActivity :  BaseActivity<ActivityCreateReviewBinding>(ActivityCreateReviewBinding::inflate),
View.OnClickListener, OnMapReadyCallback, RadioGroup.OnCheckedChangeListener {

    private var lat : Double = 0.0
    private var lng : Double = 0.0
    private var roadAddress = ""
    private var mapShowing = false

    private lateinit var map: GoogleMap
    private var categoryIdx = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        closeMap()
        setupGoogleMap()
        setOnClickListeners()
        getIntentExtras()
    }

    private fun getIntentExtras() {
        roadAddress = intent.getStringExtra("address").toString()
        lat = intent.getDoubleExtra("lat", -1.0)
        lng = intent.getDoubleExtra("lng", -1.0)
        if(roadAddress != "null") binding.tvSearchLocation.text = roadAddress

        if(lat >0 && lng >0) {
            mapShowing = true
            setupGoogleMap()
        }

    }

    private fun setOnClickListeners() {
        binding.flCreateReview.setOnClickListener(this)
        binding.llContainerReviewMap.setOnClickListener(this)
        binding.rlStoreLocationReview1.setOnClickListener(this)
        binding.btnCreateReviewNext.setOnClickListener(this)
        binding.rlStoreName.setOnClickListener(this)
        binding.rdGroup.setOnCheckedChangeListener(this)
        binding.ivStoreNameDelete.setOnClickListener(this)
        setCancelIcon()
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
            }
            R.id.btn_create_review_next -> {
                val intent = Intent(this, CreateReview2Activity::class.java)
                intent.apply {
                    putExtra("road_address", roadAddress)
                    putExtra("lat", lat)
                    putExtra("lng", lng)
                    putExtra("store_name", binding.etStoreName.text.toString())
                    putExtra("categoryIdx", categoryIdx)
                }
                startActivity(intent)
            }
            R.id.rl_store_location_review1 -> {
                startActivity(Intent(this, StoreLocationActivity::class.java))
            }
            R.id.iv_store_name_delete -> binding.etStoreName.text.clear()
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
        mapShowing = true
    }

    private fun closeMap() {
        binding.llContainerReviewMap.isVisible = true
        mapShowing = false
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            R.id.rd_btn_ko -> categoryIdx = 1
            R.id.rd_btn_ch -> categoryIdx = 2
            R.id.rd_btn_jp -> categoryIdx = 3
            R.id.rd_btn_western -> categoryIdx = 4
            R.id.rd_btn_street -> categoryIdx = 5
            R.id.rd_btn_asian -> categoryIdx = 6
            R.id.rd_btn_desert -> categoryIdx = 7
            R.id.rd_btn_etc -> categoryIdx = 8

        }
    }
}