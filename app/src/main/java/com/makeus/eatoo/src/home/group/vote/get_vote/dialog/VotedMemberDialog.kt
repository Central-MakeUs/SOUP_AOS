package com.makeus.eatoo.src.home.group.vote.get_vote.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.flexbox.FlexboxLayout
import com.makeus.eatoo.R
import com.makeus.eatoo.databinding.DialogVotedMemberBinding
import com.makeus.eatoo.src.home.group.vote.get_vote.adapter.VotedMemberRVAdapter
import com.makeus.eatoo.src.home.group.vote.get_vote.model.VotedMemberResult
import com.makeus.eatoo.src.home.notification.model.GetMemberRe
import com.makeus.eatoo.util.EatooCharList

class VotedMemberDialog(
    context : Context,
    val voteItemDetail : VotedMemberResult
) : Dialog(context) {
    private lateinit var binding: DialogVotedMemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogVotedMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
        setCancelable(false)

        binding.tvVoteItemName.text = voteItemDetail.menuName

        binding.rvVoteMember.apply {
            adapter = VotedMemberRVAdapter(context, voteItemDetail.getMembersRes)
            layoutManager = GridLayoutManager(context, 3)
        }

        binding.btnVoteDialogDone.setOnClickListener {
            dismiss()
        }
    }
}