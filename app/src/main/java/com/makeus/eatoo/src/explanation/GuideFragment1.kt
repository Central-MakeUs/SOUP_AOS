package com.makeus.eatoo.src.explanation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.databinding.FragmentGuide1Binding
import com.makeus.eatoo.src.main.MainActivity
import com.makeus.eatoo.src.suggestion.SuggestionFragment


class GuideFragment1 : BaseFragment<FragmentGuide1Binding>(FragmentGuide1Binding::bind, R.layout.fragment_guide1){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.skipBtn.setOnClickListener {
            val fragment : Fragment = GuideFragment3()
//            (context as ExplanationActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host, GuideFragment3())
//                .commitAllowingStateLoss()
        }
    }

}