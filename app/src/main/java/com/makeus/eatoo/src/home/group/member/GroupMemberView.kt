package com.makeus.eatoo.src.home.group.member

import com.makeus.eatoo.src.home.group.member.model.GroupMemberDetailResponse
import com.makeus.eatoo.src.home.group.member.model.GroupMemberResponse
import com.makeus.eatoo.src.mypage.invite.model.InviteCodeResponse

interface GroupMemberView {
    fun onGetGroupMemberSuccess(response : GroupMemberResponse)
    fun onGetGroupMemberFail(message : String?)

    fun onGetInviteCodeDateSuccess(response : InviteCodeResponse)
    fun onGetInviteCodeDateFail(message : String?)

    fun onGetMemberDetailSuccess(response: GroupMemberDetailResponse)
    fun onGetMmeberDetailFail(message: String?)
}