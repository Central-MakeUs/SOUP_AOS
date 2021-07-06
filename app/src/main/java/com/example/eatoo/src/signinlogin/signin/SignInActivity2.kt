package com.example.eatoo.src.signinlogin.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    }
}