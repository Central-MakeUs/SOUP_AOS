package com.example.eatoo.src.home.group

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityGroupBinding
import com.example.eatoo.single_status.SingleResultResponse
import com.example.eatoo.single_status.SingleService
import com.example.eatoo.single_status.SingleView
import com.example.eatoo.src.home.group.adapter.GroupViewPagerAdapter
import com.example.eatoo.src.home.group.member.GroupMemberService
import com.example.eatoo.src.home.group.member.GroupMemberView
import com.example.eatoo.src.home.group.member.model.GroupMemberResponse
import com.example.eatoo.src.main.MainActivity
import com.example.eatoo.util.getGroupIdx
import com.example.eatoo.util.getGroupName
import com.example.eatoo.util.getUserIdx
import com.google.android.material.tabs.TabLayoutMediator

class GroupActivity : BaseActivity<ActivityGroupBinding>(ActivityGroupBinding::inflate),
    SingleView, GroupMemberView {

    private lateinit var viewPagerAdapter : GroupViewPagerAdapter
    private var changeToSingle = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getSingleStatus()
        binding.customToolbar.title.text = getGroupName()
        setGroupViewPager()
        setOnClickListeners()

    }

    private fun getSingleStatus() {
        GroupMemberService(this).tryGetGroupMember(getUserIdx(), getGroupIdx())
    }


    private fun setOnClickListeners() {
        binding.customToolbar.leftIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.customToolbar.rightIcon.setOnClickListener {
            changeToSingle = binding.customToolbar.rightIcon.drawable.constantState?.equals(
                ContextCompat.getDrawable(this, R.drawable.ic_icon)?.constantState) ?:false
            SingleService(this).tryPatchSingleStatus(getUserIdx() )
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

    override fun onPatchSingleStatusSuccess() {
        showCustomToast("on off 전환 성공")
        if(changeToSingle) binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icons)
        else binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icon)

    }

    override fun onPatchSingleStatusFail(message: String?) {
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

    override fun onGetGroupMemberSuccess(response: GroupMemberResponse) {
        showCustomToast("single status 가져오기 성공!")
        Log.d("groupActivity", response.toString())
        if(response.result.singleStatus == "ON") binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icons)
        else binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icon)
    }

    override fun onGetGroupMemberFail(message: String?) {
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }
}