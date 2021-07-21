package com.example.eatoo.src.home.group.member

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.eatoo.R
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentGroupMemberBinding
import com.example.eatoo.src.home.group.member.adapter.MemberRVAdapter
import com.example.eatoo.src.home.group.member.model.GroupMember
import com.example.eatoo.src.home.group.member.model.GroupMemberResponse
import com.example.eatoo.util.getGroupIdx
import com.example.eatoo.util.getGroupName
import com.example.eatoo.util.getUserIdx

class GroupMemberFragment : BaseFragment<FragmentGroupMemberBinding>(
    FragmentGroupMemberBinding::bind,
    R.layout.fragment_group_member
), GroupMemberView{

    private lateinit var memberAdapter: MemberRVAdapter

    override fun onResume() {
        super.onResume()

        getGroupMember()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvMemberGroupName.text = getGroupName()

    }

    private fun getGroupMember() {
        showLoadingDialog(requireContext())
        GroupMemberService(this).tryGetGroupMember(getUserIdx(), getGroupIdx())
    }

    fun notifiedSingleStatusChange() {
        showCustomToast("혼밥 여부 변화!!!!")
    }

    //////server result

    override fun onGetGroupMemberSuccess(response: GroupMemberResponse) {
        dismissLoadingDialog()

        val memberList: MutableList<GroupMember> =
            response.result.members as MutableList<GroupMember>
        memberList.add(
            GroupMember(
                userIdx = -10,
                color = 0,
                characters = 0,
                nickName = " ",
                singleStatus = " "
            )
        )


        memberAdapter = MemberRVAdapter(requireContext(), memberList.toList())
        binding.rvMember.apply {
            adapter = memberAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
            clipToPadding = false
        }
    }

    override fun onGetGroupMemberFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message ?: resources.getString(R.string.failed_connection))
    }

}