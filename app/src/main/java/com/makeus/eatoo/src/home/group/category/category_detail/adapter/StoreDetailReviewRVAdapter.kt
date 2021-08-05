package com.makeus.eatoo.src.home.group.category.category_detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.databinding.ItemStoreDetailReviewBinding
import com.makeus.eatoo.src.home.group.category.category_detail.model.GetReviewRe
import com.makeus.eatoo.util.glideUtil
import com.makeus.eatoo.util.showRatingOrangeStartUtil

class StoreDetailReviewRVAdapter (
    val context : Context,
    private val storeReviewList : List<GetReviewRe>
)
    : RecyclerView.Adapter<StoreDetailReviewRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemStoreDetailReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: GetReviewRe) {
            glideUtil(context, item.imgUrl, binding.ivMenuImg)
            binding.ivMenuImg.setAlpha(200)
            binding.chipKeyword.text = item.menuName
            binding.tvReviewDate.text = item.createdAt
            binding.tvReviewContent.text = item.contents
            showRatingOrangeStartUtil(context, item.rating.toInt(), binding.ivReviewRating)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemStoreDetailReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(storeReviewList[position])
    }

    override fun getItemCount(): Int = storeReviewList.size
}