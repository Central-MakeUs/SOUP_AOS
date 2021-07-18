package com.example.eatoo.src.mypage.profile.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class PatchProfileResponse(
    @SerializedName("result") val result : String
): BaseResponse()
