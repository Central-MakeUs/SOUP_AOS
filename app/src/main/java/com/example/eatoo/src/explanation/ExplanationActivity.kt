package com.example.eatoo.src.explanation

import android.content.Intent
import android.os.Bundle
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityExplanationBinding
import com.example.eatoo.src.explanation.adapter.GuideViewpageradapter
import com.example.eatoo.src.signinlogin.login.LoginActivity
import com.example.eatoo.src.signinlogin.signin.SignInActivity

class ExplanationActivity : BaseActivity<ActivityExplanationBinding>(ActivityExplanationBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pagerAdapter = GuideViewpageradapter(this)
        pagerAdapter.addFragment(GuideFragment1())
        pagerAdapter.addFragment(GuideFragment2())
        pagerAdapter.addFragment(GuideFragment3())
        binding.photoGuideVp.adapter = pagerAdapter


    }
}