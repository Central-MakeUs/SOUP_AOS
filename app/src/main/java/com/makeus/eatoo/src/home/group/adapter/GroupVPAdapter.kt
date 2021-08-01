package com.makeus.eatoo.src.home.group.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.makeus.eatoo.R
import com.makeus.eatoo.src.home.group.GroupActivity
import com.makeus.eatoo.src.home.group.category.GroupCategoryFragment
import com.makeus.eatoo.src.home.group.category.category_list.GroupCategoryListFragment
import com.makeus.eatoo.src.home.group.main.GroupMainFragment
import com.makeus.eatoo.src.home.group.member.GroupMemberFragment
import com.makeus.eatoo.src.home.group.vote.GroupVoteFragment

class GroupVPAdapter(
    val fm : FragmentManager,
private val groupActivity: GroupActivity) : FragmentStatePagerAdapter(fm) {

    var fragmentList = arrayListOf<Fragment>(GroupMainFragment(), GroupCategoryFragment(groupActivity), GroupVoteFragment(), GroupMemberFragment())

    override fun getCount(): Int = 4

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    fun replaceFrag(oldFragment : GroupCategoryFragment){
        fm.beginTransaction().remove(oldFragment).commit()
        Log.d("groupActivity", "here")
        notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)

        return POSITION_NONE
    }

}