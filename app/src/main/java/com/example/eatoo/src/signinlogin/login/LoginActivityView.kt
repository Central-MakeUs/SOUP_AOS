package com.example.eatoo.src.signinlogin.login

import com.example.eatoo.src.signinlogin.login.model.LoginResponse
import com.example.eatoo.src.signinlogin.signin.model.SignInResponse

interface LoginActivityView {

    fun onGetUserSuccess(response: LoginResponse)

    fun onGetUserFailure(message: String)

    fun onPostLoginSuccess(response: LoginResponse)

    fun onPostLoginFailure(message: String)
}