package com.example.eatoo.src.signinlogin.login

import com.example.eatoo.src.signinlogin.login.model.LoginRequest
import com.example.eatoo.src.signinlogin.login.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginInterface {

    @GET("app/users/login/")
    fun getUsers() : Call<LoginResponse>

    @POST("app/users/login/")
    fun postSignUp(@Body body : LoginRequest): Call<LoginResponse>
}