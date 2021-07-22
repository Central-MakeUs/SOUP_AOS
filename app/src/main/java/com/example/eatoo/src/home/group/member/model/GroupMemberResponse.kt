package com.example.eatoo.src.home.group.member.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GroupMemberResponse(
    @SerializedName("result") val result: GroupMemberResult
) : BaseResponse()
