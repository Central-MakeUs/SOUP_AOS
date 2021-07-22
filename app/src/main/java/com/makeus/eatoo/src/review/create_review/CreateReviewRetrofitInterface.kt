package com.makeus.eatoo.src.review.create_review

import com.makeus.eatoo.src.review.create_review.model.ExistingStoreReviewResponse
import com.makeus.eatoo.src.review.create_review.model.Review1Request
import com.makeus.eatoo.src.review.create_review.model.Review2Request
import com.makeus.eatoo.src.review.create_review.model.ReviewResponse
import retrofit2.Call
import retrofit2.http.*

interface CreateReviewRetrofitInterface {

    @GET("/app/reviews/{userIdx}/{storeIdx}")
    fun getStoreInfo(
        @Path("userIdx") userIdx : Int,
        @Path("storeIdx") storeIdx : Int
    ) : Call<ExistingStoreReviewResponse>

    @POST ("/app/reviews/{userIdx}")
    fun postReview1(
        @Path("userIdx") userIdx : Int,
        @Body review1Request: Review1Request
    ) : Call<ReviewResponse>

    @POST ("/app/reviews/{userIdx}/stores")
    fun postReview2(
        @Path("userIdx") userIdx : Int,
        @Body review2Request: Review2Request
    ) : Call<ReviewResponse>
}