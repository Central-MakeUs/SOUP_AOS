package com.makeus.eatoo.src.mypage.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class AccountDeleteResponse(
    @SerializedName("result") val result : AccountDeleteResult
) : BaseResponse()

data class AccountDeleteResult(
    @SerializedName("userIdx") val userIdx : Int
)