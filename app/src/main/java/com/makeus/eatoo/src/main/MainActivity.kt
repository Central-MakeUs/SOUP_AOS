package com.makeus.eatoo.src.main

import android.os.Bundle
import android.util.Log
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityMainBinding
import com.makeus.eatoo.src.home.HomeFragment
import com.makeus.eatoo.src.main.model.UserResponse
import com.makeus.eatoo.src.mypage.MyPageFragment
import com.makeus.eatoo.src.suggestion.SuggestionFragment
import com.makeus.eatoo.src.wishlist.WishListFragment
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.putSharedPrefUser
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
        MainService(this).tryGetUserData(getUserIdx())
        showLoadingDialog(this)

        supportFragmentManager.beginTransaction().replace(R.id.nav_host, HomeFragment()).commitAllowingStateLoss()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {

                    R.id.homeFragment -> {
                        MainService(this).tryGetUserData(getUserIdx())
                        showLoadingDialog(this)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host, HomeFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }

                    R.id.suggestionFragment -> {
                        MainService(this).tryGetUserData(getUserIdx())
                        showLoadingDialog(this)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host, SuggestionFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.wishlistFragment -> {
                        MainService(this).tryGetUserData(getUserIdx())
                        showLoadingDialog(this)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host, WishListFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.myPageFragment -> {
                        MainService(this).tryGetUserData(getUserIdx())
                        showLoadingDialog(this)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host, MyPageFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }

                }
                false
            })

    }

    override fun onGetUserDateSuccess(response: UserResponse) {
        dismissLoadingDialog()
        Log.d("닉네임",response.result.nickName)
        putSharedPrefUser(response.result.nickName)
    }

    override fun onGetUserDateFail(message: String) {
    }

}
