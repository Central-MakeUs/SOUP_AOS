package com.example.eatoo.src.mypage.invite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.eatoo.R
import com.example.eatoo.src.mypage.invite.model.InviteGroupResult

class Invite_Group_Kind_RecyclerviewAdapter(val GroupList : ArrayList<InviteGroupResult>) : RecyclerView.Adapter<Invite_Group_Kind_RecyclerviewAdapter.CustomViewholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val inflaterview = LayoutInflater.from(parent.context).inflate(R.layout.item_invit_group_recyclerview,parent,false)

        return CustomViewholder(inflaterview)
    }


    override fun getItemCount() = GroupList.size

    override fun onBindViewHolder(holder: CustomViewholder, position : Int) {

        holder.GroupName.text = GroupList[position].name

        holder.GroupLayout.setOnClickListener {
            itemClickListener.onClick(it,position , GroupList[position].groupIdx,GroupList[position].name)
        }

    }


    class CustomViewholder(val view : View) : RecyclerView.ViewHolder(view){
        val GroupLayout : ConstraintLayout = view.findViewById(R.id.group_layout)
        val GroupName : AppCompatTextView = view.findViewById(R.id.group_name_tv)
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int , groupIdx : Int, groupname : String)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

}