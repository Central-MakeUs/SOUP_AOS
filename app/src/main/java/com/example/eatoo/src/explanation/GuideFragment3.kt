package com.example.eatoo.src.explanation

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.eatoo.R
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentGuide3Binding
import com.example.eatoo.src.signinlogin.login.LoginActivity
import com.example.eatoo.src.signinlogin.signin.SignInActivity

class GuideFragment3 : BaseFragment<FragmentGuide3Binding>(FragmentGuide3Binding::bind, R.layout.fragment_guide3){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInBtn.setOnClickListener {
            startActivity(Intent(activity, SignInActivity::class.java))
        }
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }

}