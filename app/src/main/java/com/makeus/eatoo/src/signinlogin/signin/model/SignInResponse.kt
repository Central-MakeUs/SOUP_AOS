package com.makeus.eatoo.src.signinlogin.signin.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class SignInResponse(
    @SerializedName("result")
    val result : Result
):BaseResponse()

data class Result(
    @SerializedName("jwt")
    val jwt : String,
    @SerializedName("userIdx")
    val userIdx : Int
)