package com.makeus.eatoo.src.mypage.invite

import android.annotation.SuppressLint
import android.app.Application
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.databinding.DialogInviteBinding
import com.makeus.eatoo.util.getGroupName


class InviteDialog(context: Context , val code : String) : Dialog(context) {

    private lateinit var binding : DialogInviteBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogInviteBinding.inflate(layoutInflater)
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
        binding.tvQuery1.text = String.format(ApplicationClass.applicationResources.getString(R.string.invite_dialog_query), getGroupName())
        binding.inviteCode1.text = code[0].toString()
        binding.inviteCode2.text = code[1].toString()
        binding.inviteCode3.text = code[2].toString()
        binding.inviteCode4.text = code[3].toString()

        binding.btnDialogConfirm.setOnClickListener {
            copyStr(context, code)
            dismiss()
        }

    }

    //클립보드에 코드 복사
    private fun copyStr(context: Context,str : String){
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        val clipData = ClipData.newPlainText("code", str)
        clipboardManager!!.setPrimaryClip(clipData)
        Toast.makeText(context, "클립보드에 복사되었습니다.", Toast.LENGTH_LONG).show()
    }

}