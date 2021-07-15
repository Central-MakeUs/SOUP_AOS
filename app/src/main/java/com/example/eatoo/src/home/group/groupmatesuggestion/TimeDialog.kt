package com.example.eatoo.src.home.group.groupmatesuggestion

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import com.example.eatoo.R
import com.example.eatoo.databinding.DialogTimeBinding


class TimeDialog(context: Context, val dialogInteface : TimeDialogInterface, val Type  : String) : Dialog(context) {

    private lateinit var binding : DialogTimeBinding

    private lateinit var Hour : String
    private lateinit var minute : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            Hour = "" + mTimePicker.hour
            minute = "" + mTimePicker.minute
        } else {
            Hour = mTimePicker.currentHour.toString()
            minute = mTimePicker.currentMinute.toString()
        }
        binding.cancleBtn.setOnClickListener {
            dismiss()
        }
        binding.checkBtn.setOnClickListener {
            //startActivityForResult()
            Log.d("선택된 시간",Hour + minute)
            if(Type == "start"){
                dialogInteface.onSetStartTime(Hour, minute)
            }
            else if(Type == "end"){
                dialogInteface.onSetEndTime(Hour, minute)
            }
            else if(Type == "limit"){
                dialogInteface.onSetLimitTime(Hour, minute)
            }
            dismiss()
        }
        mTimePicker.setOnTimeChangedListener { timePicker, hour, min ->
            if(hour < 10){
                Hour = "0$hour"
            }
            else{
                Hour = hour.toString()
            }
            if(min < 10){
                minute = "0$min"
            }
            else{
                minute = min.toString()
            }

        }
    }
    private fun getAmPm(hour: Int): String? {
        return if (hour >= 12) "AM" else "PM"
    }
    private fun setMinute(min: Int) {
        if (min >= 10) minute = min.toString() + "" else minute = "0$min"
    }
}