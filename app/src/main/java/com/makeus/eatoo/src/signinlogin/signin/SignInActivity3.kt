package com.makeus.eatoo.src.signinlogin.signin


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
import com.makeus.eatoo.databinding.ActivitySingIn3Binding
import com.makeus.eatoo.src.main.MainActivity
import com.makeus.eatoo.src.signinlogin.login.LoginActivity
import com.makeus.eatoo.src.signinlogin.signin.model.Check_Response
import com.makeus.eatoo.src.signinlogin.signin.model.SignInRequest
import com.makeus.eatoo.src.signinlogin.signin.model.SignInResponse
import com.makeus.eatoo.util.putSharedPref
import com.makeus.eatoo.util.putSharedPrefUser

class SignInActivity3 : BaseActivity<ActivitySingIn3Binding>(ActivitySingIn3Binding::inflate), SignInActivityView{



    lateinit var name : String
    lateinit var phone : String
    lateinit var email : String
    lateinit var password : String
    lateinit var password_check : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.nextBtn.setOnClickListener {


            if(intent.hasExtra("name") &&intent.hasExtra("phone")&&intent.hasExtra("email")&&intent.hasExtra("password")&&intent.hasExtra("password_check")){

                name = intent.getStringExtra("name").toString()
                phone = intent.getStringExtra("phone").toString()
                email = intent.getStringExtra("email").toString()
                password = intent.getStringExtra("password").toString()
                password_check = intent.getStringExtra("password_check").toString()

                val postRequest = SignInRequest(name = name,phone = phone,email = email, password = password, passwordConfirm = password_check, nickName = binding.nickNameEdt.text.toString() )
                Log.d("요청사항", ""+ postRequest)
                showLoadingDialog(this)
                SignInActivityService(this).tryPostSignUp(postRequest)

            }



        }

        binding.nickNameEdt.addTextChangedListener(object : TextWatcher {
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
                Log.d("길이",""+binding.nickNameEdt.text.length)
                binding.warningImg.visibility = View.GONE
                binding.nickNameEdt.setBackgroundDrawable(binding.nickNameEdt.getContext().getDrawable(R.drawable.edittext_background_radius))
                if(isValidNickname(binding.nickNameEdt.text.toString()) ||isValidNicknameE(binding.nickNameEdt.text.toString())|| isValidNicknameK(binding.nickNameEdt.text.toString())){

                    binding.nextBtn.isClickable = true // 버튼 클릭할수 있게
                    binding.nextBtn.isEnabled = true // 버튼 활성화
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.main_color))
                    binding.nickNameHint.setText(R.string.thank_input)
                    binding.nickNameHint.setTextColor(binding.nickNameHint.context.resources.getColor(R.color.main_color))
                    binding.nextBtn.setBackgroundDrawable(binding.nextBtn.getContext().getDrawable(R.drawable.sign_in_btn_background_main_color))


                }
                else {
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.gray_100))
                    binding.nickNameHint.setText(R.string.sign_in_password_bottom)
                    binding.nickNameHint.setTextColor(binding.nickNameHint.context.resources.getColor(R.color.gray_100))
                    binding.nextBtn.setBackgroundDrawable(binding.nextBtn.getContext().getDrawable(R.drawable.sign_in_btn_background_gray))

                }

            }
        })
    }

    fun isValidNicknameK(nickname: String?): Boolean {
        val trimmedNickname = nickname?.trim().toString()
        val exp = Regex("^[가-힣]+\$")
        return !trimmedNickname.isNullOrEmpty() && exp.matches(trimmedNickname)
    }

    fun isValidNicknameE(nickname: String?): Boolean {
        val trimmedNickname = nickname?.trim().toString()
        val exp = Regex("^[a-zA-Z]+\$")
        return !trimmedNickname.isNullOrEmpty() && exp.matches(trimmedNickname)
    }


    fun isValidNickname(nickname: String?): Boolean {
        val trimmedNickname = nickname?.trim().toString()
        val exp = Regex("^[가-힣a-zA-Z]+\$")
        return !trimmedNickname.isNullOrEmpty() && exp.matches(trimmedNickname)
    }


    override fun onGetUserSuccess(response: Check_Response) {

    }

    override fun onGetUserFailure(message: String) {

    }

    @SuppressLint("ResourceAsColor", "UseCompatLoadingForDrawables")
    override fun onPostSignUpSuccess(response: SignInResponse) {
        dismissLoadingDialog()
        if(response.code == 1000) {
            putSharedPref(response.result.jwt, response.result.userIdx)
            //showCustomToast(response.message)
            putSharedPrefUser(binding.nickNameEdt.text.toString())
            putSharedPref(response.result.jwt, response.result.userIdx)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            LocalBroadcastManager.getInstance(this).sendBroadcast(Intent("finish_explanation"))
            finish()
        }
        else if(response.code == 2033){
            binding.nickNameEdt.setBackgroundDrawable(binding.nickNameEdt.getContext().getDrawable(R.drawable.edittext_warning_background_radius))
            binding.warningImg.visibility = View.VISIBLE
            binding.nickNameHint.setText(R.string.registered_nickname)
            binding.nickNameHint.setTextColor(binding.nickNameHint.context.resources.getColor(R.color.red))
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