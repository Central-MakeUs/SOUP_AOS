package com.makeus.eatoo.src.home.group.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.makeus.eatoo.src.home.group.category.GroupCategoryFragment
import com.makeus.eatoo.src.home.group.main.GroupMainFragment
import com.makeus.eatoo.src.home.group.member.GroupMemberFragment
import com.makeus.eatoo.src.home.group.vote.GroupVoteFragment

private const val GROUP_TAB_NUM = 4

class GroupViewPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

//    val fragmentList = arrayListOf<Fragment>(GroupMainFragment(), GroupCategoryFragment())

    override fun getItemCount(): Int = GROUP_TAB_NUM

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GroupMainFragment()
            1 -> GroupCategoryFragment()
            2 -> GroupVoteFragment()
            3 -> GroupMemberFragment()
            else -> GroupMainFragment()
        }
    }






}