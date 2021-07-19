package com.example.eatoo.src.home.create_group.group_location

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityGroupLocationBinding
import com.example.eatoo.src.home.create_group.CreateGroupActivity
import com.example.eatoo.src.home.create_group.CreateGroupActivity.Companion.SEARCH_RESULT_EXTRA_KEY
import com.example.eatoo.src.home.create_group.adapter.LocationSearchRvAdapter
import com.example.eatoo.util.makeMainAdress
import com.example.googlemapsapiprac.model.LocationLatLngEntity
import com.example.googlemapsapiprac.model.SearchResultEntity
import com.example.googlemapsapiprac.response.search.Pois
import com.example.googlemapsapiprac.response.search.SearchResponse

class GroupLocationActivity : BaseActivity<ActivityGroupLocationBinding>(ActivityGroupLocationBinding::inflate),
    GroupLocationView {

    lateinit var locationAdapter : LocationSearchRvAdapter
    lateinit var mlayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        initSearchListener()
        binding.toolbarCustom.leftIcon.setOnClickListener { finish() }


    }
    private fun initViews() = with(binding) {
        clNoSearchResult.isVisible = false
        locationAdapter = LocationSearchRvAdapter()
        mlayoutManager = LinearLayoutManager(this@GroupLocationActivity)
        rvGroupLocationSearch.apply {
            adapter = locationAdapter
            layoutManager = mlayoutManager
        }
    }

    private fun initSearchListener() =with(binding) {
        etGroupLocation.setOnKeyListener { v, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                searchLocation(etGroupLocation.text.toString())
            }
            false
        }
    }

    private fun searchLocation(keyword: String) {
        GroupLocationService(this).tryGetLocation(keyword)
    }

    override fun onGetLocationSuccess(response: SearchResponse) {
        setData(response.searchPoiInfo.pois)

    }

    private fun setData(pois: Pois) {
        val dataList = pois.poi.map {
            SearchResultEntity(
                buildingName = it.name ?: "빌딩명 없음",
                fullAddress = makeMainAdress(it),
                locationLatLng = LocationLatLngEntity(
                    it.noorLat,
                    it.noorLon
                )
            )
        }
        locationAdapter.setSearchResultList(dataList) {
            Toast.makeText(
                this,
                "빌딩이름 : ${it.buildingName}, 주소: ${it.fullAddress}, 위도/경도: ${it.locationLatLng}",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(this, CreateGroupActivity::class.java).apply {
                putExtra(SEARCH_RESULT_EXTRA_KEY, it)
            })
        }
    }

    override fun onGetLocationFail(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}