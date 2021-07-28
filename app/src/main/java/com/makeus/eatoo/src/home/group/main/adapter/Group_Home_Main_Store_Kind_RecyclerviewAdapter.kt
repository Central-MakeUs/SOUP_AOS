package com.makeus.eatoo.src.home.group.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.databinding.ItemRecommendStoreBinding
import com.makeus.eatoo.databinding.ItemRecommendStoreDetailBinding
import com.makeus.eatoo.src.home.group.main.model.GroupMateResponse
import com.makeus.eatoo.src.home.group.main.model.GroupStoreResponse
import com.makeus.eatoo.src.home.group.main.store_rec.adapter.StoreRecRVAdapter
import com.makeus.eatoo.src.home.group.main.store_rec.model.StoreRecResult
import com.makeus.eatoo.util.glideUtil
import com.makeus.eatoo.util.roundTop

class Group_Home_Main_Store_Kind_RecyclerviewAdapter(
    val context: Context,
    val listener: OnStoreClickListener,
    val storeList : ArrayList<GroupStoreResponse>
) : RecyclerView.Adapter<Group_Home_Main_Store_Kind_RecyclerviewAdapter.ViewHolder>() {

    interface OnStoreClickListener {
        fun onStoreClicked(storeIdx: Int)
        fun onStoreLongClicked(storeName: String)
        fun onLikeClicked(storeIdx: Int, isLiked: Boolean)
    }

    inner class ViewHolder(val binding: ItemRecommendStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: GroupStoreResponse) {
            glideUtil(context, item.imgUrl, binding.ivStore)
            binding.tvStoreName.text = item.storeName
            binding.tvStoreLocation.text = item.address
            binding.tvStoreRating.text = item.rating.toInt().toString()
            binding.toggleStoreLike.isChecked = item.isLiked == "Y"

            binding.clMainStoreRec.setOnClickListener {
                listener.onStoreClicked(item.storeIdx)
            }
            binding.clMainStoreRec.setOnLongClickListener {
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
    ): Group_Home_Main_Store_Kind_RecyclerviewAdapter.ViewHolder {
        val view =
            ItemRecommendStoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Group_Home_Main_Store_Kind_RecyclerviewAdapter.ViewHolder, position: Int) {
        holder.bindItem(storeList[position])
    }

    override fun getItemCount(): Int = storeList.size


}