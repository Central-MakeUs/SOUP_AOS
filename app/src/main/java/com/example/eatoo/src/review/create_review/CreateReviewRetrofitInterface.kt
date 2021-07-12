package com.example.eatoo.src.review.create_review

import com.example.eatoo.src.review.create_review.model.Review2Request
import com.example.eatoo.src.review.create_review.model.ReviewResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CreateReviewRetrofitInterface {
    @POST ("/app/reviews/{userIdx}/stores")
    fun postReview2(
        @Path("userIdx") userIdx : Int,
        @Body review2Request: Review2Request
    ) : Call<ReviewResponse>
}