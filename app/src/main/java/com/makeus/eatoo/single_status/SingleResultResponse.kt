package com.makeus.eatoo.single_status

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class SingleResultResponse(
    @SerializedName("result") val result: String
) : BaseResponse()
