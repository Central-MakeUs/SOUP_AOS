package com.example.eatoo.src.home.group.main.model

import com.example.eatoo.config.BaseResponse
import com.example.eatoo.src.home.group.member.model.GroupMemberResult
import com.google.gson.annotations.SerializedName

data class MateAttendResponse(
    @SerializedName("result") val result: MateAttendResult
) : BaseResponse()

data class MateAttendResult(
    @SerializedName("userMateIdx") val userMateIdx: Int
)
