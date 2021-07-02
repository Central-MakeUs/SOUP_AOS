package com.example.eatoo.src.signinlogin.signin

import com.example.eatoo.src.signinlogin.signin.model.SignInResponse

interface SignInActivityView {
    fun onGetUserSuccess(response: SignInResponse)

    fun onGetUserFailure(message: String)

    fun onPostSignUpSuccess(response: SignInResponse)

    fun onPostSignUpFailure(message: String)
}