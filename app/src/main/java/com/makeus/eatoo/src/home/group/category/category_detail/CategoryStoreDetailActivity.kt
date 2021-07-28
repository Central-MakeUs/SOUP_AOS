package com.makeus.eatoo.src.home.group.category.category_detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.ToggleButton
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
StoreDetailView, View.OnClickListener, LikeView, CompoundButton.OnCheckedChangeListener{

    private lateinit var storeKeywordAdapter : StoreDetailKeywordRVAdapter
    private lateinit var storeImgAdapter : StoreDetailImageRVAdapter
    private lateinit var storeReviewAdapter : StoreDetailReviewRVAdapter

    var storeIdx = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getStoreIntent()
        binding.btnSuggestMate.setOnClickListener(this)
        binding.ibtnBackArrow.setOnClickListener(this)
        binding.toggleStoreLike.setOnCheckedChangeListener(this)

    }

    private fun getStoreIntent() {
        storeIdx = intent.getIntExtra("storeIdx", -1)
        if(storeIdx != -1) getStoreDetail(storeIdx)
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
        binding.tvStoreAddress.text = response.result.address
        binding.toggleStoreLike.isChecked = response.result.isLiked == "Y"

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
        }
    }
    override fun onPostLikeFail(message: String?) {
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

    override fun onPatchLikeSuccess() {
    }

    override fun onPatchLikeFail(message: String?) {
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

    override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
        if(isChecked) LikeService(this).tryPostLike(getUserIdx(), storeIdx)
        else  LikeService(this).tryPatchLike(getUserIdx(), storeIdx)
    }
}