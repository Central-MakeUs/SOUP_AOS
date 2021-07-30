package com.makeus.eatoo.src.home.group.groupmatesuggestion

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.makeus.eatoo.R
import com.makeus.eatoo.databinding.DialogMateCompleteBinding

class MateSuggestionCompleteDialog (context: Context) : Dialog(context){
    private lateinit var binding : DialogMateCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogMateCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var mContext = context
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

    }
}