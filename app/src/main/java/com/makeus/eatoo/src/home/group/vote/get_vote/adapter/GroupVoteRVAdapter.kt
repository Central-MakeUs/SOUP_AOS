package com.makeus.eatoo.src.home.group.vote.get_vote.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.databinding.ItemGroupVoteBinding
import com.makeus.eatoo.src.home.group.vote.get_vote.model.GroupVoteResult
import com.makeus.eatoo.src.home.group.vote.get_vote.model.VoteKeyword

class GroupVoteRVAdapter(
    val context : Context,
    val voteList : List<GroupVoteResult>,
    val listener : OnVoteClickListener
) : RecyclerView.Adapter<GroupVoteRVAdapter.ViewHolder>() {

    val colorArray = ApplicationClass.applicationResources.getIntArray(R.array.groupVoteRVColor)

    interface OnVoteClickListener {
        fun onVoteClicked(item : GroupVoteResult)
    }

    inner class ViewHolder(val binding : ItemGroupVoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item : GroupVoteResult, position: Int) {
            binding.tvGroupVoteName.text = item.name
            binding.tvGroupVoteNum.text = item.votedNumber.toString()
            binding.tvGroupVoteNum.setTextColor(colorArray[position%3])
            item.getVoteKeywordRes.forEach {
                addKeyword(it)
            }
            binding.clGroupVote.setBackgroundTintList(
                ColorStateList.valueOf(colorArray[position%3]))

            binding.clGroupVote.setOnClickListener {
                listener.onVoteClicked(item)
            }


        }

        private fun addKeyword(item: VoteKeyword) {
            val mInflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dynamicVoteKeyword :  View = mInflater.inflate(R.layout.view_vote_keyword, null)
            val keywordTextView = dynamicVoteKeyword.findViewById<TextView>(R.id.tv_group_vote_keyword)
            keywordTextView.text = item.name
            val insertPoint: LinearLayout = binding.llGroupVoteKeywordContainer
            insertPoint.addView(dynamicVoteKeyword, insertPoint.childCount)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemGroupVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(voteList[position], position)
    }

    override fun getItemCount(): Int = voteList.size
}