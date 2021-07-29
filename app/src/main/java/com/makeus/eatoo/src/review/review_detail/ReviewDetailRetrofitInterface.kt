package com.makeus.eatoo.src.review.review_detail

import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.src.review.review_detail.model.PatchReviewRequest
import com.makeus.eatoo.src.review.review_detail.model.ReviewDetailResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ReviewDetailRetrofitInterface {
    @GET("/app/reviews/{userIdx}/{reviewIdx}/detail")
    fun getReviewDetail(
        @Path("userIdx") userIdx : Int,
        @Path("reviewIdx") reviewIdx : Int
    ) : Call<ReviewDetailResponse>

    @PATCH("/app/reviews/{userIdx}/{reviewIdx}")
    fun patchReview(
        @Path("userIdx") userIdx : Int,
        @Path("reviewIdx") reviewIdx : Int,
        @Body patchReviewRequest : PatchReviewRequest
    ) : Call<BaseResponse>
}