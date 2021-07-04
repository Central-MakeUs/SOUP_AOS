package com.example.eatoo.src.main.create_group

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityCreateGroupBinding
import com.example.eatoo.databinding.ActivityGroupLocationBinding
import com.example.eatoo.src.main.create_group.adapter.LocationSearchRvAdapter
import com.example.googlemapsapiprac.model.LocationLatLngEntity
import com.example.googlemapsapiprac.model.SearchResultEntity
import com.example.googlemapsapiprac.response.search.Pois
import com.example.googlemapsapiprac.response.search.SearchResponse

class GroupLocationActivity : BaseActivity<ActivityGroupLocationBinding>(ActivityGroupLocationBinding::inflate), GroupLocationView{

    lateinit var locationAdapter : LocationSearchRvAdapter
    lateinit var mlayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        initSearchListener()


    }
    private fun initViews() = with(binding) {
        tvNoResult.isVisible = false
        locationAdapter = LocationSearchRvAdapter()
        mlayoutManager = LinearLayoutManager(this@GroupLocationActivity)
        rvCreateGroupLocation.apply {
            adapter = locationAdapter
            layoutManager = mlayoutManager
        }
    }

    private fun initSearchListener() =with(binding){
        btnSearch.setOnClickListener {
            searchLocation(etSearchLocation.text.toString())
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
                fullAddress = it.fullAddressRoad ?:"도로명 주소 없음",
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
//            startActivity(Intent(this, MapActivity::class.java).apply {
//                putExtra(SEARCH_RESULT_EXTRA_KEY, it)
//            })
        }
    }

    override fun onGetLocationFail(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}