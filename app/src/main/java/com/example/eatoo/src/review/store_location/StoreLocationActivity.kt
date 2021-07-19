package com.example.eatoo.src.review.store_location

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityStoreLocationBinding
import com.example.eatoo.src.review.create_review.create_review1.CreateReview1Activity
import com.example.eatoo.src.review.store_location.adaper.StoreSearchRVAdapter
import com.example.eatoo.src.review.store_location.model.KakaoSearchDoc
import com.example.eatoo.src.review.store_location.model.KakaoSearchResponse
import com.example.eatoo.src.review.store_map.StoreMapActivity

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
        StoreLocationService(this).tryGetStoreSearch(query, FIRST_PAGE_NUM)
    }

    private val mRVScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if(storeSearchAdapter.storeList.isNotEmpty()
                && !recyclerView.canScrollVertically(1)) { //?
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
        if(response.documents.isEmpty()){
            if(page == FIRST_PAGE_NUM){ //결과 없음
                binding.clNoSearchResult.isVisible = true
            }else{ //더이상 결과 없음
                isLastPage = true
            }
        }else {
            isLastPage = false
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
        val address = if(item.road_address_name.isEmpty()) item.road_address_name else item.address_name

        val intent = Intent(this, CreateReview1Activity::class.java)
        intent.apply {
            putExtra("address", address)
            putExtra("lat", item.y.toDouble())
            putExtra("lng", item.x.toDouble())
        }
        startActivity(intent)

    }


}