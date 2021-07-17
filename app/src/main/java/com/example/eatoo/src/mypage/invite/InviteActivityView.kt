package com.example.eatoo.src.mypage.invite

import com.example.eatoo.src.mypage.invite.model.InviteCodeResponse
import com.example.eatoo.src.mypage.invite.model.InviteResponse

interface InviteActivityView {
    fun onGetInviteGroupDateSuccess(response : InviteResponse)

    fun onGetInviteGroupDateFail(message : String)

    fun onGetInviteCodeDateSuccess(response : InviteCodeResponse)

    fun onGetInviteCodeDateFail(message : String)
}