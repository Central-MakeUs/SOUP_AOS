package com.example.eatoo.src.signinlogin.login.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
        @SerializedName("email") val email : String,
        @SerializedName("password") val password : String
)