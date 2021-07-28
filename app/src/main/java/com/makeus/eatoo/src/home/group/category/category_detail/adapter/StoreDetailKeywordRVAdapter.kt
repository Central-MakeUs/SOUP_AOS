package com.makeus.eatoo.src.home.group.category.category_detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.databinding.ItemStoreDetailKeywordBinding
import com.makeus.eatoo.src.home.group.category.category_detail.model.GetStoreKeywordRe

class StoreDetailKeywordRVAdapter (
    val context : Context,
    storeKeywordList : List<GetStoreKeywordRe>
)
    : RecyclerView.Adapter<StoreDetailKeywordRVAdapter.ViewHolder>() {

    var notEmptyKeywordList = storeKeywordList.filter { it.name != "" }

    inner class ViewHolder(val binding : ItemStoreDetailKeywordBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item : GetStoreKeywordRe) {
            binding.chipKeyword.text = item.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemStoreDetailKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(notEmptyKeywordList[position])
    }

    override fun getItemCount(): Int = notEmptyKeywordList.size
}