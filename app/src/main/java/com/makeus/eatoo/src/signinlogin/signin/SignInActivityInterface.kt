package com.makeus.eatoo.src.signinlogin.signin

import com.makeus.eatoo.src.signinlogin.signin.model.Check_Response
import com.makeus.eatoo.src.signinlogin.signin.model.SignInRequest
import com.makeus.eatoo.src.signinlogin.signin.model.SignInResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignInActivityInterface {

    @GET("app/users/")
    fun getUsers() : Call<SignInResponse>


    @GET("app/users/email-check")
    fun getEmailCheck(@Query("email") email : String) : Call<Check_Response>


    @GET("app/users/phone-check")
    fun getPhoneCheck(@Query("phone") phone : String) : Call<Check_Response>



    @POST("app/users/")
    fun postSignUp(@Body body : SignInRequest): Call<SignInResponse>

}