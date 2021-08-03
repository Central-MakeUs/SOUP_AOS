package com.makeus.eatoo.src.review.store_map.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.databinding.ItemExistingStoreBinding
import com.makeus.eatoo.src.review.store_map.model.Store
import com.makeus.eatoo.util.glideUtil
import com.makeus.eatoo.util.roundLeft

class ExistingStoreRVAdapter(
    val context : Context,
    private val storeList : List<Store>,
    val listener: OnReviewClickListener
    )
    : RecyclerView.Adapter<ExistingStoreRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : ItemExistingStoreBinding )
        : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item : Store) {
            binding.tvStoreName.text = item.name
            binding.tvStoreLocation.text = item.address
            binding.tvRating.text = item.rating.toString()
            glideUtil(context, item.imgUrl, binding.ivStore)
            binding.ivStore.setAlpha(200)
            binding.clExistingStore.setOnClickListener{
                listener.onReviewClicked(item)
            }
        }

    }

    interface OnReviewClickListener {
        fun onReviewClicked(item : Store)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemExistingStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(storeList[position])
    }

    override fun getItemCount(): Int = storeList.size
}