package com.example.eatoo.src.mypage.invite.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class InviteCodeResponse(
    @SerializedName("result") val result : InviteCodeResult
): BaseResponse()

data class InviteCodeResult(
    @SerializedName("code") val code : String
)