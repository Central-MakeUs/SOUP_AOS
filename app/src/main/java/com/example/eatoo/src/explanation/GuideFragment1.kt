package com.example.eatoo.src.explanation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.example.eatoo.R
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentGuide1Binding
import com.example.eatoo.src.explanation.adapter.GuideViewpageradapter


class GuideFragment1 : BaseFragment<FragmentGuide1Binding>(FragmentGuide1Binding::bind, R.layout.fragment_guide1){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.skipBtn.setOnClickListener {
            val fragment : Fragment = GuideFragment3()
        }
    }

}