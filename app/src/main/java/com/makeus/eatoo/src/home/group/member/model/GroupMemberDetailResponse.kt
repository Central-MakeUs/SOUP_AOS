package com.makeus.eatoo.src.home.group.member.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class GroupMemberDetailResponse(
    @SerializedName("result") val result : GroupMemberDetailResult
): BaseResponse()
