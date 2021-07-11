package com.example.eatoo.src.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eatoo.R
import com.example.eatoo.src.home.model.GetGroupsRes

class Home_Group_Kind_RecyclerviewAdapter(val GroupList : ArrayList<GetGroupsRes> , var groupsize : Int , var group_status : String) : RecyclerView.Adapter<Home_Group_Kind_RecyclerviewAdapter.CustomViewholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        var inflaterview = LayoutInflater.from(parent.context).inflate(R.layout.item_home_has_group,parent,false)
        if(group_status == "BASIC")
        {
            inflaterview = LayoutInflater.from(parent.context).inflate(R.layout.item_home_has_group,parent,false)
            return CustomViewholder(inflaterview);
        }
        else if(group_status == "LAST")
        {
            inflaterview = LayoutInflater.from(parent.context).inflate(R.layout.item_main_group_plus,parent,false)
            return CustomViewholder(inflaterview);
        }
        return CustomViewholder(inflaterview)
    }


    override fun getItemCount() = GroupList.size + 1

    override fun onBindViewHolder(holder: CustomViewholder, position : Int) {
        if (position == groupsize - 1) {
            group_status = "LAST"
            holder.GroupName.text = GroupList[position].name
        }
        else if (position == groupsize){
            group_status = "LAST"
        }
        else{
            holder.GroupName.text = GroupList[position].name
        }
        Log.d("GroupList.size","" + GroupList.size)
        Log.d("groupsize","" +groupsize)

    }


    class CustomViewholder(val view : View) : RecyclerView.ViewHolder(view){
        val GroupName : AppCompatTextView = view.findViewById(R.id.group_name_tv)
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

}