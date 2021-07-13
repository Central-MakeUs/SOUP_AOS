package com.example.eatoo.src.home.group.groupmatesuggestion

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.example.eatoo.R
import com.example.eatoo.databinding.ActivityTimeDialogBinding

class TimeDialogActivity (context: Context) : Dialog(context) {
    private lateinit var binding: ActivityTimeDialogBinding
    private lateinit var hour : String
    private lateinit var minute : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTimeDialogBinding.inflate(layoutInflater)

        var mContext = context
        setCancelable(false)
        setCanceledOnTouchOutside(true)
        val window = window
        if (window != null) {
            // 백그라운드 투명
            window.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            window.statusBarColor = Color.WHITE
            val params = window.attributes

            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT

            // 열기&닫기 시 애니메이션 설정
            params.windowAnimations = R.style.AnimationPopupStyle
            window.attributes = params
            // UI 상단 정렬
            window.setGravity(Gravity.CENTER)
        }

        val mTimePicker = binding.startTimePicker
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = "" + mTimePicker.hour
            minute = "" + mTimePicker.minute
        } else {
            hour = mTimePicker.currentHour.toString()
            minute = mTimePicker.currentMinute.toString()
        }

        mTimePicker.setOnTimeChangedListener { timePicker, hour, min ->

        }




    }

    private fun getAmPm(hour: Int): String? {
        return if (hour >= 12) "AM" else "PM"
    }

    private fun setMinute(min: Int) {
        if (min >= 10) minute = min.toString() + "" else minute = "0$min"
    }
}