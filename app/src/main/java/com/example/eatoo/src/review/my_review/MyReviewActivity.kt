package com.example.eatoo.src.review.my_review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityMyReviewBinding
import com.example.eatoo.src.review.create_review.CreateReviewActivity

class MyReviewActivity : BaseActivity<ActivityMyReviewBinding>(ActivityMyReviewBinding::inflate),
View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindView()

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
}