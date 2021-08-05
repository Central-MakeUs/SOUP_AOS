package com.makeus.eatoo.src.home.group

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityGroupBinding
import com.makeus.eatoo.single_status.SingleService
import com.makeus.eatoo.single_status.SingleView
import com.makeus.eatoo.src.home.group.adapter.GroupViewPagerAdapter
import com.makeus.eatoo.src.home.group.member.GroupMemberService
import com.makeus.eatoo.src.home.group.member.GroupMemberView
import com.makeus.eatoo.src.home.group.member.model.GroupMemberResponse
import com.makeus.eatoo.util.getGroupIdx
import com.makeus.eatoo.util.getGroupName
import com.makeus.eatoo.util.getUserIdx
import com.google.android.material.tabs.TabLayoutMediator
import com.makeus.eatoo.src.home.group.member.model.GroupMemberDetailResponse
import com.makeus.eatoo.src.mypage.invite.model.InviteCodeResponse


class GroupActivity : BaseActivity<ActivityGroupBinding>(ActivityGroupBinding::inflate),
    SingleView, GroupMemberView,LeaveGroupActivityInterface, View.OnClickListener{

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
        binding.customToolbar.leftIcon.setOnClickListener (this)
        binding.customToolbar.rightIcon.setOnClickListener (this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_toolbar_left-> {
                val dialog = LeaveGroupActivityDialog(this, this, getGroupName()?:"")
                dialog.show()
            }
            R.id.iv_toolbar_right -> {
                changeToSingle = binding.customToolbar.rightIcon.drawable.constantState?.equals(
                    ContextCompat.getDrawable(this, R.drawable.ic_icon)?.constantState
                ) ?:false
                SingleService(this).tryPatchSingleStatus(getUserIdx())
            }
        }
    }

    override fun onBackPressed() {
        val dialog = LeaveGroupActivityDialog(this, this, getGroupName()?:"")
        dialog.show()
    }

    override fun onLeaveGroupClicked() {
        finish()
    }

    private fun setGroupViewPager() {
        viewPagerAdapter = GroupViewPagerAdapter(this, this)

        binding.viewpagerGroup.apply {
            adapter = viewPagerAdapter
            isUserInputEnabled = false
        }


        TabLayoutMediator(binding.tablayoutGroup, binding.viewpagerGroup) { tab, position ->
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
        if(changeToSingle) binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icons)
        else binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icon)

        viewPagerAdapter.notifyDataSetChanged()
    }


    override fun onPatchSingleStatusFail(message: String?) {
        showCustomToast(message ?: resources.getString(R.string.failed_connection))
    }

    override fun onGetGroupMemberSuccess(response: GroupMemberResponse) {
        if(response.result.singleStatus == "ON") binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icons)
        else binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icon)
    }

    override fun onGetGroupMemberFail(message: String?) {
        showCustomToast(message ?: resources.getString(R.string.failed_connection))
    }

    override fun onGetInviteCodeDateSuccess(response: InviteCodeResponse) {
    }

    override fun onGetInviteCodeDateFail(message: String?) {
    }

    override fun onGetMemberDetailSuccess(response: GroupMemberDetailResponse) {
    }

    override fun onGetMmeberDetailFail(message: String?) {
    }




}