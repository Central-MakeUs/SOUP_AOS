package com.makeus.eatoo.src.signinlogin.login

import com.makeus.eatoo.src.signinlogin.login.model.LoginResponse

interface LoginActivityView {

    fun onGetUserSuccess(response: LoginResponse)

    fun onGetUserFailure(message: String)

    fun onPostLoginSuccess(response: LoginResponse)

    fun onPostLoginFailure(message: String)
}