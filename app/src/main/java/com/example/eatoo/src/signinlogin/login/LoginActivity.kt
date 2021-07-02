package com.example.eatoo.src.signinlogin.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityLoginBinding
import com.example.eatoo.src.main.MainActivity
import com.example.eatoo.src.signinlogin.login.model.LoginRequest
import com.example.eatoo.src.signinlogin.login.model.LoginResponse

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),LoginActivityView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.loginBtn.setOnClickListener {
            val postRequest = LoginRequest(email = binding.emailEdt.text.toString(), password = binding.passwordEdt.text.toString())
            Log.d("요청사항", ""+ postRequest)
            showLoadingDialog(this)
            LoginService(this).tryPostLogin(postRequest)
        }
    }

    override fun onGetUserSuccess(response: LoginResponse) {
    }

    override fun onGetUserFailure(message: String) {
    }

    override fun onPostLoginSuccess(response: LoginResponse) {
        dismissLoadingDialog()
        showCustomToast(response.message)
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onPostLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}