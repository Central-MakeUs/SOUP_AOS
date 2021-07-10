package com.example.eatoo.src.review.create_review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityCreateReviewBinding
import com.example.eatoo.databinding.ActivityMyReviewBinding
import com.example.eatoo.src.review.store_map.StoreMapActivity

class CreateReviewActivity :  BaseActivity<ActivityCreateReviewBinding>(ActivityCreateReviewBinding::inflate),
View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViews()
    }

    private fun bindViews() {
        binding.flCreateReview.setOnClickListener(this)
        binding.llContainerReviewMap.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.fl_create_review -> {
                startActivity(Intent(this, StoreMapActivity::class.java))
            }
            R.id.ll_container_review_map -> {
                showCustomToast("ll clicked!")
                startActivity(Intent(this, StoreMapActivity::class.java))
            }
        }
    }
}