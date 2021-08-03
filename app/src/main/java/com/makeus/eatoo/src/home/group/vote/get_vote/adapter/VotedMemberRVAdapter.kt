package com.makeus.eatoo.src.home.group.vote.get_vote.adapter

import android.content.Context
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.databinding.ItemVotedMemberBinding
import com.makeus.eatoo.src.home.group.vote.get_vote.model.VotedMember
import com.makeus.eatoo.src.home.group.vote.get_vote.model.VotedMemberResult
import com.makeus.eatoo.util.EatooCharList

class VotedMemberRVAdapter(
    val context : Context,
    val memberList : List<VotedMember>
) : RecyclerView.Adapter<VotedMemberRVAdapter.ViewHolder>() {


    inner class ViewHolder(val binding : ItemVotedMemberBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item : VotedMember) {

            binding.tvMemberNickname.text = item.nickName
            val memberColor = if (item.color != 0) item.color - 1 else 0
            val memberChar = if (item.characters != 0) item.characters - 1 else 0
            binding.ivMember.setImageResource(EatooCharList[(memberColor * 5) + memberChar])

            if (item.singleStatus == "ON") {
                binding.ivMemberContainer.setImageResource(R.drawable.background_member_gray)
                val grayScale = floatArrayOf(
                    0.2989f,
                    0.5870f,
                    0.1140f,
                    0F,
                    0f,
                    0.2989f,
                    0.5870f,
                    0.1140f,
                    0f,
                    0f,
                    0.2989f,
                    0.5870f,
                    0.1140f,
                    0f,
                    0f,
                    0.0000F,
                    0.0000F,
                    0.0000F,
                    1f,
                    0f
                )
                val matrix = ColorMatrixColorFilter(grayScale)
                binding.ivMember.colorFilter = matrix
            } else {
                binding.ivMemberContainer.setImageResource(R.drawable.background_member_orange)
                binding.ivMember.colorFilter = null
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemVotedMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(memberList[position])
    }

    override fun getItemCount(): Int = memberList.size
}