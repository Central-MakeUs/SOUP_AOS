package com.makeus.eatoo.src.home.group.member.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GroupMemberResponse(
    @SerializedName("result") val result: GroupMemberResult
) : BaseResponse()
