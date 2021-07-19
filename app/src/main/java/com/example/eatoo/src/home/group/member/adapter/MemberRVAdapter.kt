package com.example.eatoo.src.home.group.member.adapter

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.eatoo.R
import com.example.eatoo.databinding.ItemMemberBinding
import com.example.eatoo.src.home.group.member.model.GroupMember
import com.example.eatoo.util.EatooCharList

class MemberRVAdapter(
    val context : Context,
    private val memberList : List<GroupMember>
    ): RecyclerView.Adapter<MemberRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : ItemMemberBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item : GroupMember, position : Int) {
            binding.tvMemberNickname.text = item.nickName
            val memberColor = if(item.color != 0) item.color -1 else 0
            val memberChar = if(item.characters != 0) item.characters -1 else 0
            binding.ivMember.setImageResource(EatooCharList[(memberColor*5) + memberChar])
            binding.llMemberContainer.background = if(item.singleStatus == "OFF") ContextCompat.getDrawable(context, R.drawable.ic_ellipse_205)
            else ContextCompat.getDrawable(context, R.drawable.ic_ellipse_208)

            if(item.singleStatus == "ON"){
                val grayScale = floatArrayOf(0.2989f, 0.5870f, 0.1140f,
                    0F, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.0000F, 0.0000F, 0.0000F, 1f, 0f)
                val matrix = ColorMatrixColorFilter(grayScale)
                binding.ivMember.colorFilter= matrix

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