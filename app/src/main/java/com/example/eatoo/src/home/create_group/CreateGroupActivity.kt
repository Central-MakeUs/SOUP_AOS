package com.example.eatoo.src.home.create_group

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityCreateGroupBinding
import com.example.eatoo.src.home.create_group.group_location.GroupLocationActivity
import com.example.eatoo.src.home.create_group.group_location.current_location.CurrentLocationActivity
import com.example.eatoo.src.home.create_group.model.CreateGroupRequest
import com.example.eatoo.src.home.create_group.model.CreateGroupResponse
import com.example.eatoo.src.home.create_group.model.Keyword
import com.example.eatoo.src.home.group.GroupActivity
import com.example.eatoo.util.getUserIdx
import com.example.googlemapsapiprac.model.LocationLatLngEntity
import com.example.eatoo.src.home.create_group.model.SearchResultEntity
import com.example.eatoo.util.getUserNickName
import com.google.android.flexbox.FlexboxLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import kotlin.math.roundToInt

class CreateGroupActivity :
    BaseActivity<ActivityCreateGroupBinding>(ActivityCreateGroupBinding::inflate),
    OnMapReadyCallback, CreateGroupView {

    private lateinit var map: GoogleMap
    private lateinit var searchResult: SearchResultEntity
    private var currentSelectMarker: Marker? = null
    private lateinit var locationManager: LocationManager
    private lateinit var locationLatLngEntity: LocationLatLngEntity
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var mapShowing = false

    companion object {
        const val SEARCH_RESULT_EXTRA_KEY: String = "SEARCH_RESULT_EXTRA_KEY"
        const val CAMERA_ZOOM_LEVEL = 17f
        const val PERMISSION_REQUEST_CODE = 101
    }

    override fun onResume() {
        super.onResume()
        getSearchJson()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvUsername.text = getUserNickName()
        enableMap()
        closeMap()
        setKeyword()

        searchLocation()
        getCurrentLocation()

        registerGroup()
        binding.customToolbar.leftIcon.setOnClickListener { finish() }


    }
    private fun getSearchJson() {

        val gson = Gson()
        val jsonFromCurrentLocation =  ApplicationClass.sSharedPreferences.getString("CURRENT_LOCATION", "")
        val jsonFromLocationSearch = ApplicationClass.sSharedPreferences.getString("LOCATION_SEARCH", "")

        if(jsonFromCurrentLocation != "" || jsonFromLocationSearch != "") {
            if(jsonFromCurrentLocation != ""){
                searchResult = gson.fromJson(jsonFromCurrentLocation, SearchResultEntity::class.java)
            } else if(jsonFromLocationSearch != "") {
                searchResult = gson.fromJson(jsonFromLocationSearch, SearchResultEntity::class.java)
            }

            mapShowing = true
            setAddressTv(searchResult.fullAddress)
            setupGoogleMap()
            ApplicationClass.sSharedPreferences.edit().putString("CURRENT_LOCATION", null).apply()
            ApplicationClass.sSharedPreferences.edit().putString("LOCATION_SEARCH", null).apply()
        }


    }

    /*

    keyword chips

     */

    private fun setKeyword() {
        binding.keywordPlusBtn.setOnClickListener {
            binding.keywordPlusBtn.visibility = View.GONE
            binding.etKeyword.visibility = View.VISIBLE
            initKeywordChips()
        }
    }

    private fun initKeywordChips() {
        setKeywordEt()

        binding.etKeyword.setOnKeyListener { v, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                val keywordList = binding.flexboxMakeGroup.getAllChips()
                if (keywordList.size-1 == 4) showCustomToast(resources.getString(R.string.keyword_num_limit))
                else {
                    val et = v as EditText
                    val keyword = et.text.toString()
                    if (keyword.length >= 11) showCustomToast(resources.getString(R.string.keyword_length_limit))
                    else{
                        binding.flexboxMakeGroup.addChip(keyword)
                        et.text.clear()
                    }
                }
            }
            false
        }
    }

    private fun setKeywordEt() {
        binding.etKeyword.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etKeyword, InputMethodManager.SHOW_IMPLICIT)
    }

    @SuppressLint("InflateParams")
    private fun FlexboxLayout.addChip(keyword: String) {
        val chip = LayoutInflater.from(context).inflate(R.layout.view_chip, null) as Chip
        chip.text = keyword
        val layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
        layoutParams.rightMargin = dpToPx(4)
        chip.setOnCloseIconClickListener {
            removeView(chip as View)
        }
        addView(chip, childCount - 1, layoutParams)
    }

    fun Context.dpToPx(dp: Int): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    ).roundToInt()

    private fun FlexboxLayout.getAllChips(): List<Keyword> {
        var keywordList: MutableList<Keyword> = mutableListOf()
        (0 until childCount-1).mapNotNull { index ->
            val childChip = getChildAt(index) as? Chip
            keywordList.add(Keyword(name = childChip?.text.toString()))
        }
        return keywordList
    }

    /*

    get color chips

     */

    private fun getGroupColor(): Int {
        val checkedChip =
            binding.chipgroupMakegroup.findViewById<Chip>(binding.chipgroupMakegroup.checkedChipId).tag

        return checkedChip.toString().toInt()
    }

    /*

    register

     */

    private fun registerGroup() {
        binding.registerGroupBtn.setOnClickListener {

            val keywordList = binding.flexboxMakeGroup.getAllChips()
            val groupColor = getGroupColor()

            val group = CreateGroupRequest(
                name = binding.etGroupName.text.toString(),
                color = groupColor,
                latitude = latitude,
                longitude = longitude,
                keyword = keywordList
            )
            val userIdx = getUserIdx()

            ApplicationClass.sSharedPreferences.edit()
                .putString(ApplicationClass.GROUP_NAME, binding.etGroupName.text.toString()).apply()

            CreateGroupService(this).tryPostGroup(userIdx = userIdx, createGroup = group)

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
            if (mapShowing) startActivity(Intent(this, GroupLocationActivity::class.java))
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
        latitude = searchResult.locationLatLng.latitude.toDouble()
        longitude = searchResult.locationLatLng.longitude.toDouble()

        val positionLatLng = LatLng(latitude, longitude)
        val markerOptions = MarkerOptions().apply {
            position(positionLatLng)
            title(searchResult.buildingName)
            snippet(searchResult.fullAddress)
        }
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                positionLatLng,
                CAMERA_ZOOM_LEVEL
            )
        )

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
            } else startActivity(Intent(this, CurrentLocationActivity::class.java))
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
                startActivity(Intent(this, CurrentLocationActivity::class.java))
            } else {
                Toast.makeText(this, "권한을 받지 못 했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPostGroupSuccess(response: CreateGroupResponse) {
        ApplicationClass.sSharedPreferences.edit()
            .putInt(ApplicationClass.GROUP_IDX, response.result.groupIdx).apply()
        startActivity(Intent(this, GroupActivity::class.java))
    }

    override fun onPostGroupFail(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}