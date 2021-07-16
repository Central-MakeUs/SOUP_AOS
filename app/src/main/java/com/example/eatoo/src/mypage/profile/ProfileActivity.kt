package com.example.eatoo.src.mypage.profile

import android.R
import android.os.Bundle
import android.view.View
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityProfileBinding


class ProfileActivity : BaseActivity<ActivityProfileBinding>(ActivityProfileBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}