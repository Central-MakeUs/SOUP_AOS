package com.example.eatoo.src.explanation

import android.os.Bundle
import android.view.View
import com.example.eatoo.R
import com.example.eatoo.config.BaseFragment

import com.example.eatoo.databinding.FragmentGuide2Binding
import com.example.eatoo.src.explanation.adapter.GuideViewpageradapter

class GuideFragment2 : BaseFragment<FragmentGuide2Binding>(FragmentGuide2Binding::bind, R.layout.fragment_guide2){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.skipBtn.setOnClickListener {
        }
    }

}