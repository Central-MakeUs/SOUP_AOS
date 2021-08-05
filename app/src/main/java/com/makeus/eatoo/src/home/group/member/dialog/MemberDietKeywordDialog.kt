package com.makeus.eatoo.src.home.group.member.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.databinding.DialogMemberDietKeywordBinding
import com.makeus.eatoo.src.home.group.member.model.GroupMemberDetailResult
import com.makeus.eatoo.src.home.group.member.model.MemberDietKeyword
import com.makeus.eatoo.util.EatooCharList

class MemberDietKeywordDialog (
    context : Context,
    val memberInfo : GroupMemberDetailResult,
    val parentX : Int,
    val parentY : Int
) : Dialog(context) {
    private lateinit var binding: DialogMemberDietKeywordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogMemberDietKeywordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Log.d("memberKeywordDialog", memberInfo.toString())

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.tvMemberName.text = memberInfo.nickName
        binding.btnDialogConfirm.setOnClickListener { dismiss() }

        setMemberChar()
        getDialogLength()
    }

    private fun setMemberChar() {
        val memberColor = if(memberInfo.color != 0) memberInfo.color -1 else 0
        val memberChar = if(memberInfo.characters != 0) memberInfo.characters -1 else 0
        binding.ivMember.setImageResource(EatooCharList[(memberColor*5) + memberChar])

        if(memberInfo.singleStatus == "ON"){
            binding.ivMemberContainer.setImageResource(R.drawable.background_member_gray)
            val grayScale = floatArrayOf(0.2989f, 0.5870f, 0.1140f,
                0F, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.0000F, 0.0000F, 0.0000F, 1f, 0f)
            val matrix = ColorMatrixColorFilter(grayScale)
            binding.ivMember.colorFilter= matrix
        }else {
            binding.ivMemberContainer.setImageResource(R.drawable.background_member_orange)
        }
    }

    private fun getDialogLength() {
        binding.clDialogContainer.post(object : Runnable{
            override fun run() {
                val containerHeight = binding.clDialogContainer.height.toFloat()
                val containerWidth = binding.clDialogContainer.width.toFloat()
                //Log.d("memberKeywordDialog", "containerWidth : ${ containerWidth}, containerWidth : $containerHeight")
                calcKeywordLocation(containerWidth, containerHeight)
            }
        })
    }

    private fun calcKeywordLocation(containerWidth: Float, containerHeight: Float) {

        memberInfo.getUserKeywordRes.forEachIndexed { index, memberDietKeyword ->

            if(memberDietKeyword.x != 0.0 && memberDietKeyword.y != 0.0 ){
                //Log.d("memberKeywordDialog", "parentX : ${ parentX}, parentY : $parentY")
                //Log.d("memberKeywordDialog", "memberDietKeyword.x : ${ memberDietKeyword.x}, memberDietKeyword.y : ${memberDietKeyword.y}")
                val keywordX = (memberDietKeyword.x * containerWidth / parentX!!).toFloat()
                val keywordY = (memberDietKeyword.y * containerHeight / parentY!!).toFloat()
                //Log.d("memberKeywordDialog", "keywordX : ${ keywordX}, keywordY : $keywordY")
                makeKeywordCircle(memberDietKeyword, keywordX, keywordY)
            }
        }

    }

    private fun makeKeywordCircle(dietKeywordInfo: MemberDietKeyword, keywordX : Float, keywordY : Float) {
        Log.d("dietKeywordAct", dietKeywordInfo.toString())
        when(dietKeywordInfo.size){
            1 -> showKeywordCircle(dietKeywordInfo.name, dietKeywordInfo.isPrefer, ApplicationClass.applicationResources.getDimension(R.dimen.diet_keyword_dialog_size1),
                ApplicationClass.applicationResources.getDimension(R.dimen.diet_keyword_dialog_text_size1), keywordX, keywordY)
            2 -> showKeywordCircle(dietKeywordInfo.name, dietKeywordInfo.isPrefer, ApplicationClass.applicationResources.getDimension(R.dimen.diet_keyword_dialog_size2),
                ApplicationClass.applicationResources.getDimension(R.dimen.diet_keyword_dialog_text_size2), keywordX, keywordY)
            3 -> showKeywordCircle(dietKeywordInfo.name, dietKeywordInfo.isPrefer, ApplicationClass.applicationResources.getDimension(R.dimen.diet_keyword_dialog_size3),
                ApplicationClass.applicationResources.getDimension(R.dimen.diet_keyword_dialog_text_size3), keywordX, keywordY)
            4 -> showKeywordCircle(dietKeywordInfo.name, dietKeywordInfo.isPrefer, ApplicationClass.applicationResources.getDimension(R.dimen.diet_keyword_dialog_size4),
                ApplicationClass.applicationResources.getDimension(R.dimen.diet_keyword_dialog_text_size4), keywordX, keywordY)
            5 -> showKeywordCircle(dietKeywordInfo.name, dietKeywordInfo.isPrefer, ApplicationClass.applicationResources.getDimension(R.dimen.diet_keyword_dialog_size5),
                ApplicationClass.applicationResources.getDimension(R.dimen.diet_keyword_dialog_text_size5), keywordX, keywordY)
        }
    }

    @SuppressLint("InflateParams")
    private fun showKeywordCircle(name: String, isPrefer: String, size: Float, sizeText : Float, keywordX : Float, keywordY : Float ) {
        val mInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val dynamicCircle: View = mInflater.inflate(R.layout.view_diet_keyword, null)
        (dynamicCircle as TextView).apply {
            layoutParams = LinearLayout.LayoutParams(size.toInt(), size.toInt())
            background = if(isPrefer == "Y") ContextCompat.getDrawable(context, R.drawable.background_keyword_pos)
            else ContextCompat.getDrawable(context, R.drawable.background_keyword_neg)

            x = keywordX
            y = keywordY
            text = name
            setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeText)
        }
        binding.clDialogContainer.addView(dynamicCircle, binding.clDialogContainer.childCount)
    }
}