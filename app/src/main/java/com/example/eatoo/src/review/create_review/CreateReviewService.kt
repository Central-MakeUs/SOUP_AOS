package com.example.eatoo.src.review.create_review

import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.review.create_review.model.Review2Request
import com.example.eatoo.src.review.create_review.model.ReviewResponse
import com.example.eatoo.src.review.store_map.StoreMapRetrofitInterface
import com.example.eatoo.src.review.store_map.model.StoreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateReviewService(val view : CreateReviewView) {

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