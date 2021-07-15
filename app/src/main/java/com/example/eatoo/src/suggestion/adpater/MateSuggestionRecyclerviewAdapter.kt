package com.example.eatoo.src.suggestion.adpater

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eatoo.R

class MateSuggestionRecyclerviewAdapter (val NameList: ArrayList<String>) : RecyclerView.Adapter<MateSuggestionRecyclerviewAdapter.Viewholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val inflaterview = LayoutInflater.from(parent.context).inflate(R.layout.item_my_suggestion,parent,false)

        return Viewholder(inflaterview)
    }

    override fun getItemCount() = NameList.size

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        }

    class Viewholder(val view : View) : RecyclerView.ViewHolder(view){

    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

}