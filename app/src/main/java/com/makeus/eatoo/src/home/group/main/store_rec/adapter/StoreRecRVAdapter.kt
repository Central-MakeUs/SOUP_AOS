package com.makeus.eatoo.src.home.group.main.store_rec.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.databinding.ItemRecommendStoreDetailBinding
import com.makeus.eatoo.src.home.group.main.store_rec.model.StoreRecResult
import com.makeus.eatoo.util.glideUtil

class StoreRecRVAdapter(
    val context: Context,
    val listener: OnStoreClickListener
) : RecyclerView.Adapter<StoreRecRVAdapter.ViewHolder>() {

    var storeList = arrayListOf<StoreRecResult>()

    interface OnStoreClickListener {
        fun onStoreClicked(storeIdx: Int)
        fun onStoreLongClicked(storeName: String)
        fun onLikeClicked(storeIdx: Int, isLiked: Boolean)
    }

    inner class ViewHolder(val binding: ItemRecommendStoreDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindItem(item: StoreRecResult) {
            glideUtil(context, item.imgUrl, binding.ivStore)
            binding.tvStoreName.text = item.storeName
            binding.tvStoreLocation.text = item.address
            binding.tvStoreRating.text = item.rating.toInt().toString()
            binding.toggleStoreLike.isChecked = item.isLiked == "Y"

            binding.clStoreRec.setOnClickListener {
                listener.onStoreClicked(item.storeIdx)
            }
            binding.clStoreRec.setOnLongClickListener {
                listener.onStoreLongClicked(item.storeName)

                return@setOnLongClickListener true
            }
            binding.toggleStoreLike.setOnCheckedChangeListener { compoundButton, isChecked ->
                if(isChecked) listener.onLikeClicked(item.storeIdx, true)
                else listener.onLikeClicked(item.storeIdx, false)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoreRecRVAdapter.ViewHolder {
        val view =
            ItemRecommendStoreDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreRecRVAdapter.ViewHolder, position: Int) {
        holder.bindItem(storeList[position])
    }

    override fun getItemCount(): Int = storeList.size

    fun addAllData(item: ArrayList<StoreRecResult>) {
        storeList.addAll(item)
        notifyDataSetChanged()
    }

    fun removeAllData() {
        storeList.clear()
        notifyDataSetChanged()
    }
}