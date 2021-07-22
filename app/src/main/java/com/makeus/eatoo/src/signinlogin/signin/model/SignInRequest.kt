package com.makeus.eatoo.src.signinlogin.signin.model

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("name") val name : String,
    @SerializedName("phone") val phone : String,
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String,
    @SerializedName("passwordConfirm") val passwordConfirm : String,
    @SerializedName("nickName") val nickName : String
)