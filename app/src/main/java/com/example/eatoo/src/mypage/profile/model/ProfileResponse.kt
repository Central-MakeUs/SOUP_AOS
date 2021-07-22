package com.example.eatoo.src.mypage.profile.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("result") val result : ProfileResult
): BaseResponse()
