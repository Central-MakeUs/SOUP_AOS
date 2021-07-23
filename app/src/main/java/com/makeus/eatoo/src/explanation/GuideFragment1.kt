package com.makeus.eatoo.src.explanation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.databinding.FragmentGuide1Binding


class GuideFragment1 : BaseFragment<FragmentGuide1Binding>(FragmentGuide1Binding::bind, R.layout.fragment_guide1){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.skipBtn.setOnClickListener {
            val fragment : Fragment = GuideFragment3()
        }
    }

}