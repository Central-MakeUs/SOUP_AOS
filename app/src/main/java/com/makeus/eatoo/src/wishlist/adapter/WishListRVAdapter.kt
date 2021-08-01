package com.makeus.eatoo.src.wishlist.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.databinding.ItemCategoryListStoreBinding
import com.makeus.eatoo.src.home.group.category.category_list.model.GetStoresRe
import com.makeus.eatoo.src.review.store_location.model.KakaoSearchDoc
import com.makeus.eatoo.src.wishlist.model.WishListResult
import com.makeus.eatoo.util.glideUtil

class WishListRVAdapter(
    val context: Context,
    val listener: OnStoreClickListener
) : RecyclerView.Adapter<WishListRVAdapter.ViewHolder>() {

    var storeList = arrayListOf<WishListResult>()

    interface OnStoreClickListener {
        fun onStoreClicked(storeIdx: Int)
        fun onStoreLongClicked(storeName: String, storeImg : String)
        fun onLikeClicked(storeIdx: Int, isLiked: Boolean)
    }

    inner class ViewHolder(val binding: ItemCategoryListStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindItem(item: WishListResult) {


            glideUtil(context, item.imgUrl, binding.ivStore)
            binding.tvStoreName.text = item.storeName
            binding.tvRating.text = item.rating.toInt().toString()
            binding.toggleStoreLike.isChecked = item.isLiked == "Y"

            setKeyword(item)

            binding.clCategoryStoreList.setOnClickListener {
                listener.onStoreClicked(item.storeIdx)
            }
            binding.clCategoryStoreList.setOnLongClickListener {
                listener.onStoreLongClicked(item.storeName, item.imgUrl)

                return@setOnLongClickListener true
            }
            binding.toggleStoreLike.setOnClickListener {
                if (binding.toggleStoreLike.isChecked) listener.onLikeClicked(item.storeIdx, true)
                else listener.onLikeClicked(item.storeIdx, false)
            }
        }

        private fun setKeyword(item: WishListResult) {
            var keyword1: String? = null
            var keyword2: String? = null

            item.getStoreKeywordRes.forEach {
                if (it.name != "" && it.name.length <= 5) {
                    if (keyword1 == null) {
                        Log.d("wishlist1", it.name)
                        keyword1 = it.name
                    } else if (keyword2 == null) {
                        Log.d("wishlist2", it.name)
                        keyword2 = it.name
                    }
                }
            }

            if (keyword1 != null) binding.tvStoreKeyword1.text = keyword1
            else binding.tvStoreKeyword1.text = ""
            if (keyword2 != null) {
                binding.tvStoreKeyword2.text = keyword2
                binding.llKeyword2.isVisible = true
            }else{
                binding.llKeyword2.isVisible = false
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WishListRVAdapter.ViewHolder {
        val view =
            ItemCategoryListStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishListRVAdapter.ViewHolder, position: Int) {
        holder.bindItem(storeList[position])
    }

    override fun getItemCount(): Int = storeList.size

    fun addAllData(item: ArrayList<WishListResult>) {
        storeList.addAll(item)
        notifyDataSetChanged()
    }

    fun removeAllData() {
        storeList.clear()
        notifyDataSetChanged()
    }
}