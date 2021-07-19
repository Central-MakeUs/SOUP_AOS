package com.example.eatoo.src.signinlogin.signin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivitySingIn2Binding


class SignInActivity2 : BaseActivity<ActivitySingIn2Binding>(ActivitySingIn2Binding::inflate)  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.nextBtn.setOnClickListener {

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
        }


        binding.emailEdt.addTextChangedListener(object : TextWatcher {
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

                if(binding.emailEdt.text.length > 6 && binding.passwordEdt.text.length > 7 && binding.passwordCheckEdt.text.length > 7){

                    binding.nextBtn.isClickable = true // 버튼 클릭할수 있게
                    binding.nextBtn.isEnabled = true // 버튼 활성화
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.main_color))

                }
                else {
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.gray_100))
                }
                if(binding.emailEdt.text.length > 6){
                    binding.emailHint.setText(R.string.thank_input)
                    binding.emailHint.setTextColor(binding.emailHint.context.resources.getColor(R.color.main_color))
                }
                else if(binding.emailEdt.text.length <= 6) {
                    binding.emailHint.setText(R.string.sign_in_email_bottom)
                    binding.emailHint.setTextColor(binding.emailHint.context.resources.getColor(R.color.black))
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
            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 중

                if(binding.emailEdt.text.length > 6 && binding.passwordEdt.text.length > 7 && binding.passwordCheckEdt.text.length > 7){

                    binding.nextBtn.isClickable = true // 버튼 클릭할수 있게
                    binding.nextBtn.isEnabled = true // 버튼 활성화
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.main_color))

                }
                else {
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.gray_100))
                }
                if( binding.passwordEdt.text.length > 7){
                    binding.passwordHint.setText(R.string.thank_input_password)
                    binding.passwordHint.setTextColor(binding.passwordHint.context.resources.getColor(R.color.main_color))
                }
                else if( binding.passwordEdt.text.length <= 7) {
                    binding.passwordHint.setText(R.string.sign_in_password_bottom)
                    binding.passwordHint.setTextColor(binding.passwordHint.context.resources.getColor(R.color.black))
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
            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 중

                if(binding.emailEdt.text.length > 6 && binding.passwordEdt.text.length > 7 && binding.passwordCheckEdt.text.length > 7){

                    binding.nextBtn.isClickable = true // 버튼 클릭할수 있게
                    binding.nextBtn.isEnabled = true // 버튼 활성화
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.main_color))

                }
                else {
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.gray_100))
                }
                if( binding.passwordCheckEdt.text.length > 7){
                    binding.passwordCheckHint.setText(R.string.thank_input_password)
                    binding.passwordCheckHint.setTextColor(binding.passwordCheckHint.context.resources.getColor(R.color.main_color))
                }
                else if( binding.passwordCheckEdt.text.length <= 7) {
                    binding.passwordCheckHint.setText(R.string.sign_in_password_bottom)
                    binding.passwordCheckHint.setTextColor(binding.passwordCheckHint.context.resources.getColor(R.color.black))
                }
            }
        })

    }
}