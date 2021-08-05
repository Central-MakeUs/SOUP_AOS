package com.makeus.eatoo.src.mypage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.ApplicationClass.Companion.GROUP_IDX
import com.makeus.eatoo.config.ApplicationClass.Companion.TOP_HEIGHT_LIMIT
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
import com.makeus.eatoo.src.mypage.keyword.DietKeywordActivity
import com.makeus.eatoo.src.mypage.model.GetUserKeywordRe
import com.makeus.eatoo.src.review.my_review.MyReviewActivity
import com.makeus.eatoo.src.splash.SplashActivity
import com.makeus.eatoo.util.EatooCharList
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.getUserNickName


class MyPageFragment
    : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page),
    MyPageView, View.OnClickListener, AccountWithdrawalDialogInterface, QuestionDialogInterface, LogoutInterface  {

    var parentX : Int? = 0
    var parentY : Int? = 0
    private var topHeightLimit = ApplicationClass.sSharedPreferences.getInt(ApplicationClass.TOP_HEIGHT_LIMIT, 0)

    override fun onResume() {
        super.onResume()

        calcViewLength()

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()



    }

    private fun calcViewLength() {
        val outMetrics = DisplayMetrics()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = activity?.display
            display?.getRealMetrics(outMetrics)
        } else {
            @Suppress("DEPRECATION")
            val display = activity?.windowManager?.defaultDisplay
            @Suppress("DEPRECATION")
            display?.getMetrics(outMetrics)
        }

        parentX = outMetrics.widthPixels
        parentY = outMetrics.heightPixels

        getMyPage()
    }

    private fun setOnClickListeners() {
        binding.logoutLayout.setOnClickListener (this)
        binding.reviewLayout.setOnClickListener (this)
        binding.profileLayout.setOnClickListener (this)
        binding.inviteLayout.setOnClickListener (this)
        binding.findInviteLayout.setOnClickListener (this)
        binding.accountSecessionLayout.setOnClickListener(this)
        binding.questionsLayout.setOnClickListener(this)
        binding.tvEditDietKeyword.setOnClickListener(this)
        binding.clNoKeyword.setOnClickListener (this)

        binding.findInviteBtn.setOnClickListener (this)
        binding.accountSecessionBtn.setOnClickListener (this)
        binding.questionsBtn.setOnClickListener (this)
        binding.logoutBtn.setOnClickListener (this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.logout_layout, R.id.logout_btn -> {
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
            R.id.find_invite_layout , R.id.find_invite_btn -> {
                val dialog = FindInviteDialog(this)
                dialog.show()
            }
            R.id.account_secession_layout, R.id.account_secession_btn -> {
                context?.let {
                    val dialog = AccountWithdrawDialog(it, this)
                    dialog.show()
                }
            }
            R.id.questions_layout, R.id.questions_btn -> {
                context?.let {
                    val dialog = QuestionDialog(it, this)
                    dialog.show()
                }
            }
            R.id.tv_edit_diet_keyword , R.id.cl_no_keyword-> {
                context?.let {
                    startActivity(Intent(it, DietKeywordActivity::class.java))
                }
            }

        }
    }

    private fun getMyPage() {
        context?.let {
            showLoadingDialog(it)
            MyPageService(this).tryGetMyPage(getUserIdx())
        }
    }

    /**
     * dialog click event
     */

    override fun onGoToGoogleStoreClicked() {
        context?.let {
            val marketIntent = Intent(Intent.ACTION_VIEW)
            marketIntent.data = Uri.parse("market://details?id=" + it.packageName)
            startActivity(marketIntent)
        }

    }

    override fun onWithdrawClicked() {
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

    /**
     * server result
     */


    override fun onGetMyPageSuccess(response: MyPageResponse) {
        dismissLoadingDialog()

        val userColor = if (response.result.color == 0) 0 else response.result.color - 1
        val userChar = if (response.result.characters == 0) 0 else response.result.characters - 1
        binding.ivUserChar.setImageResource(EatooCharList[(userColor * 5) + userChar])
        binding.nickNameTxt.text = String.format(resources.getString(R.string.nickname_plus), response.result.nickName)
        binding.nickNameTxt.isVisible = true

        if(response.result.getUserKeywordRes.isNotEmpty()) {
            binding.clNoKeyword.visibility = View.GONE
            getContainerLength(response.result.getUserKeywordRes)
        } else {
            binding.clNoKeyword.visibility = View.VISIBLE
            binding.tvInputKeyword1.text = String.format(resources.getString(R.string.input_diet_keyword1), getUserNickName())
        }

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
        ApplicationClass.sSharedPreferences.edit().putString(TOP_HEIGHT_LIMIT, null).apply()
        activity?.let {
            ActivityCompat.finishAffinity(it)
        }
    }

    override fun onDeleteAccountFail(message: String?) {
        dismissLoadingDialog()

    }

    /**
     * 식성 키워드
     */
    private fun getContainerLength(userKeywordRes: List<GetUserKeywordRe>) {
        binding.clDietKeywordContainer.post(object : Runnable{
            override fun run() {
                val containerHeight = binding.clDietKeywordContainer.height.toFloat()
                val containerWidth = binding.clDietKeywordContainer.width.toFloat()
                //Log.d("mypagefrag", "containerWidth : ${ containerWidth}, containerWidth : $containerHeight")
                calcKeywordLocation(userKeywordRes, containerWidth, containerHeight)
            }
        })
    }

    private fun calcKeywordLocation(userKeywordRes: List<GetUserKeywordRe>, containerWidth: Float, containerHeight: Float) {
        userKeywordRes.forEachIndexed { index, userDietKeyword ->
            if(index < 6){
                //Log.d("mypagefrag", "parentX : ${ parentX}, parentY : $parentY")
                //Log.d("mypagefrag", "userDietKeyword.x : ${ userDietKeyword.x}, userDietKeyword.y : ${userDietKeyword.y}")
                val keywordX = (userDietKeyword.x * containerWidth / parentX!!).toFloat()
                val keywordY = ((userDietKeyword.y + topHeightLimit)* containerHeight / parentY!!).toFloat()
                //Log.d("mypagefrag", "keywordX : ${ keywordX}, keywordY : $keywordY")
                makeKeywordCircle(userDietKeyword, keywordX, keywordY)
            }else return@forEachIndexed
        }
    }

    private fun makeKeywordCircle(
        userDietKeyword: GetUserKeywordRe,
        keywordX: Float,
        keywordY: Float
    ) {
        when(userDietKeyword.size){
            1 -> showKeywordCircle(userDietKeyword.name, userDietKeyword.isPrefer, resources.getDimension(R.dimen.diet_keyword_dialog_size1),
                resources.getDimension(R.dimen.diet_keyword_dialog_text_size1), keywordX, keywordY)
            2 -> showKeywordCircle(userDietKeyword.name, userDietKeyword.isPrefer, resources.getDimension(R.dimen.diet_keyword_dialog_size2),
                resources.getDimension(R.dimen.diet_keyword_dialog_text_size2), keywordX, keywordY)
            3 -> showKeywordCircle(userDietKeyword.name, userDietKeyword.isPrefer, resources.getDimension(R.dimen.diet_keyword_dialog_size3),
                resources.getDimension(R.dimen.diet_keyword_dialog_text_size3), keywordX, keywordY)
            4 -> showKeywordCircle(userDietKeyword.name, userDietKeyword.isPrefer, resources.getDimension(R.dimen.diet_keyword_dialog_size4),
                resources.getDimension(R.dimen.diet_keyword_dialog_text_size4), keywordX, keywordY)
            5 -> showKeywordCircle(userDietKeyword.name, userDietKeyword.isPrefer, resources.getDimension(R.dimen.diet_keyword_dialog_size5),
                resources.getDimension(R.dimen.diet_keyword_dialog_text_size5), keywordX, keywordY)
        }
    }

    @SuppressLint("InflateParams")
    private fun showKeywordCircle(name: String, isPrefer: String, size: Float, sizeText: Float, keywordX: Float, keywordY: Float) {
        val mInflater: LayoutInflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val dynamicCircle: View = mInflater.inflate(R.layout.view_diet_keyword, null)
        (dynamicCircle as TextView).apply {
            layoutParams = LinearLayout.LayoutParams(size.toInt(), size.toInt())
            background = if(isPrefer == "Y") ContextCompat.getDrawable(context, R.drawable.background_keyword_pos)
            else ContextCompat.getDrawable(context, R.drawable.background_keyword_neg)
            this.post(object : Runnable {
                override fun run() {
                    val tvHeight = dynamicCircle.height
                    Log.d("mypagefrag", tvHeight.toString())
                    x = keywordX
                    y = keywordY - tvHeight/2
                    text = name
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeText)
                }
            })
        }
        binding.clDietKeywordContainer.addView(dynamicCircle, binding.clDietKeywordContainer.childCount)
    }


}