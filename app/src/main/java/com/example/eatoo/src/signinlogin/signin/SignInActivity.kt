package com.example.eatoo.src.signinlogin.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.eatoo.config.ApplicationClass.Companion.sSharedPreferences
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivitySignInBinding
import com.example.eatoo.src.explanation.ExplanationActivity

class SignInActivity :  BaseActivity<ActivitySignInBinding>(ActivitySignInBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.nameTxt.bringToFront()

        binding.nextBtn.setOnClickListener {

            val intent = Intent(this, SignInActivity2::class.java)

            intent.putExtra("name", binding.nameEdt.text.toString())
            intent.putExtra("phone", binding.phoneNumberEdt.text.toString())

            Log.d("인텐트 종류",""+binding.nameEdt.text+ binding.phoneNumberEdt.text)

            startActivity(intent)
        }

    }
}