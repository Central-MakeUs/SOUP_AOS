package com.example.eatoo.src.review.my_review

import com.example.eatoo.src.review.my_review.model.MyReviewResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyReviewRetrofitInterface {
    @GET("/app/reviews/{userIdx}")
    fun getMyReview(@Path ("userIdx") userIdx : Int) : Call<MyReviewResponse>
}