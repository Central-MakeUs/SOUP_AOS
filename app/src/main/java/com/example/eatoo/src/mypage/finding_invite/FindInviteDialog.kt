package com.example.eatoo.src.mypage.finding_invite

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.example.eatoo.R
import com.example.eatoo.databinding.DialogFindInviteBinding
import com.example.eatoo.src.home.group.vote.GroupVoteFragment
import com.example.eatoo.src.mypage.MyPageFragment
import com.example.eatoo.src.mypage.finding_invite.model.FindGroupRequest
import com.example.eatoo.src.mypage.finding_invite.model.FindGroupResponse
import com.example.eatoo.util.getUserIdx
import java.security.acl.Group


class FindInviteDialog( val view: MyPageFragment) : Dialog(view.requireContext()) ,FindInviteDialogView {

    private lateinit var binding : DialogFindInviteBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogFindInviteBinding.inflate(layoutInflater)
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
            val requeststring = FindGroupRequest(binding.inviteCodeEdt.text.toString())
            FindInviteDialogService(this).tryPostParticipate(requeststring , getUserIdx())
            dismiss()
        }

    }

    override fun onPostGroupParticipateSuccess(response: FindGroupResponse) {
    }

    override fun onPostGroupParticipateFailure(message: String) {
    }


}