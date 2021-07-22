package com.example.eatoo.src.home.group.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatoo.R
import com.example.eatoo.src.home.group.main.model.GroupStoreResponse
import com.example.eatoo.util.glideUtil
import com.example.eatoo.util.roundTop

class Group_Home_Main_Store_Kind_RecyclerviewAdapter(val StoreList : ArrayList<GroupStoreResponse>) : RecyclerView.Adapter<Group_Home_Main_Store_Kind_RecyclerviewAdapter.CustomViewholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val inflaterview = LayoutInflater.from(parent.context).inflate(R.layout.item_recommend_store,parent,false)

        return CustomViewholder(inflaterview)
    }


    override fun getItemCount() = 2

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: CustomViewholder, position : Int) {



        val imageUrl: String = StoreList[position].imgUrl
        if(imageUrl != null) {
            glideUtil(holder.view.context, imageUrl, roundTop(holder.Storeimg,20f ) )
//            Glide.with(holder.view.context).load(imageUrl).into(holder.Storeimg,)
//            holder.Storeimg.background = holder.view.context.resources.getDrawable(R.drawable.group_main_home_store_img_background, null)
//            holder.Storeimg.clipToOutline = true
        }

        if(StoreList[position].isLiked == "Y"){
            holder.Storeimg.background = holder.view.context.resources.getDrawable(R.drawable.eva_heart_outline, null)
        }
        else{
            holder.Storeimg.background = holder.view.context.resources.getDrawable(R.drawable.vector, null)
        }

        holder.StoreName.text = StoreList[position].storeName
        holder.ResraurantLocation.text = StoreList[position].address
        holder.StoreScore.text = StoreList[position].rating.toString()

    }


    class CustomViewholder(val view : View) : RecyclerView.ViewHolder(view){
        val Storeimg : AppCompatImageView = view.findViewById(R.id.store_img)
        val StoreName : AppCompatTextView = view.findViewById(R.id.store_name_tv)
        val ResraurantLocation : AppCompatTextView = view.findViewById(R.id.store_location_tv)
        val StoreScore : AppCompatTextView = view.findViewById(R.id.store_score_tv)
        val StoreWish : AppCompatImageView = view.findViewById(R.id.store_wish)
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }



}