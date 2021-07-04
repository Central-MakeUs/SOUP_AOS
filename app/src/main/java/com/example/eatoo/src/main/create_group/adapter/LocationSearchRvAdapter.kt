package com.example.eatoo.src.main.create_group.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eatoo.databinding.ItemLocationSearchResultBinding
import com.example.googlemapsapiprac.model.SearchResultEntity

class LocationSearchRvAdapter : RecyclerView.Adapter<LocationSearchRvAdapter.MainViewHolder>() {

    private var searchResultList: List<SearchResultEntity> = listOf()
    private lateinit var searchResultClickListener: (SearchResultEntity) -> Unit

    class MainViewHolder(
        val binding : ItemLocationSearchResultBinding,
        val searchResultClickListener: (SearchResultEntity) -> Unit)
        : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: SearchResultEntity) = with(binding) {
            tvTitle.text = data.buildingName
            tvSubtitle.text = data.fullAddress
        }

        fun bindView(data: SearchResultEntity) {
            binding.root.setOnClickListener {
                searchResultClickListener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view =
            ItemLocationSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(view, searchResultClickListener)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindData(searchResultList[position])
        holder.bindView(searchResultList[position])
    }

    override fun getItemCount(): Int= searchResultList.size

    fun setSearchResultList(
        searchResultList: List<SearchResultEntity>,
        searchResultClickListener: (SearchResultEntity) -> Unit
    ) {
        this.searchResultList = searchResultList
        this.searchResultClickListener = searchResultClickListener
        notifyDataSetChanged()
    }
}