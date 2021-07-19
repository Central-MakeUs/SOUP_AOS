package com.example.eatoo.src.home.group

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityGroupBinding
import com.example.eatoo.src.home.group.adapter.GroupViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class GroupActivity : BaseActivity<ActivityGroupBinding>(ActivityGroupBinding::inflate) {

    private lateinit var viewPagerAdapter : GroupViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setGroupViewPager()

    }

    private fun setGroupViewPager() {
        viewPagerAdapter = GroupViewPagerAdapter(this)

        binding.viewpagerGroup.adapter = viewPagerAdapter


        TabLayoutMediator( binding.tablayoutGroup, binding.viewpagerGroup) { tab, position ->
            tab.text = when(position) {
                0 -> resources.getString(R.string.group_main)
                1 -> resources.getString(R.string.group_category)
                2 -> resources.getString(R.string.group_vote)
                3 -> resources.getString(R.string.group_member)
                else -> resources.getString(R.string.group_main)
            }
        }.attach()

    }
}