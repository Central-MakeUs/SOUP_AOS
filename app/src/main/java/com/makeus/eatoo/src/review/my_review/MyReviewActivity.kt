package com.makeus.eatoo.src.review.my_review

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityMyReviewBinding
import com.makeus.eatoo.src.review.create_review.create_review1.CreateReview1Activity
import com.makeus.eatoo.src.review.my_review.adapter.MyReviewRVAdapter
import com.makeus.eatoo.src.review.my_review.model.MyReviewResponse
import com.makeus.eatoo.src.review.my_review.model.MyReviewResult
import com.makeus.eatoo.src.review.review_detail.ReviewDetailActivity
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.getUserNickName

class MyReviewActivity : BaseActivity<ActivityMyReviewBinding>(ActivityMyReviewBinding::inflate),
View.OnClickListener, MyReviewView, MyReviewRVAdapter.OnMyReviewClickListener{

    private lateinit var myReviewAdapter : MyReviewRVAdapter

    override fun onResume() {
        super.onResume()

        getMyReview()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setViews()
        registerBr()

    }

    private val reviewBr = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if(p1?.action.equals("finish_my_review")) {
                this@MyReviewActivity.finish()
            }
        }

    }

    private fun registerBr() {
        val filter = IntentFilter("finish_my_review")
        LocalBroadcastManager.getInstance(this).registerReceiver(reviewBr, filter)
    }

    private fun unregisterBr() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(reviewBr)
    }

    override fun onDestroy() {
        unregisterBr()
        super.onDestroy()
    }

    private fun getMyReview() {
        showLoadingDialog(this)
        MyReviewService(this).tryGetMyReview(getUserIdx())
    }

    private fun setViews() {
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
            myReviewAdapter.notifyDataSetChanged()
        }else {
            binding.tvNoReview.isVisible = true
            binding.ivNoReview.isVisible = true
        }

    }

    override fun onGetMyReviewFail(message: String?) {
        dismissLoadingDialog()
        binding.tvNoReview.isVisible = true
        binding.ivNoReview.isVisible = true
    }

    override fun onMyReviewClicked(reviewIdx : Int) {
        val intent = Intent(this, ReviewDetailActivity::class.java)
        intent.putExtra("reviewIdx", reviewIdx)
        startActivity(intent)
    }
}