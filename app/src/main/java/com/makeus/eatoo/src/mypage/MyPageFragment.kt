package com.makeus.eatoo.src.mypage

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.ApplicationClass.Companion.GROUP_IDX
import com.makeus.eatoo.config.ApplicationClass.Companion.USER_IDX
import com.makeus.eatoo.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.databinding.FragmentMyPageBinding
import com.makeus.eatoo.src.home.group.groupmatesuggestion.TimeDialogInterface
import com.makeus.eatoo.src.mypage.finding_invite.FindInviteDialog
import com.makeus.eatoo.src.mypage.invite.InviteActivity
import com.makeus.eatoo.src.mypage.model.AccountDeleteResponse
import com.makeus.eatoo.src.mypage.model.MyPageResponse
import com.makeus.eatoo.src.mypage.profile.ProfileActivity
import com.makeus.eatoo.src.mypage.dialog.AccountWithdrawDialog
import com.makeus.eatoo.src.mypage.dialog.AccountWithdrawalDialogInterface
import com.makeus.eatoo.src.mypage.dialog.QuestionDialog
import com.makeus.eatoo.src.mypage.dialog.QuestionDialogInterface
import com.makeus.eatoo.src.review.my_review.MyReviewActivity
import com.makeus.eatoo.src.splash.SplashActivity
import com.makeus.eatoo.util.EatooCharList
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.getUserNickName


class MyPageFragment
    : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page),
    MyPageView, View.OnClickListener, AccountWithdrawalDialogInterface, QuestionDialogInterface, LogoutInterface  {

    override fun onResume() {
        super.onResume()
        getMyPage()
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()




    }

    private fun setOnClickListeners() {
        binding.logoutLayout.setOnClickListener (this)
        binding.reviewLayout.setOnClickListener (this)
        binding.profileLayout.setOnClickListener (this)
        binding.inviteLayout.setOnClickListener (this)
        binding.findInviteLayout.setOnClickListener (this)
        binding.accountSecessionLayout.setOnClickListener(this)
        binding.questionsLayout.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.logout_layout -> {
                val dialog = LogoutDialog(this,this)
                dialog.show()
            }
            R.id.review_layout -> {
                startActivity(Intent(activity, MyReviewActivity::class.java))
            }
            R.id.profile_layout -> {
                startActivity(Intent(activity, ProfileActivity::class.java))
            }
            R.id.invite_layout -> {
                startActivity(Intent(activity, InviteActivity::class.java))
            }
            R.id.find_invite_layout -> {
                val dialog = FindInviteDialog(this)
                dialog.show()
            }
            R.id.account_secession_layout -> {
                context?.let {
                    val dialog = AccountWithdrawDialog(it, this)
                    dialog.show()
                }
            }
            R.id.questions_layout -> {
                context?.let {
                    val dialog = QuestionDialog(it, this)
                    dialog.show()
                }
            }
        }
    }

    override fun onGoToGoogleStoreClicked() {
        context?.let {
            val marketIntent = Intent(Intent.ACTION_VIEW)
            marketIntent.data = Uri.parse("market://details?id=" + it.packageName)
            startActivity(marketIntent)
        }

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

    override fun onWithdrawClicked() {
        //server
        context?.let {
            showLoadingDialog(it)
            MyPageService(this).tryDeleteAccount(getUserIdx())
        }
    }

    override fun Setlogout(status: String) {
        if(status == "YES") {
            ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, null).apply()
            startActivity(Intent(activity, SplashActivity::class.java))
            activity?.finish()
        }
    }


    override fun onGetMyPageSuccess(response: MyPageResponse) {
        dismissLoadingDialog()

        val userColor = if (response.result.color == 0) 0 else response.result.color - 1
        val userChar = if (response.result.characters == 0) 0 else response.result.characters - 1
        binding.ivUserChar.setImageResource(EatooCharList[(userColor * 5) + userChar])
        binding.nickNameTxt.text = String.format(resources.getString(R.string.nickname_plus), response.result.nickName)

//        setKeyword(response.result.getUserKeywordRes)
    }


    override fun onGetMyPageFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message ?: resources.getString(R.string.failed_connection))
    }


    override fun onDeleteAccountSuccess(response: AccountDeleteResponse) {
        dismissLoadingDialog()
        ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, null).apply()
        ApplicationClass.sSharedPreferences.edit().putString(GROUP_IDX, null).apply()
        ApplicationClass.sSharedPreferences.edit().putString(USER_IDX, null).apply()
        activity?.let {
            ActivityCompat.finishAffinity(it)
        }

    }

    override fun onDeleteAccountFail(message: String?) {
        dismissLoadingDialog()

    }



}