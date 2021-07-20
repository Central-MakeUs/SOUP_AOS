package com.example.eatoo.src.mypage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentMyPageBinding
import com.example.eatoo.src.mypage.finding_invite.FindInviteDialog
import com.example.eatoo.src.mypage.invite.InviteActivity
import com.example.eatoo.src.mypage.profile.ProfileActivity
import com.example.eatoo.src.review.my_review.MyReviewActivity
import com.example.eatoo.src.splash.SplashActivity
import com.example.eatoo.util.getUserNickName


class MyPageFragment
    : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page){

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutLayout.setOnClickListener {
            ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, null).apply()
            startActivity(Intent(activity, SplashActivity::class.java))
        }

        binding.reviewLayout.setOnClickListener {
            startActivity(Intent(activity, MyReviewActivity::class.java))
        }

        binding.profileLayout.setOnClickListener {
            startActivity(Intent(activity, ProfileActivity::class.java))
        }
        binding.inviteLayout.setOnClickListener {
            startActivity(Intent(activity, InviteActivity::class.java))
        }
        binding.findInviteLayout.setOnClickListener {
            val dialog = FindInviteDialog(this)
            dialog.show()
        }

        binding.nickNameTxt.text = getUserNickName() + binding.nickNameTxt.text


    }


}