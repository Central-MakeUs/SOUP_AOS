package com.example.eatoo.src.review.store_map.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eatoo.databinding.ItemExistingStoreBinding
import com.example.eatoo.src.review.store_map.domain.Store
import com.example.eatoo.util.glideUtil

class ExistingStoreRVAdapter(val context : Context, val storeList : List<Store>) : RecyclerView.Adapter<ExistingStoreRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : ItemExistingStoreBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item : Store, position: Int) {
            binding.tvStoreName.text = item.name
            binding.tvStoreLocation.text = item.address
            binding.tvRating.text = item.rating.toString()
            glideUtil(context, item.imgUrl, binding.ivStore)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemExistingStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(storeList[position], position)
    }

    override fun getItemCount(): Int = storeList.size
}