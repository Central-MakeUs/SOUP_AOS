package com.makeus.eatoo.src.signinlogin.signin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivitySignInBinding

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
            finish()
        }


        binding.nameEdt.addTextChangedListener(object : TextWatcher {
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

                if(binding.nameEdt.text.length > 1 && binding.phoneNumberEdt.text.length > 10){

                    binding.nextBtn.isClickable = true // 버튼 클릭할수 있게
                    binding.nextBtn.isEnabled = true // 버튼 활성화
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.main_color))


                }
                else {
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.gray_100))
                }
                if(binding.nameEdt.text.length > 1){
                    binding.nameHint.setText(R.string.thank_input)
                    binding.nameHint.setTextColor(binding.nameHint.context.resources.getColor(R.color.main_color))
                }
                else if(binding.nameEdt.text.length <= 1){
                    binding.nameHint.setText(R.string.sign_in_name_bottom)
                    binding.nameHint.setTextColor(binding.nameHint.context.resources.getColor(R.color.black))
                }


            }
        })

        binding.phoneNumberEdt.addTextChangedListener(object : TextWatcher {
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

                if(binding.nameEdt.text.length > 1 && binding.phoneNumberEdt.text.length > 10){

                    binding.nextBtn.isClickable = true // 버튼 클릭할수 있게
                    binding.nextBtn.isEnabled = true // 버튼 활성화
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.main_color))


                }
                else {
                    binding.nextBtn.setBackgroundColor(binding.nextBtn.context.resources.getColor(
                        R.color.gray_100))

                }
                if(binding.phoneNumberEdt.text.length > 10)
                {
                    binding.phoneHint.setText(R.string.thank_input)
                    binding.phoneHint.setTextColor(binding.phoneHint.context.resources.getColor(R.color.main_color))
                }
                else if(binding.phoneNumberEdt.text.length <= 10){
                    binding.phoneHint.setText(R.string.sign_in_password_bottom)
                    binding.phoneHint.setTextColor(binding.phoneHint.context.resources.getColor(R.color.black))
                }


            }
        })

    }
}