package com.makeus.eatoo.src.mypage.keyword

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.src.mypage.keyword.model.DietKeywordReq
import com.makeus.eatoo.src.mypage.keyword.model.DietKeywordResponse
import com.makeus.eatoo.src.mypage.keyword.model.PostUserKeywordReq
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DietKeywordService(val view : DietKeywordView) {

    fun tryGetDietKeyword(userIdx: Int) {
        val dietKeywordRetrofitInterface = ApplicationClass.sRetrofit.create(
            DietKeywordRetrofitInterface::class.java
        )
        dietKeywordRetrofitInterface.getDietKeyword(userIdx)
            .enqueue(object :
                Callback<DietKeywordResponse> {
                override fun onResponse(
                    call: Call<DietKeywordResponse>,
                    response: Response<DietKeywordResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetDietKeywordSuccess(response.body() as DietKeywordResponse)
                        else view.onGetDietKeywordFail(it.message)
                    }
                }

                override fun onFailure(call: Call<DietKeywordResponse>, t: Throwable) {
                    view.onGetDietKeywordFail(t.message)
                }

            })
    }

    fun tryPostDietKeyword(userIdx: Int, dietKeywordReqList: List<DietKeywordReq>) {
        val dietKeywordRetrofitInterface = ApplicationClass.sRetrofit.create(
            DietKeywordRetrofitInterface::class.java
        )
        dietKeywordRetrofitInterface.postDietKeyword(userIdx, PostUserKeywordReq(dietKeywordReqList))
            .enqueue(object :
                Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onPostDietKeywordSuccess(response.body() as BaseResponse)
                        else view.onPostDietKeywordFail(it.message)
                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    view.onPostDietKeywordFail(t.message)
                }

            })
    }
}