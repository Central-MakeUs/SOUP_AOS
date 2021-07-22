package com.makeus.eatoo.src.explanation

import android.os.Bundle
import android.view.View
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseFragment

import com.makeus.eatoo.databinding.FragmentGuide2Binding

class GuideFragment2 : BaseFragment<FragmentGuide2Binding>(FragmentGuide2Binding::bind, R.layout.fragment_guide2){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.skipBtn.setOnClickListener {
        }
    }

}