package com.makeus.eatoo.src.home.group.category.category_detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityCategoryStoreDetailBinding
import com.makeus.eatoo.like.LikeService
import com.makeus.eatoo.like.LikeView
import com.makeus.eatoo.src.home.group.category.category_detail.adapter.StoreDetailImageRVAdapter
import com.makeus.eatoo.src.home.group.category.category_detail.adapter.StoreDetailKeywordRVAdapter
import com.makeus.eatoo.src.home.group.category.category_detail.adapter.StoreDetailReviewRVAdapter
import com.makeus.eatoo.src.home.group.category.category_detail.model.GetReviewImgRe
import com.makeus.eatoo.src.home.group.category.category_detail.model.GetReviewRe
import com.makeus.eatoo.src.home.group.category.category_detail.model.GetStoreKeywordRe
import com.makeus.eatoo.src.home.group.category.category_detail.model.StoreDetailResponse
import com.makeus.eatoo.src.home.group.groupmatesuggestion.Group_Mate_Suggetsion_Activity
import com.makeus.eatoo.util.getGroupIdx
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.glideUtil
import okhttp3.internal.applyConnectionSpec

class CategoryStoreDetailActivity
    : BaseActivity<ActivityCategoryStoreDetailBinding>(ActivityCategoryStoreDetailBinding::inflate),
StoreDetailView, View.OnClickListener, LikeView{

    private lateinit var storeKeywordAdapter : StoreDetailKeywordRVAdapter
    private lateinit var storeImgAdapter : StoreDetailImageRVAdapter
    private lateinit var storeReviewAdapter : StoreDetailReviewRVAdapter

    var storeAddress : String? = null
    var isLike = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getStoreIntent()
        binding.btnSuggestMate.setOnClickListener(this)
        binding.ibtnBackArrow.setOnClickListener(this)
        binding.ibtnLike.setOnClickListener(this)

    }

    private fun getStoreIntent() {
        val storeIdx = intent.getIntExtra("storeIdx", -1)
        storeAddress = intent.getStringExtra("address")
        if(storeIdx != -1 && storeAddress != null) getStoreDetail(storeIdx)
    }

    private fun setImgRV(imgList: List<GetReviewImgRe>) {
        storeImgAdapter = StoreDetailImageRVAdapter(this, imgList)
        binding.rvStoreImage.apply {
            adapter = storeImgAdapter
            layoutManager = LinearLayoutManager(this@CategoryStoreDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setKeywordRV(keywordList: List<GetStoreKeywordRe>) {
        storeKeywordAdapter = StoreDetailKeywordRVAdapter(this, keywordList)
        binding.rvStoreKeyword.apply {
            adapter = storeKeywordAdapter
            layoutManager = LinearLayoutManager(this@CategoryStoreDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setReviewRV(reviewList: List<GetReviewRe>) {
        storeReviewAdapter = StoreDetailReviewRVAdapter(this, reviewList)
        binding.rvStoreGroupReview.apply {
            adapter = storeReviewAdapter
            layoutManager = LinearLayoutManager(this@CategoryStoreDetailActivity)
        }
    }


    private fun getStoreDetail(storeIdx: Int) {
        showLoadingDialog(this)
        StoreDetailService(this).tryGetStoreDetail(getUserIdx(), storeIdx)
    }

    override fun onGetStoreDetailSuccess(response: StoreDetailResponse) {
        dismissLoadingDialog()

        glideUtil(this, response.result.imgUrl, binding.ivStoreImage)
        binding.tvStoreName.text = response.result.storeName
        binding.tvStoreAddress.text = storeAddress
        if(response.result.isLiked == "Y") {
            isLike = true
            binding.ibtnLike.setBackgroundResource(R.drawable.eva_heart_outline)
        }else {
            isLike = false
            binding.ibtnLike.setBackgroundResource(R.drawable.vector)
        }

        setKeywordRV(response.result.getStoreKeywordRes)
        setImgRV(response.result.getReviewImgRes)
        setReviewRV(response.result.getReviewRes)
    }



    override fun onGetStoreDetailFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_suggest_mate -> {
                startActivity(Intent(this, Group_Mate_Suggetsion_Activity::class.java))
            }
            R.id.ibtn_back_arrow -> {
                finish()
            }
            R.id.ibtn_like -> {
                if(isLike) {
                    binding.ibtnLike.setBackgroundResource(R.drawable.vector)
                    LikeService(this).tryPatchLike(getUserIdx(), getGroupIdx())
                }else {
                    binding.ibtnLike.setBackgroundResource(R.drawable.eva_heart_outline)
                    LikeService(this).tryPostLike(getUserIdx(), getGroupIdx())
                }
            }
        }
    }

    override fun onPostLikeSuccess() {
        isLike = true
    }

    override fun onPostLikeFail(message: String?) {
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

    override fun onPatchLikeSuccess() {
        isLike= false
    }

    override fun onPatchLikeFail(message: String?) {
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }
}