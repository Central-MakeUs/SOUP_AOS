package com.makeus.eatoo.src.mypage

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.mypage.model.AccountDeleteResponse
import com.makeus.eatoo.src.mypage.model.MyPageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService(val view : MyPageView) {

    fun tryGetMyPage(userIdx: Int) {
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(
            MyPageRetrofitInterface::class.java
        )
        myPageRetrofitInterface.getMyPage(userIdx)
            .enqueue(object :
                Callback<MyPageResponse> {
                override fun onResponse(
                    call: Call<MyPageResponse>,
                    response: Response<MyPageResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetMyPageSuccess(response.body() as MyPageResponse)
                        else view.onGetMyPageFail(it.message)
                    }
                }

                override fun onFailure(call: Call<MyPageResponse>, t: Throwable) {
                    view.onGetMyPageFail(t.message)
                }

            })
    }

    fun tryDeleteAccount(userIdx: Int) {
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(
            MyPageRetrofitInterface::class.java
        )
        myPageRetrofitInterface.deleteAccount(userIdx)
            .enqueue(object :
                Callback<AccountDeleteResponse> {
                override fun onResponse(
                    call: Call<AccountDeleteResponse>,
                    response: Response<AccountDeleteResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onDeleteAccountSuccess(response.body() as AccountDeleteResponse)
                        else view.onDeleteAccountFail(it.message)
                    }
                }

                override fun onFailure(call: Call<AccountDeleteResponse>, t: Throwable) {
                    view.onDeleteAccountFail(t.message)
                }

            })
    }
}