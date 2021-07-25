package com.makeus.eatoo.src.home.group.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.makeus.eatoo.src.home.group.GroupActivity
import com.makeus.eatoo.src.home.group.category.GroupCategoryFragment
import com.makeus.eatoo.src.home.group.main.GroupMainFragment
import com.makeus.eatoo.src.home.group.member.GroupMemberFragment
import com.makeus.eatoo.src.home.group.vote.GroupVoteFragment

private const val GROUP_TAB_NUM = 4

class GroupViewPagerAdapter(
    fm: FragmentActivity,
    private val groupActivity: GroupActivity
    ) : FragmentStateAdapter(fm) {

    var fragmentList = arrayListOf<Fragment>(GroupMainFragment(), GroupCategoryFragment(groupActivity), GroupVoteFragment(), GroupMemberFragment())

    override fun getItemCount(): Int = GROUP_TAB_NUM

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)

        val fragment: GroupMemberFragment? = groupActivity.supportFragmentManager.findFragmentByTag("f$position") as? GroupMemberFragment?
        fragment?.reload()
    }








}