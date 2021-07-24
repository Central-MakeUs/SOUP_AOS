package com.makeus.eatoo.src.home.group.category.category_map.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.databinding.ItemCategoryStoreBinding
import com.makeus.eatoo.src.home.group.category.category_map.model.CategoryMapStoreInfo
import com.makeus.eatoo.util.glideUtil
import com.makeus.eatoo.util.roundLeft
import com.makeus.eatoo.util.showRatingStartUtil

class CategoryStoreRVAdapter(
    val context : Context,
    private val storeList : List<CategoryMapStoreInfo>,
    val listener: OnStoreClickListener
)
    : RecyclerView.Adapter<CategoryStoreRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : ItemCategoryStoreBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item : CategoryMapStoreInfo) {
            binding.tvStoreName.text = item.name
            binding.tvStoreLocation.text = item.address
            glideUtil(context, item.imgUrl, roundLeft(binding.ivStore, 20f))
            showRatingStartUtil(context, item.rating.toInt(), binding.ivReviewStar)
            if(item.isLiked == "Y") binding.ivReviewLike.setImageResource(R.drawable.eva_heart_outline)
            binding.tvStoreReviewNum.text = item.reviewsNumber.toString()
            binding.clCategoryMapStore.setOnClickListener{
                listener.onStoreClicked(item)
            }
            binding.ivReviewLike.setOnClickListener {
                if(item.isLiked == "Y"){
                    listener.onLikeClicked(item.storeIdx)
                    binding.ivReviewLike.setImageResource(R.drawable.vector)
                }
            }
        }

    }

    interface OnStoreClickListener {
        fun onStoreClicked(item : CategoryMapStoreInfo)
        fun onLikeClicked(storeIdx : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCategoryStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(storeList[position])
    }

    override fun getItemCount(): Int = storeList.size
}
