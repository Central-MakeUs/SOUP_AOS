package com.makeus.eatoo.src.review.store_location

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityStoreLocationBinding
import com.makeus.eatoo.src.home.create_group.model.SearchResultEntity
import com.makeus.eatoo.src.review.create_review.create_review1.CreateReview1Activity
import com.makeus.eatoo.src.review.store_location.adaper.StoreSearchRVAdapter
import com.makeus.eatoo.src.review.store_location.model.KakaoSearchDoc
import com.makeus.eatoo.src.review.store_location.model.KakaoSearchResponse
import com.makeus.eatoo.src.review.store_map.StoreMapActivity
import com.makeus.googlemapsapiprac.model.LocationLatLngEntity

class StoreLocationActivity
    : BaseActivity<ActivityStoreLocationBinding>(ActivityStoreLocationBinding::inflate),
StoreLocationView, StoreSearchRVAdapter.OnSearchResultClickListener, View.OnClickListener {

    companion object {
        const val FIRST_PAGE_NUM = 1
    }

    private var page = FIRST_PAGE_NUM
    private var isLastPage = false
    private var query = " "

    private lateinit var storeSearchAdapter : StoreSearchRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listenStoreSearch()

        setViewListener()
        storeSearchAdapter = StoreSearchRVAdapter(this, this)
        binding.rvStoreSearch.apply {
            adapter = storeSearchAdapter
            layoutManager = LinearLayoutManager(this@StoreLocationActivity)
        }
        binding.rvStoreSearch.addOnScrollListener(mRVScrollListener)


    }

    private fun setViewListener() {
        binding.tvGotoMap.setOnClickListener(this)
        binding.ivReviewSearchCancel.setOnClickListener(this)
        binding.etReviewSearchStore.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.ivReviewSearchCancel.isVisible = p0.toString().isNotEmpty()
            }
            override fun afterTextChanged(p0: Editable?) {}

        })
        binding.toolbarCustom.leftIcon.setOnClickListener { finish() }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.tv_goto_map -> {
                startActivity(Intent(this, StoreMapActivity::class.java))
            }
            R.id.iv_review_search_cancel -> binding.etReviewSearchStore.text.clear()
        }
    }

    private fun listenStoreSearch() = with(binding){
        etReviewSearchStore.setOnKeyListener { v, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                query = if(etReviewSearchStore.text.toString()=="") " " else etReviewSearchStore.text.toString()
                getSearchStore(query)
            }
            false
        }
    }

    private fun getSearchStore(query: String?) {
        showLoadingDialog(this)
        storeSearchAdapter.removeAllData()
        page = FIRST_PAGE_NUM
        StoreLocationService(this).tryGetStoreSearch(query, page)
    }

    private val mRVScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if(!mLoadingDialog.isShowing && storeSearchAdapter.storeList.isNotEmpty()
                && !recyclerView.canScrollVertically(1)) {
                if(!isLastPage) loadPage(++page)
            }
        }
    }

    private fun loadPage(pageNum: Int) {
        showLoadingDialog(this)
        StoreLocationService(this).tryGetStoreSearch( query, pageNum)
    }

    override fun onSearchStoreSuccess(response: KakaoSearchResponse) {
        dismissLoadingDialog()
        if(response.documents.isEmpty() && page == FIRST_PAGE_NUM){  //결과 없음
                binding.clNoSearchResult.isVisible = true
        }else {
                isLastPage = response.meta.is_end
                binding.clNoSearchResult.isVisible = false
                binding.clNoSearchResult.isVisible = false
                storeSearchAdapter.addAllData(response.documents)
        }
    }

    override fun onSearchStoreFail(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onSearchResultClick(item: KakaoSearchDoc) {
        val address = if(item.road_address_name.isEmpty()) item.address_name else item.road_address_name

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
            .putString("REVIEW_LOCATION_SEARCH", jsonLocationSearch).apply()

        finish()

    }


}