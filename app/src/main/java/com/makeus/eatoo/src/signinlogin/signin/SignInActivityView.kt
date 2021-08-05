package com.makeus.eatoo.src.signinlogin.signin

import com.makeus.eatoo.src.signinlogin.signin.model.Check_Response
import com.makeus.eatoo.src.signinlogin.signin.model.SignInResponse

interface SignInActivityView {
    fun onGetUserSuccess(response: Check_Response)

    fun onGetUserFailure(message: String)

    fun onPostSignUpSuccess(response: SignInResponse)

    fun onPostSignUpFailure(message: String)
}