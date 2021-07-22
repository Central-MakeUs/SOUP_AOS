package com.example.eatoo.src.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eatoo.R
import com.example.eatoo.src.home.model.GroupResponse
import com.example.eatoo.src.home.model.GroupResultResponse
import org.w3c.dom.Text

class HomeViewPagerAdapter(val groupList : List<GroupResultResponse>) : RecyclerView.Adapter<HomeViewPagerAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val groupName :TextView = itemView.findViewById(R.id.tv_group_name)
        val groupMemberNum : TextView = itemView.findViewById(R.id.tv_group_member_num)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_make_group, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = groupList.size
}