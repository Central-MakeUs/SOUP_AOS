package com.makeus.eatoo.src.mypage

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.core.content.ContextCompat.startActivity
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.databinding.DialogFindInviteBinding
import com.makeus.eatoo.databinding.DialogLogoutBinding
import com.makeus.eatoo.src.home.group.groupmatesuggestion.TimeDialogInterface
import com.makeus.eatoo.src.mypage.finding_invite.FindInviteDialogService
import com.makeus.eatoo.src.mypage.finding_invite.FindInviteDialogView
import com.makeus.eatoo.src.mypage.finding_invite.model.FindGroupRequest
import com.makeus.eatoo.src.splash.SplashActivity
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.getUserNickName


class LogoutDialog( val view: MyPageFragment, val dialogInteface : LogoutInterface) : Dialog(view.requireContext()) {

    private lateinit var binding: DialogLogoutBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCancelable(false)
        setCanceledOnTouchOutside(true)
        val window = window
        if (window != null) {
            // 백그라운드 투명
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.statusBarColor = Color.WHITE
            val params = window.attributes
            params.width = WindowManager.LayoutParams.WRAP_CONTENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            // 열기&닫기 시 애니메이션 설정
            params.windowAnimations = R.style.AnimationPopupStyle
            window.attributes = params
            // UI 상단 정렬
            window.setGravity(Gravity.CENTER)
        }

        binding.btnDialogConfirm.setOnClickListener {
            ApplicationClass.sSharedPreferences.edit().putString(ApplicationClass.X_ACCESS_TOKEN, null).apply()
            dialogInteface.Setlogout("YES")
            dismiss()
        }
        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }

        binding.tvQuery1.text = getUserNickName() + binding.tvQuery1.text

    }
}