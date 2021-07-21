package com.example.eatoo.src.mypage.finding_invite

import com.example.eatoo.src.mypage.finding_invite.model.FindGroupResponse


interface FindInviteDialogView {
    fun onPostGroupParticipateSuccess(response: FindGroupResponse)

    fun onPostGroupParticipateFailure(message: String)
}