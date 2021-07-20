package com.example.eatoo.src.review.my_review

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityMyReviewBinding
import com.example.eatoo.src.review.create_review.create_review1.CreateReview1Activity
import com.example.eatoo.src.review.my_review.adapter.MyReviewRVAdapter
import com.example.eatoo.src.review.my_review.model.MyReviewResponse
import com.example.eatoo.src.review.my_review.model.MyReviewResult
import com.example.eatoo.util.getUserIdx
import com.example.eatoo.util.getUserNickName

class MyReviewActivity : BaseActivity<ActivityMyReviewBinding>(ActivityMyReviewBinding::inflate),
View.OnClickListener, MyReviewView, MyReviewRVAdapter.OnMyReviewClickListener{

    private lateinit var myReviewAdapter : MyReviewRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindView()
        getMyReview()
    }

    private fun getMyReview() {
        showLoadingDialog(this)
        MyReviewService(this).tryGetMyReview(getUserIdx())
    }

    private fun bindView() {
        binding.fabMyreview.setOnClickListener(this)
        binding.tvMyReviewUserName.text = getUserNickName()
        binding.customToolbar.leftIcon.setOnClickListener { finish() }
        binding.fabMyreview.imageTintList =   ColorStateList.valueOf(Color.rgb(255, 255, 255))
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fab_myreview -> {
                startActivity(Intent(this, CreateReview1Activity::class.java))
            }
        }
    }

    override fun onGetMyReviewSuccess(response: MyReviewResponse) {
        dismissLoadingDialog()
        if(response.result.isNotEmpty()){
            binding.tvNoReview.isVisible = false
            binding.ivNoReview.isVisible = false
            myReviewAdapter = MyReviewRVAdapter(this, response.result, this)
            binding.rvMyreview.apply {
                adapter = myReviewAdapter
                layoutManager = LinearLayoutManager(this@MyReviewActivity)
            }
        }else {
            binding.tvNoReview.isVisible = true
            binding.ivNoReview.isVisible = true
        }

    }

    override fun onGetMyReviewFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:"통신오류가 발생했습니다.")
    }

    override fun onMyReviewClicked(item: MyReviewResult) {
        showCustomToast("${item.storeName} clicked!!")
    }
}