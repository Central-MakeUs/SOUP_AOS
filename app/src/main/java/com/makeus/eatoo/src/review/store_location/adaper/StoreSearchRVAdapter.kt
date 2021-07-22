package com.makeus.eatoo.src.review.store_location.adaper

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.databinding.ItemReviewStoreSearchBinding
import com.makeus.eatoo.src.review.store_location.model.KakaoSearchDoc

class StoreSearchRVAdapter(
    private val context : Context,
    val listener : OnSearchResultClickListener
) : RecyclerView.Adapter<StoreSearchRVAdapter.ViewHolder>(){

    var storeList = ArrayList<KakaoSearchDoc>()

    interface OnSearchResultClickListener {
        fun onSearchResultClick(item : KakaoSearchDoc)
    }

    inner class ViewHolder(val binding : ItemReviewStoreSearchBinding) : RecyclerView.ViewHolder(binding.root)  {
        fun bindItem(item : KakaoSearchDoc) {
            binding.tvReviewSearchStoreName.text = item.place_name
            binding.tvReviewSearchStoreLocation.text = item.address_name
            binding.tvMyreviewStoreRoadAddress.text = item.road_address_name
            binding.clReviewSearchStoreResult.setOnClickListener {
                listener.onSearchResultClick(item)
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = ItemReviewStoreSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(storeList[position])
    }

    override fun getItemCount(): Int = storeList.size

    fun addAllData(item: ArrayList<KakaoSearchDoc>) {
        storeList.addAll(item)
        notifyItemRangeChanged(storeList.size, item.size)
    }

    fun removeAllData() {
        storeList.clear()
        notifyDataSetChanged()
    }
}