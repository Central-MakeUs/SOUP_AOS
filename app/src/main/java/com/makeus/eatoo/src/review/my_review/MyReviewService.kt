package com.makeus.eatoo.src.review.my_review

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.review.my_review.model.MyReviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyReviewService(val view : MyReviewView) {

    fun tryGetMyReview(userIdx : Int) {
        val myReviewRetrofitInterface = ApplicationClass.sRetrofit.create(
            MyReviewRetrofitInterface::class.java
        )
        myReviewRetrofitInterface.getMyReview(userIdx)
            .enqueue(object :
                Callback<MyReviewResponse> {
                override fun onResponse(
                    call: Call<MyReviewResponse>,
                    response: Response<MyReviewResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetMyReviewSuccess(response.body() as MyReviewResponse)
                        else view.onGetMyReviewFail(it.message)
                    }
                }

                override fun onFailure(call: Call<MyReviewResponse>, t: Throwable) {
                    view.onGetMyReviewFail(t.message)
                }

            })
    }

}