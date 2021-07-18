package com.example.eatoo.src.mypage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentMyPageBinding
import com.example.eatoo.src.mypage.profile.ProfileActivity
import com.example.eatoo.src.mypage.profile.ProfileView
import com.example.eatoo.src.review.my_review.MyReviewActivity
import com.example.eatoo.src.splash.SplashActivity
import com.example.eatoo.util.getUserNickName


class MyPageFragment
    : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page),
    View.OnClickListener{

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setOnClickListeners()

        binding.nickNameTxt.text = getUserNickName() + binding.nickNameTxt.text


    }

    private fun setOnClickListeners() {
        binding.logoutLayout.setOnClickListener(this)
        binding.reviewLayout.setOnClickListener(this)
        binding.profileLayout.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.review_layout -> startActivity(
                Intent(
                    requireContext(),
                    MyReviewActivity::class.java
                )
            )
            R.id.profile_layout -> startActivity(
                Intent(
                    requireContext(),
                    ProfileActivity::class.java
                )
            )
            R.id.logout_layout -> {
                ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, "").apply()
                startActivity(Intent(activity, SplashActivity::class.java))
            }
        }

    }
}