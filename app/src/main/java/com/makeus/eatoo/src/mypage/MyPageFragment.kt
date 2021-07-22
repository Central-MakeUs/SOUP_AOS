package com.makeus.eatoo.src.mypage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.databinding.FragmentMyPageBinding
import com.makeus.eatoo.src.mypage.finding_invite.FindInviteDialog
import com.makeus.eatoo.src.mypage.invite.InviteActivity
import com.makeus.eatoo.src.mypage.model.MyPageResponse
import com.makeus.eatoo.src.mypage.profile.ProfileActivity
import com.makeus.eatoo.src.review.my_review.MyReviewActivity
import com.makeus.eatoo.src.splash.SplashActivity
import com.makeus.eatoo.util.EatooCharList
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.getUserNickName


class MyPageFragment
    : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page),
    MyPageView {

    override fun onResume() {
        super.onResume()
        getMyPage()
    }

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

    private fun getMyPage() {
        context?.let {
            showLoadingDialog(it)
            MyPageService(this).tryGetMyPage(getUserIdx())
        }
    }

    //    private fun setKeyword(userKeywordList: List<GetUserKeywordRe>) {
//        userKeywordList.forEach {
//            if(it.isPrefer == YES) setPositiveKeyword(it)
//            else setNegativeKeyword(it)
//        }
//    }
//
//    private fun setPositiveKeyword(keywordRe: GetUserKeywordRe) {
//        val positiveChip = Chip(requireContext())
//        positiveChip.apply {
//            text = keywordRe.name
//            setChipBackgroundColorResource(R.color.positive_orange)
//            isCloseIconVisible = false
//            setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
//            chipStrokeColor = ContextCompat.getColorStateList(requireContext(), R.color.main_orange)
//        }
//        binding.chipgroupMypage.addView(positiveChip)
//    }
//
//    private fun setNegativeKeyword(keywordRe: GetUserKeywordRe) {
//        val negativeChip = Chip(requireContext())
//        negativeChip.apply {
//            text = keywordRe.name
//            setChipBackgroundColorResource(R.color.white)
//            isCloseIconVisible = false
//            setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
//            chipStrokeColor = ContextCompat.getColorStateList(requireContext(), R.color.dialog_cancel_text)
//        }
//        binding.chipgroupMypage.addView(negativeChip)
//    }



    override fun onGetMyPageSuccess(response: MyPageResponse) {
        dismissLoadingDialog()
        val userColor = if (response.result.color == 0) 0 else response.result.color - 1
        val userChar = if (response.result.characters == 0) 0 else response.result.characters - 1
        binding.ivUserChar.setImageResource(EatooCharList[(userColor * 5) + userChar])

//        setKeyword(response.result.getUserKeywordRes)
    }


    override fun onGetMyPageFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message ?: resources.getString(R.string.failed_connection))
    }


}