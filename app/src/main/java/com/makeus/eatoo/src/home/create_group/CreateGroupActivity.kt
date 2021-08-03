package com.makeus.eatoo.src.home.create_group

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityCreateGroupBinding
import com.makeus.eatoo.src.home.create_group.group_location.GroupLocationActivity
import com.makeus.eatoo.src.home.create_group.group_location.current_location.CurrentLocationActivity
import com.makeus.eatoo.src.home.create_group.model.CreateGroupRequest
import com.makeus.eatoo.src.home.create_group.model.CreateGroupResponse
import com.makeus.eatoo.src.home.create_group.model.Keyword
import com.makeus.eatoo.src.home.group.GroupActivity
import com.makeus.eatoo.util.getUserIdx
import com.naver.maps.geometry.LatLng
import com.makeus.eatoo.src.home.create_group.model.SearchResultEntity
import com.makeus.eatoo.util.getUserNickName
import com.google.android.flexbox.FlexboxLayout
import com.naver.maps.map.overlay.Marker
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.naver.maps.map.*
import com.naver.maps.map.util.MarkerIcons
import kotlin.math.roundToInt

class CreateGroupActivity :
    BaseActivity<ActivityCreateGroupBinding>(ActivityCreateGroupBinding::inflate),
    OnMapReadyCallback, CreateGroupView, View.OnClickListener {

    private lateinit var naverMap: NaverMap
    private lateinit var searchResult: SearchResultEntity
    private lateinit var locationManager: LocationManager
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var mapShowing = false

    companion object {
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

        getCurrentLocation()

        setOnClickListeners()


    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.register_group_btn -> checkValidation()
            R.id.tv_search_location -> startActivity(
                Intent(
                    this,
                    GroupLocationActivity::class.java
                )
            )
            R.id.btn_change_location -> if (mapShowing) startActivity(
                Intent(
                    this,
                    GroupLocationActivity::class.java
                )
            )
        }
    }

    private fun setOnClickListeners() {
        binding.customToolbar.leftIcon.setOnClickListener { finish() }
        binding.registerGroupBtn.setOnClickListener(this)
        binding.tvSearchLocation.setOnClickListener(this)
        binding.btnChangeLocation.setOnClickListener(this)

    }

    /*

  지도 보이기

   */
    private fun getSearchJson() {

        val gson = Gson()
        val jsonFromCurrentLocation =
            ApplicationClass.sSharedPreferences.getString("CURRENT_LOCATION", "")
        val jsonFromLocationSearch =
            ApplicationClass.sSharedPreferences.getString("LOCATION_SEARCH", "")

        if (jsonFromCurrentLocation != "" || jsonFromLocationSearch != "") {
            if (jsonFromCurrentLocation != "") {
                searchResult =
                    gson.fromJson(jsonFromCurrentLocation, SearchResultEntity::class.java)
            } else if (jsonFromLocationSearch != "") {
                searchResult = gson.fromJson(jsonFromLocationSearch, SearchResultEntity::class.java)
            }

            mapShowing = true
            setAddressTv(searchResult.fullAddress)
            setupMap()
            ApplicationClass.sSharedPreferences.edit().putString("CURRENT_LOCATION", null).apply()
            ApplicationClass.sSharedPreferences.edit().putString("LOCATION_SEARCH", null).apply()
        }
    }

    private fun setAddressTv(fullAddress: String) {
        binding.tvSearchLocation.text = fullAddress
    }

    private fun enableMap() {
        setupMap()
    }

    private fun showMap() {
        binding.llContainerMap.isVisible = false
        mapShowing = true
        binding.btnChangeLocation.isVisible = true

        binding.zoom.map = naverMap
        binding.zoom.isVisible = true
    }

    private fun closeMap() {
        binding.llContainerMap.isVisible = true
        mapShowing = false
        binding.btnChangeLocation.isVisible = false
    }

    private fun setupMap() {
        val fm = supportFragmentManager
        val mapFragment =
            fm.findFragmentById(R.id.frag_create_group_map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    fm.beginTransaction().add(R.id.frag_create_group_map, it).commit()
                }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        naverMap.maxZoom = 19.0
        naverMap.minZoom = 10.0

        if (mapShowing) {
            setupMarker(searchResult)
            val uiSetting = naverMap.uiSettings
            uiSetting.isZoomControlEnabled = false
            showMap()
        }
    }

    private fun setupMarker(searchResult: SearchResultEntity) { //검색한 위도경도
        latitude = searchResult.locationLatLng.latitude.toDouble()
        longitude = searchResult.locationLatLng.longitude.toDouble()

        val positionLatLng = LatLng(latitude, longitude)

        val cameraUpdate = CameraUpdate.scrollTo(positionLatLng).animate(CameraAnimation.Easing)

        if (::naverMap.isInitialized) naverMap.moveCamera(cameraUpdate)
        else setupMap()

        val marker = Marker()
        marker.apply {  //태그 달 수 있음.
            position = positionLatLng
            map = naverMap
            icon = MarkerIcons.BLACK
            iconTintColor = Color.RED
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
                if (keywordList.size - 1 == 4) showCustomToast(resources.getString(R.string.keyword_num_limit))
                else {
                    val et = v as EditText
                    val keyword = et.text.toString()
                    if (keyword.length >= 11) showCustomToast(resources.getString(R.string.keyword_length_limit))
                    else {
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
        (0 until childCount - 1).mapNotNull { index ->
            val childChip = getChildAt(index) as? Chip
            keywordList.add(Keyword(name = childChip?.text.toString()))
        }
        return keywordList
    }

    /*

    color chips

     */

    private fun getGroupColor(): Int {
        val checkedColor
        = binding.radiogroupCreateGroup.findViewById<RadioButton>(binding.radiogroupCreateGroup.checkedRadioButtonId).tag

        return checkedColor.toString().toInt()
    }

    /*

    register

     */

    private fun checkValidation() {

        if (binding.etGroupName.text.isEmpty()) {
            showCustomToast("그룹 이름을 입력해주세요.")
            return
        }

        if (latitude == 0.0 || longitude == 0.0) {
            showCustomToast("위치를 입력해주세요.")
            return
        }


        val group = CreateGroupRequest(
            name = binding.etGroupName.text.toString(),
            color = getGroupColor(),
            latitude = latitude,
            longitude = longitude,
            keyword = binding.flexboxMakeGroup.getAllChips()
        )

        createGroupRequest(group)
    }

    private fun createGroupRequest(group: CreateGroupRequest) {
        showLoadingDialog(this)
        CreateGroupService(this).tryPostGroup(getUserIdx(), group)
        ApplicationClass.sSharedPreferences.edit()
            .putString(ApplicationClass.GROUP_NAME, binding.etGroupName.text.toString()).apply()
    }

    /*

     위치 권한

     */


    private fun getCurrentLocation() = with(binding) {
        if (!mapShowing) { //첫 방문
            llContainerMap.setOnClickListener {
                requestPermission()
            }
        }
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



    ///////server result

    override fun onPostGroupSuccess(response: CreateGroupResponse) {
        dismissLoadingDialog()
        ApplicationClass.sSharedPreferences.edit()
            .putInt(ApplicationClass.GROUP_IDX,  response.result.groupIdx).apply()
        ApplicationClass.sSharedPreferences.edit()
            .putString(ApplicationClass.GROUP_NAME,  binding.etGroupName.text.toString()).apply()
        startActivity(Intent(this, GroupActivity::class.java))
        finish()
    }

    override fun onPostGroupFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }


}