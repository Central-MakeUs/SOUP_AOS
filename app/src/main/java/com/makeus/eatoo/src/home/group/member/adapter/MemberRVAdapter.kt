package com.makeus.eatoo.src.home.group.member.adapter

import android.content.Context
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.databinding.ItemMemberBinding
import com.makeus.eatoo.src.home.group.member.model.GroupMember
import com.makeus.eatoo.src.home.group.member.model.MemberDietKeyword
import com.makeus.eatoo.util.EatooCharList

class MemberRVAdapter(
    val context : Context,
    private val memberList : List<GroupMember>,
    val listener : OnAddMemberClickListener
    ): RecyclerView.Adapter<MemberRVAdapter.ViewHolder>() {

    interface OnAddMemberClickListener {
        fun onAddMemberClicked()
        fun onMemberClicked(memberInfo : GroupMember)
    }

    inner class ViewHolder(val binding : ItemMemberBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item : GroupMember, position : Int) {
            if(item.userIdx != -10){
                binding.tvMemberNickname.text = item.nickName
                val memberColor = if(item.color != 0) item.color -1 else 0
                val memberChar = if(item.characters != 0) item.characters -1 else 0
                binding.ivMember.setImageResource(EatooCharList[(memberColor*5) + memberChar])

                if(item.singleStatus == "ON"){
                    binding.ivMemberContainer.setImageResource(R.drawable.background_member_gray)
                    val grayScale = floatArrayOf(0.2989f, 0.5870f, 0.1140f,
                        0F, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.0000F, 0.0000F, 0.0000F, 1f, 0f)
                    val matrix = ColorMatrixColorFilter(grayScale)
                    binding.ivMember.colorFilter= matrix
                }else {
                    binding.ivMemberContainer.setImageResource(R.drawable.background_member_orange)
                }
                binding.clCharContainer.setOnClickListener {
                    listener.onMemberClicked(item)
                }

            }else {
                binding.ivMemberContainer.setImageResource(R.drawable.background_member_gray)
                binding.ivMember.isVisible = false
                binding.ivAddMember.isVisible = true
                binding.ivMemberContainer.setOnClickListener {
                    listener.onAddMemberClicked()
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(memberList[position], position)
    }

    override fun getItemCount(): Int = memberList.size
}