package com.makeus.eatoo.src.mypage.invite.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.src.mypage.invite.model.InviteGroupResult

class IndicatorAdapter(val context: Context, var cardList: ArrayList<InviteGroupResult>)
    : RecyclerView.Adapter<IndicatorAdapter.IndexListViewHolder>() {
    val indexList = Array(cardList.size) { false } // 리스트를 모두 false로 초기화
    var index = 0 // 선택된 item의 index

    init {
        if (cardList.isNotEmpty())
            indexList[0] = true // 첫번째 요소를 default로 선택
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): IndexListViewHolder {
        val viewGroup = LayoutInflater.from(parent.context).inflate(R.layout.activity_invite, parent, false)


        return IndexListViewHolder(viewGroup)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    fun setItemIndex(position: Int) {
        // index를 새로운 item의 position으로 교체
        indexList[index] = false
        indexList[position] = true
        index = position

        notifyDataSetChanged()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: IndexListViewHolder, position: Int) {
        val asset = context.resources.getDrawable(
            if (indexList[position]) R.drawable.fill_circle
            else R.drawable.stoke_circle
        )

        //holder.bind(asset)
    }

    inner class IndexListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val dotsView: View = view.findViewById(R.id.indicator)
//
//        fun bind(asset: Drawable) {
//            dotsView.background = asset
//        }
    }
}