package com.example.eatoo.src.mypage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentMyPageBinding
import com.example.eatoo.src.home.create_group.model.Keyword
import com.example.eatoo.src.home.group.vote.create_vote.CreateVoteActivity.Companion.YES
import com.example.eatoo.src.mypage.finding_invite.FindInviteDialog
import com.example.eatoo.src.mypage.invite.InviteActivity
import com.example.eatoo.src.mypage.model.GetUserKeywordRe
import com.example.eatoo.src.mypage.model.MyPageResponse
import com.example.eatoo.src.mypage.profile.ProfileActivity
import com.example.eatoo.src.review.my_review.MyReviewActivity
import com.example.eatoo.src.splash.SplashActivity
import com.example.eatoo.util.EatooCharList
import com.example.eatoo.util.getUserIdx
import com.example.eatoo.util.getUserNickName
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.chip.Chip
import kotlin.math.roundToInt


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