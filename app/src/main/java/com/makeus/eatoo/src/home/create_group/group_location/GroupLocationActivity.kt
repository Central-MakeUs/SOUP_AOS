package com.makeus.eatoo.src.home.create_group.group_location

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityGroupLocationBinding
import com.makeus.eatoo.src.home.create_group.adapter.LocationSearchRvAdapter
import com.makeus.eatoo.src.home.create_group.group_location.current_location.CurrentLocationActivity
import com.makeus.googlemapsapiprac.model.LocationLatLngEntity
import com.makeus.eatoo.src.home.create_group.model.SearchResultEntity
import com.makeus.eatoo.src.review.store_location.StoreLocationActivity
import com.makeus.eatoo.src.review.store_location.StoreLocationService
import com.makeus.eatoo.src.review.store_location.StoreLocationView
import com.makeus.eatoo.src.review.store_location.model.KakaoSearchDoc
import com.makeus.eatoo.src.review.store_location.model.KakaoSearchResponse
import com.google.gson.Gson

class GroupLocationActivity :
    BaseActivity<ActivityGroupLocationBinding>(ActivityGroupLocationBinding::inflate),
    StoreLocationView, LocationSearchRvAdapter.OnSearchResultClickListener, View.OnClickListener {

    lateinit var locationAdapter: LocationSearchRvAdapter

    private var page = StoreLocationActivity.FIRST_PAGE_NUM
    private var isLastPage = false
    private var query = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        initSearchListener()


        locationAdapter = LocationSearchRvAdapter(this)
        binding.rvGroupLocationSearch.apply {
            adapter = locationAdapter
            layoutManager = LinearLayoutManager(this@GroupLocationActivity)
        }
        binding.rvGroupLocationSearch.addOnScrollListener(mRVScrollListener)


    }

    private fun initViews() = with(binding) {
        ivLocationSearchCancel.setOnClickListener(this@GroupLocationActivity)
        tvSetCurrentLocation.setOnClickListener(this@GroupLocationActivity)

        clNoSearchResult.isVisible = false
        toolbarCustom.leftIcon.setOnClickListener { finish() }

        etGroupLocation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                ivLocationSearchCancel.isVisible = p0.toString().isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_location_search_cancel -> binding.etGroupLocation.text.clear()
            R.id.tv_set_current_location -> {
                startActivity(Intent(this, CurrentLocationActivity::class.java))
                finish()
            }
        }
    }

    private fun initSearchListener() = with(binding) {
        etGroupLocation.setOnKeyListener { v, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                query =
                    if (etGroupLocation.text.toString() == "") " " else etGroupLocation.text.toString()
                searchLocation(query)
            }
            false
        }
    }

    private fun searchLocation(query: String?) {
        showLoadingDialog(this)
        locationAdapter.removeAllData()
        StoreLocationService(this).tryGetStoreSearch(query, StoreLocationActivity.FIRST_PAGE_NUM)
    }

    private val mRVScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (!mLoadingDialog.isShowing && locationAdapter.storeList.isNotEmpty()
                && !recyclerView.canScrollVertically(1)
            ) {
                if (!isLastPage) loadPage(++page)
            }
        }
    }

    private fun loadPage(pageNum: Int) {
        showLoadingDialog(this)
        StoreLocationService(this).tryGetStoreSearch(query, pageNum)
    }

    override fun onSearchResultClick(item: KakaoSearchDoc) {
        val address =
            if (item.road_address_name.isEmpty()) item.address_name else item.road_address_name
        val searchResult = SearchResultEntity(
            buildingName = item.place_name,
            fullAddress = address,
            locationLatLng = LocationLatLngEntity(
                item.y.toFloat(),
                item.x.toFloat()

            )
        )

        val gson = Gson()
        val jsonLocationSearch = gson.toJson(searchResult)

        ApplicationClass.sSharedPreferences.edit()
            .putString("LOCATION_SEARCH", jsonLocationSearch).apply()

        finish()

    }

    override fun onSearchStoreSuccess(response: KakaoSearchResponse) {
        dismissLoadingDialog()
        if (response.documents.isEmpty()) {
            if (page == StoreLocationActivity.FIRST_PAGE_NUM) { //결과 없음
                binding.clNoSearchResult.isVisible = true
            } else { //더이상 결과 없음
                isLastPage = true
            }
        } else {
            isLastPage = false
            binding.clNoSearchResult.isVisible = false
            binding.clNoSearchResult.isVisible = false
            locationAdapter.addAllData(response.documents)
        }
    }

    override fun onSearchStoreFail(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }


}