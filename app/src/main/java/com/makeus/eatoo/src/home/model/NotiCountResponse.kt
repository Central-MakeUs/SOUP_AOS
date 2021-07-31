package com.makeus.eatoo.src.home.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class NotiCountResponse(
    @SerializedName("result") val result : NotiCountResult
): BaseResponse()

data class NotiCountResult(
    @SerializedName("count") val count : Int
)
