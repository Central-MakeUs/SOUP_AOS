package com.makeus.eatoo.src.home.group.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.src.home.group.main.model.GroupMateResponse
import com.makeus.eatoo.util.glideUtil
import com.makeus.eatoo.util.roundAll

class Group_Home_Main_Mate_Kind_RecyclerviewAdapter(val MateList : ArrayList<GroupMateResponse>) : RecyclerView.Adapter<Group_Home_Main_Mate_Kind_RecyclerviewAdapter.CustomViewholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        var inflaterview = LayoutInflater.from(parent.context).inflate(R.layout.finding_mate_recyclerview_item,parent,false)

        return CustomViewholder(inflaterview)
    }


    override fun getItemCount() = MateList.size

    override fun onBindViewHolder(holder: CustomViewholder, position : Int) {



        val imageUrl: String = MateList[position].imgUrl
        if(imageUrl != "") {
            glideUtil(holder.view.context, imageUrl, roundAll(holder.Mateimg, 100f ))
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

        if(MateList[position].isAttended == "Y"){
            holder.MateAttend.visibility = VISIBLE
            holder.NoMateAttend.visibility = GONE
        }
        else if (MateList[position].isAttended == "N"){
            holder.MateAttend.visibility = GONE
            holder.NoMateAttend.visibility = VISIBLE
        }


        holder.MateLayout.setOnClickListener {
            itemClickListener.onClick(it,position,MateList[position].mateIdx)
        }

    }


    class CustomViewholder(val view : View) : RecyclerView.ViewHolder(view){
        val MateLayout : ConstraintLayout = view.findViewById(R.id.mate_layout)
        val Mateimg : AppCompatImageView = view.findViewById(R.id.mate_suggestion_img)
        val MateStatus : AppCompatTextView = view.findViewById(R.id.mate_title)
        val MateName : AppCompatTextView = view.findViewById(R.id.mate_title)
        val ResraurantName : AppCompatTextView = view.findViewById(R.id.mate_resraurant_txt)
        val Matetime : AppCompatTextView = view.findViewById(R.id.mate_time_txt)
        val MateLimitPeople : AppCompatTextView = view.findViewById(R.id.mate_limit_people_txt)
        val MateAttend : AppCompatImageView = view.findViewById(R.id.attend_img)
        val NoMateAttend : AppCompatImageView = view.findViewById(R.id.no_attend_img)


    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int, mateIdx : Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }



}