package com.example.eatoo.src.review.my_review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityMyReviewBinding
import com.example.eatoo.src.review.create_review.CreateReviewActivity
import com.example.eatoo.src.review.my_review.model.MyReviewResponse
import com.example.eatoo.util.getUserIdx

class MyReviewActivity : BaseActivity<ActivityMyReviewBinding>(ActivityMyReviewBinding::inflate),
View.OnClickListener, MyReviewView{

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
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fab_myreview -> {
                startActivity(Intent(this, CreateReviewActivity::class.java))
            }
        }
    }

    override fun onGetMyReviewSuccess(response: MyReviewResponse) {
        dismissLoadingDialog()
        Log.d("myreviewactivity", response.toString())
    }

    override fun onGetMyReviewFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:"통신오류가 발생했습니다.")
    }
}