package com.example.eatoo.src.suggestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatoo.R
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentMyPageBinding
import com.example.eatoo.databinding.FragmentSuggestionBinding
import com.example.eatoo.src.suggestion.adpater.MateSuggestionRecyclerviewAdapter


class SuggestionFragment : BaseFragment<FragmentSuggestionBinding>(FragmentSuggestionBinding::bind, R.layout.fragment_suggestion) {
    var Celebnames = arrayListOf<String>("sdasd","asdas","asdasd")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val SearchAdapter = MateSuggestionRecyclerviewAdapter(Celebnames)
        binding.mySuggestionRecyclerview.adapter = SearchAdapter
        binding.mySuggestionRecyclerview.layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.VERTICAL }
    }
}