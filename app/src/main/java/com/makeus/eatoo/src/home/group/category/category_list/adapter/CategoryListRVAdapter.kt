package com.makeus.eatoo.src.home.group.category.category_list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.databinding.ItemCategoryListStoreBinding
import com.makeus.eatoo.src.home.group.category.category_list.model.GetStoresRe
import com.makeus.eatoo.src.review.store_location.model.KakaoSearchDoc
import com.makeus.eatoo.util.glideUtil

class CategoryListRVAdapter(
    val context: Context,
    val listener : OnStoreClickListener
) : RecyclerView.Adapter<CategoryListRVAdapter.ViewHolder>(){

    var storeList = arrayListOf<GetStoresRe>()

    interface OnStoreClickListener {
        fun onStoreClicked(storeIdx : Int)
        fun onStoreLongClicked(storeName : String)
        fun onLikeClicked(storeIdx : Int, isLiked: Boolean)
    }

    inner class ViewHolder(val binding : ItemCategoryListStoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item : GetStoresRe){
            glideUtil(context, item.imgUrl, binding.ivStore)
            binding.tvStoreName.text = item.storeName
            binding.tvRating.text = item.rating.toInt().toString()
            binding.toggleStoreLike.isChecked = item.isLiked == "Y"
            item.getStoreKeywordRes.forEach {
                if(it.name != "" && it.name.length <=5){
                    if(binding.tvStoreKeyword1.text.isEmpty()){
                        binding.tvStoreKeyword1.text = it.name
                    }else if(binding.tvStoreKeyword2.text.isEmpty()){
                            binding.tvStoreKeyword2.text = it.name
                            binding.llKeyword2.isVisible = true
                    }
                }
            }
            binding.clCategoryStoreList.setOnClickListener{
                listener.onStoreClicked(item.storeIdx)
            }
            binding.clCategoryStoreList.setOnLongClickListener{
                listener.onStoreLongClicked(item.storeName)

                return@setOnLongClickListener true
            }
            binding.toggleStoreLike.setOnClickListener {
                if(binding.toggleStoreLike.isChecked) listener.onLikeClicked(item.storeIdx, true)
                else listener.onLikeClicked(item.storeIdx, false)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryListRVAdapter.ViewHolder {
        val view = ItemCategoryListStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryListRVAdapter.ViewHolder, position: Int) {
        holder.bindItem(storeList[position])
    }

    override fun getItemCount(): Int = storeList.size

    fun addAllData(item: ArrayList<GetStoresRe>) {
        storeList.addAll(item)
        notifyDataSetChanged()
    }

    fun removeAllData() {
        storeList.clear()
        notifyDataSetChanged()
    }
}