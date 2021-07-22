package com.makeus.eatoo.src.review.create_review.create_review2

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.review.create_review.CreateReviewRetrofitInterface
import com.makeus.eatoo.src.review.create_review.model.Review1Request
import com.makeus.eatoo.src.review.create_review.model.Review2Request
import com.makeus.eatoo.src.review.create_review.model.ReviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateReview2Service(val view : CreateReview2View) {

    fun tryPostReview1(userIdx : Int, review1Request: Review1Request) {
        val createReviewRetrofitInterface = ApplicationClass.sRetrofit.create(
            CreateReviewRetrofitInterface::class.java
        )
        createReviewRetrofitInterface.postReview1(userIdx, review1Request)
            .enqueue(object :
                Callback<ReviewResponse> {
                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onPostReview2Success(response.body() as ReviewResponse)
                        else view.onPostReview2Fail(it.message)
                    }
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    view.onPostReview2Fail(t.message)
                }

            })
    }

    fun tryPostReview2(userIdx : Int, review2Request: Review2Request) {
        val createReviewRetrofitInterface = ApplicationClass.sRetrofit.create(
            CreateReviewRetrofitInterface::class.java
        )
        createReviewRetrofitInterface.postReview2(userIdx, review2Request)
            .enqueue(object :
                Callback<ReviewResponse> {
                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onPostReview2Success(response.body() as ReviewResponse)
                        else view.onPostReview2Fail(it.message)
                    }
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    view.onPostReview2Fail(t.message)
                }

            })
    }
}