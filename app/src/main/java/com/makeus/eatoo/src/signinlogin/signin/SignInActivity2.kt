package com.makeus.eatoo.src.signinlogin.signin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivitySingIn2Binding
import com.makeus.eatoo.src.signinlogin.signin.model.Check_Response
import com.makeus.eatoo.src.signinlogin.signin.model.SignInResponse


class SignInActivity2 : BaseActivity<ActivitySingIn2Binding>(ActivitySingIn2Binding::inflate) , SignInActivityView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.nextBtn.setOnClickListener {

            SignInActivityService(this).tryemailcheck(binding.emailEdt.text.toString())

        }


        if (intent.hasExtra("email") &&intent.hasExtra("password")&&intent.hasExtra("password_check")){

            val email = intent.getStringExtra("email").toString()
            val password = intent.getStringExtra("password").toString()
            val password_check = intent.getStringExtra("password_check").toString()
            binding.emailEdt.setText(email)
            binding.passwordEdt.setText(password)
            binding.passwordCheckEdt.setText(password_check)

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
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailEdt.text).matches() && binding.passwordEdt.text.length > 7 && binding.passwordCheckEdt.text.length > 7){

                    binding.nextBtn.isClickable = true // 버튼 클릭할수 있게
                    binding.nextBtn.isEnabled = true // 버튼 활성화
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.main_color))
                    binding.nextBtn.setBackgroundDrawable(binding.nextBtn.getContext().getDrawable(R.drawable.sign_in_btn_background_main_color))

                }
                else {
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.gray_100))
                    binding.nextBtn.setBackgroundDrawable(binding.nextBtn.getContext().getDrawable(R.drawable.sign_in_btn_background_gray))
                }

                if(android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailEdt.text).matches()){
                    binding.emailHint.setText(R.string.thank_input_email)
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

                if(android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailEdt.text).matches() && isValidPassword(binding.passwordEdt.text.toString()) && isValidPassword(binding.passwordCheckEdt.text.toString())){

                    binding.nextBtn.isClickable = true // 버튼 클릭할수 있게
                    binding.nextBtn.isEnabled = true // 버튼 활성화
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.main_color))
                    binding.nextBtn.setBackgroundDrawable(binding.nextBtn.getContext().getDrawable(R.drawable.sign_in_btn_background_main_color))

                }
                else {
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.gray_100))
                    binding.nextBtn.setBackgroundDrawable(binding.nextBtn.getContext().getDrawable(R.drawable.sign_in_btn_background_gray))
                }
                if(isValidPassword(binding.passwordEdt.text.toString())){
                    binding.passwordHint.setText(R.string.thank_input_password)
                    binding.passwordHint.setTextColor(binding.passwordHint.context.resources.getColor(R.color.main_color))
                }
                else {
                    binding.passwordHint.setText(R.string.sign_in_password_bottom)
                    binding.passwordHint.setTextColor(binding.passwordHint.context.resources.getColor(R.color.gray_100))
                }

            }
        })

        binding.passwordCheckEdt.addTextChangedListener(object : TextWatcher {
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
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailEdt.text).matches() && isValidPassword(binding.passwordEdt.text.toString()) && passwordCheck(binding.passwordCheckEdt.text.toString() , binding.passwordEdt.text.toString())){

                    binding.nextBtn.isClickable = true // 버튼 클릭할수 있게
                    binding.nextBtn.isEnabled = true // 버튼 활성화
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.main_color))
                    binding.nextBtn.setBackgroundDrawable(binding.nextBtn.getContext().getDrawable(R.drawable.sign_in_btn_background_main_color))

                }
                else {
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.gray_100))
                    binding.nextBtn.setBackgroundDrawable(binding.nextBtn.getContext().getDrawable(R.drawable.sign_in_btn_background_gray))
                }
                if(isValidPassword(binding.passwordCheckEdt.text.toString())){
                    binding.passwordCheckHint.setText(R.string.thank_input_password)
                    binding.passwordCheckHint.setTextColor(binding.passwordCheckHint.context.resources.getColor(R.color.main_color))
                }
                else{
                    binding.passwordCheckHint.setText(R.string.sign_in_password_bottom)
                    binding.passwordCheckHint.setTextColor(binding.passwordCheckHint.context.resources.getColor(R.color.gray_100))
                }
            }
        })

    }

    fun passwordCheck(password1:String,password2:String):Boolean{
        return (password1 == password2)
    }



    fun isValidPassword(password: String?): Boolean {
        val trimmedPassword = password?.trim().toString()
        val exp = Regex("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{8,20}.\$")
        return !trimmedPassword.isNullOrEmpty() && exp.matches(trimmedPassword)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onGetUserSuccess(response: Check_Response) {
        if(response.code == 1000){
            val intent1 = Intent(this, SignInActivity3::class.java)
            val name = intent.getStringExtra("name")
            val phone = intent.getStringExtra("phone")
            intent1.putExtra("name", name.toString())
            intent1.putExtra("phone", phone.toString())
            intent1.putExtra("email", binding.emailEdt.text.toString())
            intent1.putExtra("password", binding.passwordEdt.text.toString())
            intent1.putExtra("password_check", binding.passwordCheckEdt.text.toString())


            Log.d("인텐트 종류",""+binding.emailEdt.text+ binding.passwordEdt.text+binding.passwordCheckEdt.text)

            startActivity(intent1)
            finish()
        }
        if(response.code == 2017){
            binding.emailEdt.setBackgroundDrawable(binding.emailEdt.getContext().getDrawable(R.drawable.edittext_warning_background_radius))
            binding.warningImg.visibility = View.VISIBLE
            binding.emailHint.setText(R.string.registered_email)
            binding.emailHint.setTextColor(binding.emailHint.context.resources.getColor(R.color.red))
        }

    }

    override fun onGetUserFailure(message: String) {

    }

    override fun onPostSignUpSuccess(response: SignInResponse) {

    }

    override fun onPostSignUpFailure(message: String) {

    }
}