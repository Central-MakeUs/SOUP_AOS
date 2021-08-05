package com.makeus.eatoo.src.home.group.member

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.databinding.FragmentGroupMemberBinding
import com.makeus.eatoo.src.home.group.member.adapter.MemberRVAdapter
import com.makeus.eatoo.src.home.group.member.dialog.AddMemberDialog
import com.makeus.eatoo.src.home.group.member.dialog.AddMemberDialogInterface
import com.makeus.eatoo.src.home.group.member.model.GroupMember
import com.makeus.eatoo.src.home.group.member.model.GroupMemberResponse
import com.makeus.eatoo.src.home.group.member.model.MemberDietKeyword
import com.makeus.eatoo.src.mypage.invite.InviteDialog
import com.makeus.eatoo.src.mypage.invite.InviteService
import com.makeus.eatoo.src.mypage.invite.model.InviteCodeResponse
import com.makeus.eatoo.util.getGroupIdx
import com.makeus.eatoo.util.getGroupName
import com.makeus.eatoo.util.getUserIdx

class GroupMemberFragment : BaseFragment<FragmentGroupMemberBinding>(
    FragmentGroupMemberBinding::bind,
    R.layout.fragment_group_member
), GroupMemberView, AddMemberDialogInterface, MemberRVAdapter.OnAddMemberClickListener {

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

    fun reload() {
        getGroupMember()
    }

    override fun onDialogAddMemberClicked() {
        context?.let {
            showLoadingDialog(it)
            GroupMemberService(this).tryGetInviteCode(getUserIdx(), getGroupIdx())
        }
    }

    override fun onMemberClicked(memberInfo : GroupMember) {
        //dialog
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
                singleStatus = " ",
                getUserKeywordRes = listOf()
            )
        )
        memberAdapter = MemberRVAdapter(requireContext(), memberList.toList(), this)
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

    override fun onGetInviteCodeDateSuccess(response: InviteCodeResponse) {
        dismissLoadingDialog()
        context?.let {
            val dialog = InviteDialog(it, response.result.code)
            dialog.show()
        }

    }

    override fun onGetInviteCodeDateFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

    override fun onAddMemberClicked() {
        context?.let {
            val dialog = AddMemberDialog(it, this)
            dialog.show()
        }

    }


}