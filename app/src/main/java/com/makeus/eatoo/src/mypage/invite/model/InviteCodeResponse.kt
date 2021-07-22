package com.makeus.eatoo.src.mypage.invite.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class InviteCodeResponse(
    @SerializedName("result") val result : InviteCodeResult
): BaseResponse()

data class InviteCodeResult(
    @SerializedName("code") val code : String
)