package com.makeus.eatoo.src.mypage.finding_invite.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class FindGroupResponse(
    @SerializedName("result") val result : FindGroupResult
):BaseResponse()

data class FindGroupResult(
    @SerializedName("userMateIdx") val userMateIdx : Int
)