package com.example.eatoo.src.home.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class MateResponse(
    @SerializedName("result") val result: ArrayList<MateResultResponse>
) : BaseResponse()