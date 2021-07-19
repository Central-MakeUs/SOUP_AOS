package com.example.eatoo.src.home.group

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityGroupBinding
import com.example.eatoo.src.home.group.adapter.GroupViewPagerAdapter
import com.example.eatoo.src.main.MainActivity
import com.example.eatoo.util.getGroupName
import com.google.android.material.tabs.TabLayoutMediator

class GroupActivity : BaseActivity<ActivityGroupBinding>(ActivityGroupBinding::inflate) {

    private lateinit var viewPagerAdapter : GroupViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.customToolbar.title.text = getGroupName()
        setGroupViewPager()
        binding.customToolbar.leftIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun setGroupViewPager() {
        viewPagerAdapter = GroupViewPagerAdapter(this)

        binding.viewpagerGroup.apply {
            adapter = viewPagerAdapter
            isUserInputEnabled = false
        }


        TabLayoutMediator( binding.tablayoutGroup, binding.viewpagerGroup) { tab, position ->
            tab.text = when(position) {
                0 -> resources.getString(R.string.group_main)
                1 -> resources.getString(R.string.group_category)
                2 -> resources.getString(R.string.group_vote)
                3 -> resources.getString(R.string.group_member)
                else -> resources.getString(R.string.group_main)
            }
        }.attach()

        binding.tablayoutGroup.isSmoothScrollingEnabled = false

    }
}