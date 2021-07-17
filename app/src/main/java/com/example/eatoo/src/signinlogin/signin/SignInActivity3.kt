package com.example.eatoo.src.signinlogin.signin


import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivitySingIn3Binding
import com.example.eatoo.src.signinlogin.login.LoginActivity
import com.example.eatoo.src.signinlogin.signin.model.SignInRequest
import com.example.eatoo.src.signinlogin.signin.model.SignInResponse
import com.example.eatoo.util.putSharedPref

class SignInActivity3 : BaseActivity<ActivitySingIn3Binding>(ActivitySingIn3Binding::inflate), SignInActivityView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.nextBtn.setOnClickListener {
            if(intent.hasExtra("name") &&intent.hasExtra("phone")&&intent.hasExtra("email")&&intent.hasExtra("password")&&intent.hasExtra("password_check")){

                val name = intent.getStringExtra("name")
                val phone = intent.getStringExtra("phone")
                val email = intent.getStringExtra("email")
                val password = intent.getStringExtra("password")
                val password_check = intent.getStringExtra("password_check")

                val postRequest = SignInRequest(name = name.toString(),phone = phone.toString(),email = email.toString(), password = password.toString(), passwordConfirm = password_check.toString(), nickName = binding.nickNameEdt.text.toString() )
                Log.d("요청사항", ""+ postRequest)
                showLoadingDialog(this)
                SignInActivityService(this).tryPostSignUp(postRequest)

            }
            else{
                showCustomToast("전달받은 인텐트 없음")
            }

        }
    }

    override fun onGetUserSuccess(response: SignInResponse) {

    }

    override fun onGetUserFailure(message: String) {

    }

    override fun onPostSignUpSuccess(response: SignInResponse) {
        dismissLoadingDialog()
        if(response.code == 1000) {
            putSharedPref(response.result.jwt, response.result.userIdx)
            showCustomToast(response.message)
            startActivity(Intent(this, LoginActivity::class.java))
        }
        else if(response.code == 2025){
            showCustomToast(response.message)
            startActivity(Intent(this, SignInActivity::class.java))
        }
        else if(response.code == 2016 || response.code == 2027 ||response.code == 2028){
            showCustomToast(response.message)
            startActivity(Intent(this, SignInActivity2::class.java))
        }
        else if(response.code == 2033){
            showCustomToast(response.message)
        }
    }


    override fun onPostSignUpFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)

    }
}