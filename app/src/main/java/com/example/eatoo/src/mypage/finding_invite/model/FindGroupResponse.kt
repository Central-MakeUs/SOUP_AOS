package com.example.eatoo.src.mypage.finding_invite.model

import com.example.eatoo.config.BaseResponse
import com.example.eatoo.src.signinlogin.login.model.Result
import com.google.gson.annotations.SerializedName

data class FindGroupResponse(
    @SerializedName("result") val result : FindGroupResult
):BaseResponse()

data class FindGroupResult(
    @SerializedName("userMateIdx") val userMateIdx : Int
)