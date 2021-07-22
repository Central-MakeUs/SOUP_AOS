package com.example.eatoo.src.home.group.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatoo.R
import com.example.eatoo.src.home.group.main.model.GroupMateResponse
import com.example.eatoo.util.glideUtil
import com.example.eatoo.util.roundAll

class Group_Home_Main_Mate_Kind_RecyclerviewAdapter(val MateList : ArrayList<GroupMateResponse>) : RecyclerView.Adapter<Group_Home_Main_Mate_Kind_RecyclerviewAdapter.CustomViewholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        var inflaterview = LayoutInflater.from(parent.context).inflate(R.layout.finding_mate_recyclerview_item,parent,false)

        return CustomViewholder(inflaterview)
    }


    override fun getItemCount() = MateList.size

    override fun onBindViewHolder(holder: CustomViewholder, position : Int) {



        val imageUrl: String = MateList[position].imgUrl
        if(imageUrl != null) {
            glideUtil(holder.view.context, imageUrl, roundAll(holder.Mateimg, 100f ))
//            Glide.with(holder.view.context).load(imageUrl).into(holder.Mateimg)
//            holder.Mateimg.clipToOutline = true
        }
        if(MateList[position].status == 0 ){
            holder.MateStatus.setText(R.string.group_main_mate_matching)
        }
        else{
            holder.MateStatus.setText(R.string.group_main_mate_matching_limit)
        }
        holder.MateName.text = MateList[position].mateName
        holder.ResraurantName.text = MateList[position].storeName
        holder.Matetime.text = MateList[position].time
        holder.MateLimitPeople.text = MateList[position].membersNumber.toString()

    }


    class CustomViewholder(val view : View) : RecyclerView.ViewHolder(view){
        val Mateimg : AppCompatImageView = view.findViewById(R.id.mate_suggestion_img)
        val MateStatus : AppCompatTextView = view.findViewById(R.id.mate_title)
        val MateName : AppCompatTextView = view.findViewById(R.id.mate_title)
        val ResraurantName : AppCompatTextView = view.findViewById(R.id.mate_resraurant_txt)
        val Matetime : AppCompatTextView = view.findViewById(R.id.mate_time_txt)
        val MateLimitPeople : AppCompatTextView = view.findViewById(R.id.mate_limit_people_txt)
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }



}