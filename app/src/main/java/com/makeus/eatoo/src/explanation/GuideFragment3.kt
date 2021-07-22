package com.makeus.eatoo.src.explanation

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.databinding.FragmentGuide3Binding
import com.makeus.eatoo.src.signinlogin.login.LoginActivity
import com.makeus.eatoo.src.signinlogin.signin.SignInActivity

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