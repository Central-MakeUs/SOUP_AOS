package com.makeus.eatoo.src.suggestion.adpater


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.src.suggestion.model.SuggestionMateResultResponse

class MateSuggestionRecyclerviewAdapter (val MateList : ArrayList<SuggestionMateResultResponse>) : RecyclerView.Adapter<MateSuggestionRecyclerviewAdapter.Viewholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val inflaterview = LayoutInflater.from(parent.context).inflate(R.layout.item_my_suggestion,parent,false)

        return Viewholder(inflaterview)
    }

    override fun getItemCount() = MateList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Viewholder, position: Int) {



        holder.GroupName.text = MateList[position].groupName
        holder.MateName.text = MateList[position].mateName
        holder.RestaurantName.text = MateList[position].storeName
        holder.StartTime.text = "| " +  MateList[position].startTime
        holder.EndTime.text =  "-" + MateList[position].endTime
        holder.Date.text = MateList[position].createdAt
        holder.People.text = MateList[position].membersNumber.toString()

        }

    class Viewholder(val view : View) : RecyclerView.ViewHolder(view){

        val GroupName : AppCompatTextView = view.findViewById(R.id.group_name_tv)
        val MateName : AppCompatTextView = view.findViewById(R.id.title_txt)
        val RestaurantName : AppCompatTextView = view.findViewById(R.id.restaurant_txt)
        val StartTime : AppCompatTextView = view.findViewById((R.id.start_time_txt))
        val EndTime : AppCompatTextView = view.findViewById((R.id.end_time_txt))
        val Date : AppCompatTextView = view.findViewById(R.id.date_txt)
        val People : AppCompatTextView = view.findViewById((R.id.limit_people_txt))
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

}