package com.makeus.eatoo.src.signinlogin.login

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.signinlogin.login.model.LoginRequest
import com.makeus.eatoo.src.signinlogin.login.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService (val view : LoginActivityView){


    fun tryGetData() {
        val logininerface = ApplicationClass.sRetrofit.create(LoginInterface::class.java)
        logininerface.getUsers().enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                view.onGetUserSuccess(response.body() as LoginResponse)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                view.onGetUserFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostLogin(postLoginRequest: LoginRequest){
        val logininerface = ApplicationClass.sRetrofit.create(LoginInterface::class.java)
        logininerface.postSignUp(postLoginRequest).enqueue(object :
                Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                view.onPostLoginSuccess(response.body() as LoginResponse)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                view.onPostLoginFailure(t.message ?: "통신 오류")
            }
        })
    }
}