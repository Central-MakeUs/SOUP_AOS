package com.example.eatoo.src.main

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityMainBinding
import com.example.eatoo.src.main.model.UserResponse
import com.example.eatoo.util.getUserIdx
import com.example.eatoo.util.putSharedPrefUser

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),MainActivityView {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 네비게이션 호스트
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        // 네비게이션 컨트롤러
        val navController = navHostFragment.navController

        // 바인딩
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        MainService(this).tryGetUserData(getUserIdx())
    }

    override fun onGetUserDateSuccess(response: UserResponse) {
        Log.d("닉네임",response.result.nickName)
        putSharedPrefUser(response.result.nickName)
    }

    override fun onGetUserDateFail(message: String) {
    }

}