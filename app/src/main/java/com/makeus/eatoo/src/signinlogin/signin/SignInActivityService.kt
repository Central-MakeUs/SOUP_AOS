package com.makeus.eatoo.src.signinlogin.signin

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.signinlogin.signin.model.Check_Response
import com.makeus.eatoo.src.signinlogin.signin.model.SignInRequest
import com.makeus.eatoo.src.signinlogin.signin.model.SignInResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivityService (val view : SignInActivityView){


    fun tryemailcheck(email : String) {
        val emailInterface = ApplicationClass.sRetrofit.create(SignInActivityInterface::class.java)
        emailInterface.getEmailCheck(email).enqueue(object : Callback<Check_Response> {
            override fun onResponse(call: Call<Check_Response>, response: Response<Check_Response>) {
                view.onGetUserSuccess(response.body() as Check_Response)
            }

            override fun onFailure(call: Call<Check_Response>, t: Throwable) {
                view.onGetUserFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPhonecheck(phone : String) {
        val emailInterface = ApplicationClass.sRetrofit.create(SignInActivityInterface::class.java)
        emailInterface.getPhoneCheck(phone).enqueue(object : Callback<Check_Response> {
            override fun onResponse(call: Call<Check_Response>, response: Response<Check_Response>) {
                view.onGetUserSuccess(response.body() as Check_Response)
            }

            override fun onFailure(call: Call<Check_Response>, t: Throwable) {
                view.onGetUserFailure(t.message ?: "통신 오류")
            }
        })
    }


    fun tryPostSignUp(postSignUpRequest: SignInRequest){
        val signInterface = ApplicationClass.sRetrofit.create(SignInActivityInterface::class.java)
        signInterface.postSignUp(postSignUpRequest).enqueue(object :
            Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                view.onPostSignUpSuccess(response.body() as SignInResponse)
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                view.onPostSignUpFailure(t.message ?: "통신 오류")
            }
        })
    }
}