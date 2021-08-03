package com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate.adpater

import android.annotation.SuppressLint
import android.content.Context
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
import com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate.model.GroupMateResult
import com.makeus.eatoo.util.glideUtil
import com.makeus.eatoo.util.roundAll


class Find_Mate_Recyclerview_Adapter(val context : Context,val MateList : ArrayList<GroupMateResult>) : RecyclerView.Adapter<Find_Mate_Recyclerview_Adapter.CustomViewholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val inflaterview = LayoutInflater.from(parent.context).inflate(R.layout.item_group_suggstion,parent,false)

        return CustomViewholder(inflaterview)
    }

    override fun getItemCount() = MateList.size

    @SuppressLint("SetTextI18n", "ResourceAsColor", "UseCompatLoadingForDrawables", "NewApi")
    override fun onBindViewHolder(holder: CustomViewholder, position : Int) {


        if(MateList[position].status == 1){
            holder.Mathing_status.setText(R.string.group_main_mate_matching)
            holder.LimitPeople_img.visibility = VISIBLE
            holder.LimitPeople_end_img.visibility = GONE
            holder.MateLayout.setOnClickListener {
                itemClickListener.onClick(it,position,MateList[position].mateIdx, MateList[position].isAttended)
            }

        }
        else if(MateList[position].status == 2){
            holder.Mathing_status.setText(R.string.group_main_mate_matching_end)
            holder.Mathing_status.background = holder.view.context.resources.getDrawable(R.drawable.group_name_background_gray,null)
            holder.MealTime.background = holder.view.context.resources.getDrawable(R.drawable.background_keyword_gray_color,null)
            holder.LimitTime.background = holder.view.context.resources.getDrawable(R.drawable.background_keyword_gray_color,null)
            holder.LimitPeople.background = holder.view.context.resources.getDrawable(R.drawable.background_keyword_gray_color,null)
            holder.RestaurantName.background = holder.view.context.resources.getDrawable(R.drawable.background_keyword_gray_color,null)
            holder.LimitPeople_img.visibility = GONE
            holder.LimitPeople_end_img.visibility = VISIBLE
            holder.LimitPeople_Num.setTextColor(holder.LimitPeople_Num.context.getColor(R.color.gray_100))
            holder.MateName.setTextColor(holder.MateName.context.getColor(R.color.gray_100))
            holder.MealTime.setTextColor(holder.MealTime.context.getColor(R.color.gray_100))
            holder.LimitTime.setTextColor(holder.LimitTime.context.getColor(R.color.gray_100))
            holder.LimitPeople.setTextColor(holder.LimitPeople.context.getColor(R.color.gray_100))
            holder.RestaurantName.setTextColor(holder.RestaurantName.context.getColor(R.color.gray_100))

        }

        if(MateList[position].imgUrl != "") {
            glideUtil(context, MateList[position].imgUrl, roundAll(holder.Mateimg, 100))
            holder.Mateimg.setAlpha(200)
        }
        else holder.Mateimg.setImageResource(R.drawable.group_385)

        holder.MateName.text = MateList[position].mateName
        holder.RestaurantName.text = MateList[position].storeName
        holder.MealTime.text = MateList[position].time
        holder.LimitTime.text = MateList[position].timeLimit
        holder.LimitPeople.text = MateList[position].headCount
        holder.LimitPeople_Num.text = MateList[position].membersNumber



    }


    class CustomViewholder(val view : View) : RecyclerView.ViewHolder(view){
        val MateLayout : ConstraintLayout = view.findViewById(R.id.mate_layout)
        val Mathing_status : AppCompatTextView = view.findViewById(R.id.mate_status_tv)
        val MateName : AppCompatTextView = view.findViewById(R.id.title_txt)
        val Mateimg : AppCompatImageView = view.findViewById(R.id.food_img)
        val RestaurantName : AppCompatTextView = view.findViewById(R.id.mate_store)
        val MealTime : AppCompatTextView = view.findViewById((R.id.meal_time_tv))
        val LimitTime : AppCompatTextView = view.findViewById((R.id.limit_time_tv))
        val LimitPeople : AppCompatTextView = view.findViewById((R.id.limit_people_tv))
        val LimitPeople_Num : AppCompatTextView = view.findViewById((R.id.limit_people_number_tv))
        val LimitPeople_img : AppCompatImageView = view.findViewById((R.id.group_status_img))
        val LimitPeople_end_img : AppCompatImageView = view.findViewById((R.id.group_status_end_img))

    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int, mateIdx : Int, isAttended : String)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }



}