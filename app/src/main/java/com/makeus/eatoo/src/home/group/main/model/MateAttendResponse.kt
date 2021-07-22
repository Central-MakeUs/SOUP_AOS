package com.makeus.eatoo.src.home.group.main.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class MateAttendResponse(
    @SerializedName("result") val result: MateAttendResult
) : BaseResponse()

data class MateAttendResult(
    @SerializedName("userMateIdx") val userMateIdx: Int
)
