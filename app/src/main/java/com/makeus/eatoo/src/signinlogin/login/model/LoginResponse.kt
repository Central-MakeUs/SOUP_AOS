package com.makeus.eatoo.src.signinlogin.login.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
        @SerializedName("result")
        val result : Result,
        @SerializedName("isSuccess")
        val isSuccess : Boolean,
        @SerializedName("code")
        val code : Int,
        @SerializedName("message")
        val message : String
)

data class Result(
        @SerializedName("jwt")
        val jwt : String,
        @SerializedName("userIdx")
        val userIdx : Int
)