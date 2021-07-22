package com.makeus.eatoo.src.signinlogin.signin


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivitySingIn3Binding
import com.makeus.eatoo.src.signinlogin.login.LoginActivity
import com.makeus.eatoo.src.signinlogin.signin.model.SignInRequest
import com.makeus.eatoo.src.signinlogin.signin.model.SignInResponse
import com.makeus.eatoo.util.putSharedPref

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
                //showCustomToast("전달받은 인텐트 없음")
            }


            binding.nickNameEdt.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    //텍스트를 입력 후
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // 텍스트 입력 전
                }
                //
                @SuppressLint("ResourceAsColor")
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //텍스트 입력 중

                    if(binding.nickNameEdt.text.length > 1){

                        binding.nextBtn.isClickable = true // 버튼 클릭할수 있게
                        binding.nextBtn.isEnabled = true // 버튼 활성화
                        binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                            R.color.main_color))


                    }
                    else {
                        binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                            R.color.gray_100))
                    }
                    if(binding.nickNameEdt.text.length > 1){
                        binding.nickNameHint.setText(R.string.thank_input)
                        binding.nickNameHint.setTextColor(binding.nickNameHint.context.resources.getColor(R.color.main_color))
                    }
                    else if(binding.nickNameEdt.text.length <= 1){
                        binding.nickNameHint.setText(R.string.sign_in_name_bottom)
                        binding.nickNameHint.setTextColor(binding.nickNameHint.context.resources.getColor(R.color.black))
                    }


                }
            })

        }
    }

    override fun onGetUserSuccess(response: SignInResponse) {

    }

    override fun onGetUserFailure(message: String) {

    }

    @SuppressLint("ResourceAsColor")
    override fun onPostSignUpSuccess(response: SignInResponse) {
        dismissLoadingDialog()
        if(response.code == 1000) {
            putSharedPref(response.result.jwt, response.result.userIdx)
            //showCustomToast(response.message)
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
        if(response.code != 2033){
            binding.nickNameHint.setText(R.string.thank_input)
            binding.nickNameHint.setTextColor(binding.nickNameHint.context.resources.getColor(R.color.main_color))
        }
    }


    override fun onPostSignUpFailure(message: String) {
        dismissLoadingDialog()
        //showCustomToast(message)

    }
}