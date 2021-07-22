package com.makeus.eatoo.src.explanation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.makeus.eatoo.src.explanation.ExplanationActivity

class GuideViewpageradapter(fragment: ExplanationActivity) : FragmentStateAdapter(fragment){

    var fragmentList : ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
        notifyItemInserted(fragmentList.size - 1)
    }


}