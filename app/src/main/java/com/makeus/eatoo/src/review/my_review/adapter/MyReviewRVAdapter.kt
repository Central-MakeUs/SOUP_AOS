package com.makeus.eatoo.src.review.my_review.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.databinding.ItemMyReviewBinding
import com.makeus.eatoo.src.review.my_review.model.MyReviewResult
import com.makeus.eatoo.util.glideUtil

class MyReviewRVAdapter(
    val context : Context,
    private val myReviewList : List<MyReviewResult>,
    val listener: OnMyReviewClickListener
    )
    : RecyclerView.Adapter<MyReviewRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : ItemMyReviewBinding )
        : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item : MyReviewResult) {
            binding.tvMyreviewStoreName.text = item.storeName
            binding.tvMyreviewStoreLocation.text = item.address
            setRatingStarIv(item)
            binding.tvMyreviewMenuName.text = item.menuName
            binding.tvMyreviewCreatedAt.text = item.createdAt
            glideUtil(context, item.imgUrl, binding.ivMyreview)
            binding.cardviewMyreview.setOnClickListener{
                listener.onMyReviewClicked(item)
            }
        }

        private fun setRatingStarIv(item: MyReviewResult) {
            when(item.rating.toInt()){
                1 -> binding.ivMyreviewRating.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.group_369))
                2-> binding.ivMyreviewRating.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.group_368))
                3 -> binding.ivMyreviewRating.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.group_367))
                4 -> binding.ivMyreviewRating.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.group_366))
                5 -> binding.ivMyreviewRating.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.group_365))
            }
        }

    }

    interface OnMyReviewClickListener {
        fun onMyReviewClicked(item : MyReviewResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMyReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(myReviewList[position])
    }

    override fun getItemCount(): Int = myReviewList.size
}