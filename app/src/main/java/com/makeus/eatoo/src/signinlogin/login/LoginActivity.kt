package com.makeus.eatoo.src.signinlogin.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityLoginBinding
import com.makeus.eatoo.src.explanation.ExplanationActivity
import com.makeus.eatoo.src.main.MainActivity
import com.makeus.eatoo.src.signinlogin.login.model.LoginRequest
import com.makeus.eatoo.src.signinlogin.login.model.LoginResponse
import com.makeus.eatoo.util.putSharedPref
import com.makeus.eatoo.util.putSharedPrefUser

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),LoginActivityView {


    private val passwordPattern : String = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{8,20}.\$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.loginBtn.setOnClickListener {
            val postRequest = LoginRequest(email = binding.emailEdt.text.toString(), password = binding.passwordEdt.text.toString())
            Log.d("요청사항", ""+ postRequest)
            showLoadingDialog(this)
            LoginService(this).tryPostLogin(postRequest)
        }


        binding.emailEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //텍스트를 입력 후
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 텍스트 입력 전
            }
            //
            @SuppressLint("ResourceAsColor", "UseCompatLoadingForDrawables")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 중

                binding.warningImg.visibility = View.GONE
                binding.emailEdt.setBackgroundDrawable(binding.emailEdt.getContext().getDrawable(R.drawable.edittext_background_radius))
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailEdt.text).matches() && isValidPassword(binding.passwordEdt.text.toString())){

                    binding.loginBtn.isClickable = true // 버튼 클릭할수 있게
                    binding.loginBtn.isEnabled = true // 버튼 활성화
                    binding.loginBtn.setBackgroundColor(binding.loginBtn.context.resources.getColor(R.color.main_color))

                }
                else {
                    binding.loginBtn.setBackgroundColor(binding.loginBtn.context.resources.getColor(R.color.gray_100))

                }
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailEdt.text).matches()){
                    binding.emailHint.setText(R.string.thank_input)
                    binding.emailHint.setTextColor(binding.emailHint.context.resources.getColor(R.color.main_color))
                }
                else {
                    binding.emailHint.setText(R.string.sign_in_email_bottom)
                    binding.emailHint.setTextColor(binding.emailHint.context.resources.getColor(R.color.gray_100))
                }

            }
        })

        binding.passwordEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //텍스트를 입력 후
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 텍스트 입력 전
            }
            //
            @SuppressLint("ResourceAsColor", "UseCompatLoadingForDrawables")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 중

                Log.v( "email 텍스트 길이", " : "+binding.emailEdt.text.length )
                Log.v( "password 텍스트 길이", " : "+binding.passwordEdt.text.length )
                binding.warningImg2.visibility = View.GONE
                binding.passwordEdt.setBackgroundDrawable(binding.passwordEdt.getContext().getDrawable(R.drawable.edittext_background_radius))
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailEdt.text).matches() &&  isValidPassword(binding.passwordEdt.text.toString())){


                    binding.loginBtn.isClickable = true // 버튼 클릭할수 있게
                    binding.loginBtn.isEnabled = true // 버튼 활성화
                    binding.loginBtn.setBackgroundColor(binding.loginBtn.getContext().getResources().getColor(
                        R.color.main_color))
                    binding.loginBtn.setBackgroundDrawable(binding.loginBtn.getContext().getDrawable(R.drawable.sign_in_btn_background_main_color))

                }
                else {
                    binding.loginBtn.setBackgroundColor(binding.loginBtn.getContext().getResources().getColor(R.color.gray_100))
                    binding.loginBtn.setBackgroundDrawable(binding.loginBtn.getContext().getDrawable(R.drawable.sign_in_btn_background_gray))

                }
                if( isValidPassword(binding.passwordEdt.text.toString())){
                    binding.passwordHint.setText(R.string.thank_input_password)
                    binding.passwordHint.setTextColor(binding.passwordHint.context.resources.getColor(R.color.main_color))
                }
                else{
                    binding.passwordHint.setText(R.string.sign_in_password_bottom)
                    binding.passwordHint.setTextColor(binding.passwordHint.context.resources.getColor(R.color.gray_100))
                }
            }
        })
    }

    override fun onGetUserSuccess(response: LoginResponse) {
    }

    override fun onGetUserFailure(message: String) {
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onPostLoginSuccess(response: LoginResponse) {
        dismissLoadingDialog()
//        showCustomToast(response.message)
        if(response.code == 1000){

            Log.d("jwt", response.result.jwt)
            Log.d("userIdx", response.result.userIdx.toString())
            putSharedPrefUser(response.result.nickName)
            putSharedPref(response.result.jwt, response.result.userIdx)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            LocalBroadcastManager.getInstance(this).sendBroadcast(Intent("finish_explanation"))
            finish()
        }
        if(response.code == 2005){
            binding.emailEdt.setBackgroundDrawable(binding.emailEdt.getContext().getDrawable(R.drawable.edittext_warning_background_radius))
            binding.warningImg.visibility = View.VISIBLE
            binding.emailHint.setText(R.string.unregistered_email)
            binding.emailHint.setTextColor(binding.emailHint.context.resources.getColor(R.color.red))
        }
        if(response.code == 2004){
            binding.passwordEdt.setBackgroundDrawable(binding.passwordEdt.getContext().getDrawable(R.drawable.edittext_warning_background_radius))
            binding.warningImg2.visibility = View.VISIBLE
            binding.passwordHint.setText(R.string.unregistered_password)
            binding.passwordHint.setTextColor(binding.passwordHint.context.resources.getColor(R.color.red))
        }
    }

    override fun onPostLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    fun isValidPassword(password: String?): Boolean {
        val trimmedPassword = password?.trim().toString()
        val exp = Regex("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{8,20}.\$")
        return !trimmedPassword.isNullOrEmpty() && exp.matches(trimmedPassword)
    }

}