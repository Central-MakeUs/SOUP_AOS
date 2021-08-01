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

        if (intent.hasExtra("name") &&intent.hasExtra("phone")){

            val name = intent.getStringExtra("name").toString()
            val phone = intent.getStringExtra("phone").toString()
            binding.nameEdt.setText(name)
            binding.phoneNumberEdt.setText(phone)

        }


        binding.nameEdt.addTextChangedListener(object : TextWatcher {
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

                if((isValidNameK(binding.nameEdt.text.toString())|| isValidName(binding.nameEdt.text.toString())||isValidNameE(binding.nameEdt.text.toString()) ) && isValidPhone(binding.phoneNumberEdt.text.toString())){

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
                if(isValidNameK(binding.nameEdt.text.toString())|| isValidName(binding.nameEdt.text.toString())||isValidNameE(binding.nameEdt.text.toString()) ){
                    binding.nameHint.setText(R.string.thank_input)
                    binding.nameHint.setTextColor(binding.nameHint.context.resources.getColor(R.color.main_color))
                }
                else{
                    binding.nameHint.setText(R.string.sign_in_name_bottom)
                    binding.nameHint.setTextColor(binding.nameHint.context.resources.getColor(R.color.gray_100))
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
            @SuppressLint("ResourceAsColor", "UseCompatLoadingForDrawables")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 중

                if( (isValidNameK(binding.nameEdt.text.toString())|| isValidName(binding.nameEdt.text.toString())||isValidNameE(binding.nameEdt.text.toString()) ) && isValidPhone(binding.phoneNumberEdt.text.toString()) ){

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
                if(isValidPhone(binding.phoneNumberEdt.text.toString()))
                {
                    binding.phoneHint.setText(R.string.thank_input)
                    binding.phoneHint.setTextColor(binding.phoneHint.context.resources.getColor(R.color.main_color))
                }
                else{
                    binding.phoneHint.setText(R.string.sign_in_phone_bottom)
                    binding.phoneHint.setTextColor(binding.phoneHint.context.resources.getColor(R.color.gray_100))
                }


            }
        })

    }

    fun isValidNameK(name: String?): Boolean {
        val trimmedName = name?.trim().toString()
        val exp = Regex("^[가-힣]+\$")
        return !trimmedName.isNullOrEmpty() && exp.matches(trimmedName)
    }

    fun isValidNameE(name: String?): Boolean {
        val trimmedName = name?.trim().toString()
        val exp = Regex("^[a-zA-Z]+\$")
        return !trimmedName.isNullOrEmpty() && exp.matches(trimmedName)
    }


    fun isValidName(name: String?): Boolean {
        val trimmedName = name?.trim().toString()
        val exp = Regex("^[가-힣a-zA-Z]+\$")
        return !trimmedName.isNullOrEmpty() && exp.matches(trimmedName)
    }



    fun isValidPhone(phone: String?): Boolean {
        val trimmedPhone = phone?.trim().toString()
        val exp = Regex("^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})\$")
        return !trimmedPhone.isNullOrEmpty() && exp.matches(trimmedPhone)
    }
}