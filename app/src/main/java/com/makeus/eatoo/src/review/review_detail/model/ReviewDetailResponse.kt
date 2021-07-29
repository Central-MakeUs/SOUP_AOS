package com.makeus.eatoo.src.review.review_detail.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class ReviewDetailResponse(
   @SerializedName("result") val result: ReviewDetailResult
) : BaseResponse()

data class ReviewDetailResult(
    @SerializedName("address")  val address: String,
    @SerializedName("contents")  val contents: String,
    @SerializedName("getReviewKeywordRes")  val getReviewKeywordRes: List<GetReviewKeywordRe>,
    @SerializedName("imgUrl")  val imgUrl: String,
    @SerializedName("link")  val link: String,
    @SerializedName("menuName")  val menuName: String,
    @SerializedName("rating")  val rating: Double,
    @SerializedName("storeCategoryIdx")  val storeCategoryIdx: Int,
    @SerializedName("storeName")  val storeName: String
)

data class GetReviewKeywordRe(
    @SerializedName("name")  val name: String
)