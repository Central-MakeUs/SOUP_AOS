package com.example.eatoo.src.home.group.member

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.eatoo.R
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentGroupMemberBinding
import com.example.eatoo.src.home.group.member.adapter.MemberRVAdapter
import com.example.eatoo.src.home.group.member.model.GroupMemberResponse
import com.example.eatoo.util.getGroupIdx
import com.example.eatoo.util.getGroupName
import com.example.eatoo.util.getUserIdx

class GroupMemberFragment : BaseFragment<FragmentGroupMemberBinding>(FragmentGroupMemberBinding::bind, R.layout.fragment_group_member), GroupMemberView{

    private lateinit var memberAdapter : MemberRVAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getGroupMember()
        binding.tvMemberGroupName.text = getGroupName()

    }

    private fun getGroupMember() {
        showLoadingDialog(requireContext())
       GroupMemberService(this).tryGetGroupMember(getUserIdx(), getGroupIdx())
    }

    override fun onGetGroupMemberSuccess(response: GroupMemberResponse) {
        dismissLoadingDialog()
        Log.d("groupmemberfrag", response.toString())
        memberAdapter = MemberRVAdapter(requireContext(), response.result.members)
        binding.rvMember.apply {
            adapter = memberAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
        }
    }

    override fun onGetGroupMemberFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

}