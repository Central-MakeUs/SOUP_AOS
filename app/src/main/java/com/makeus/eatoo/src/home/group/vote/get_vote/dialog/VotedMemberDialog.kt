package com.makeus.eatoo.src.home.group.vote.get_vote.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import com.makeus.eatoo.R
import com.makeus.eatoo.databinding.DialogVotedMemberBinding
import com.makeus.eatoo.src.home.group.vote.get_vote.model.VotedMemberResult
import com.makeus.eatoo.src.home.notification.model.GetMemberRe
import com.makeus.eatoo.util.EatooCharList

class VotedMemberDialog(
    context : Context,
    val votedMemberList : List<VotedMemberResult>
) : Dialog(context) {
    private lateinit var binding: DialogVotedMemberBinding
    private val mInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogVotedMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setMember()

    }

    private fun setMember() {
        val insertPoint: FlexboxLayout = binding.flexboxVoteMember

        votedMemberList.forEach {
            val memberView: View = mInflater.inflate(R.layout.view_notification_member, null)
            val memberContainer = memberView.findViewById<ImageView>(R.id.iv_member_container)
            val memberChar = memberView.findViewById<ImageView>(R.id.iv_member)
            val memberNickname = memberView.findViewById<TextView>(R.id.tv_member_nickname)
            setMemberChar(memberContainer, memberChar, it)
            memberNickname.text = it.nickName

            insertPoint.addView(memberView, insertPoint.childCount)
        }
    }

    private fun setMemberChar(
        memberContainer: ImageView?,
        member: ImageView?,
        it: VotedMemberResult
    ) {
        val memberColor = if(it.color != 0) it.color -1 else 0
        val memberChar = if(it.characters != 0) it.characters -1 else 0

        member?.setImageResource(EatooCharList[(memberColor*5) + memberChar])

        if(it.singleStatus == "ON"){
            memberContainer?.setImageResource(R.drawable.background_member_gray)
            val grayScale = floatArrayOf(0.2989f, 0.5870f, 0.1140f,
                0F, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.0000F, 0.0000F, 0.0000F, 1f, 0f)
            val matrix = ColorMatrixColorFilter(grayScale)
            member?.colorFilter= matrix
        }else {
            memberContainer?.setImageResource(R.drawable.background_member_orange)
            member?.colorFilter = null
        }
    }
}