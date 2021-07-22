package com.example.eatoo.src.home.group.member

import com.example.eatoo.src.home.group.member.model.GroupMemberResponse

interface GroupMemberView {
    fun onGetGroupMemberSuccess(response : GroupMemberResponse)

    fun onGetGroupMemberFail(message : String?)
}