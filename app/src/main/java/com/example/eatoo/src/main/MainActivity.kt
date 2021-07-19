package com.example.eatoo.src.main

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityMainBinding
import com.example.eatoo.src.home.HomeFragment
import com.example.eatoo.src.main.model.UserResponse
import com.example.eatoo.src.mypage.MyPageFragment
import com.example.eatoo.src.suggestion.SuggestionFragment
import com.example.eatoo.src.wishlist.WishListFragment
import com.example.eatoo.util.getUserIdx
import com.example.eatoo.util.putSharedPrefUser
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),MainActivityView {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        // 네비게이션 호스트
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
//
//        // 네비게이션 컨트롤러
//        val navController = navHostFragment.navController
//
//        // 바인딩
//        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        supportFragmentManager.beginTransaction().replace(R.id.nav_host, HomeFragment()).commitAllowingStateLoss()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.homeFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host, HomeFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }

                    R.id.suggestionFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host, SuggestionFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.wishlistFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host, WishListFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.myPageFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host, MyPageFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }

                }
                false
            })

        MainService(this).tryGetUserData(getUserIdx())
    }

    override fun onGetUserDateSuccess(response: UserResponse) {
        Log.d("닉네임",response.result.nickName)
        putSharedPrefUser(response.result.nickName)
    }

    override fun onGetUserDateFail(message: String) {
    }

}
