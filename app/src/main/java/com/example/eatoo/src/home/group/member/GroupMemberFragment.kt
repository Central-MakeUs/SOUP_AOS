package com.example.eatoo.src.home.group.member

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.eatoo.R
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentGroupMemberBinding
import com.example.eatoo.src.home.group.member.model.GroupMemberResponse
import com.example.eatoo.util.getGroupIdx
import com.example.eatoo.util.getUserIdx

class GroupMemberFragment : BaseFragment<FragmentGroupMemberBinding>(FragmentGroupMemberBinding::bind, R.layout.fragment_group_member), GroupMemberView{


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getGroupMember()

    }

    private fun getGroupMember() {
        val userIdx = getUserIdx()
        val groupIdx = getGroupIdx()

        GroupMemberService(this).tryGetGroupMember(userIdx, groupIdx)
    }

    override fun onGetGroupMemberSuccess(response: GroupMemberResponse) {
        Toast.makeText(requireContext(), "요청에 성공하였습니다.", Toast.LENGTH_SHORT).show()
        Log.d("groupmemberfrag", response.toString())
    }

    override fun onGetGroupMemberFail(message: String?) {
        Toast.makeText(requireContext(), message , Toast.LENGTH_SHORT).show()
    }

}