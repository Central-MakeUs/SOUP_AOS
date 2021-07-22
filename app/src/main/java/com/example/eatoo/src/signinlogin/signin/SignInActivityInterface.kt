package com.example.eatoo.src.signinlogin.signin

import com.example.eatoo.src.signinlogin.signin.model.SignInRequest
import com.example.eatoo.src.signinlogin.signin.model.SignInResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SignInActivityInterface {

    @GET("app/users/")
    fun getUsers() : Call<SignInResponse>

    @POST("app/users/")
    fun postSignUp(@Body body : SignInRequest): Call<SignInResponse>

}