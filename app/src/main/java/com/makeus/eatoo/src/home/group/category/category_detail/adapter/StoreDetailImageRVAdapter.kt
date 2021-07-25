package com.makeus.eatoo.src.home.group.category.category_detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.databinding.ItemStoreDetailImageBinding
import com.makeus.eatoo.src.home.group.category.category_detail.model.GetReviewImgRe
import com.makeus.eatoo.util.glideUtil
import com.makeus.eatoo.util.roundAll

class StoreDetailImageRVAdapter(
    val context : Context,
    private val storeImgList : List<GetReviewImgRe>
)
    : RecyclerView.Adapter<StoreDetailImageRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemStoreDetailImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: GetReviewImgRe) {
            glideUtil(context, item.imgUrl, roundAll(binding.ivStoreDetailImage, 10f))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemStoreDetailImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(storeImgList[position])
    }

    override fun getItemCount(): Int = storeImgList.size
}