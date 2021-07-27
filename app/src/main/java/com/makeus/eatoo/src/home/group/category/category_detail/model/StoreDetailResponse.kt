package com.makeus.eatoo.src.home.group.category.category_detail.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class StoreDetailResponse(
    @SerializedName("result") val result: StoreDetailResult
): BaseResponse()

data class StoreDetailResult(
    @SerializedName("getReviewImgRes")  val getReviewImgRes: List<GetReviewImgRe>,
    @SerializedName("getReviewRes")   val getReviewRes: List<GetReviewRe>,
    @SerializedName("getStoreKeywordRes")  val getStoreKeywordRes: List<GetStoreKeywordRe>,
    @SerializedName("imgUrl")  val imgUrl: String,
    @SerializedName("isLiked")  val isLiked: String,
    @SerializedName("latitude")  val latitude: Double,
    @SerializedName("longitude")  val longitude: Double,
    @SerializedName("address")  val address: String,
    @SerializedName("storeName")  val storeName: String
)

data class GetReviewImgRe(
    @SerializedName("imgUrl") val imgUrl: String
)

data class GetReviewRe(
    @SerializedName("contents") val contents: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("menuName") val menuName: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("reviewIdx") val reviewIdx: Int
)

data class GetStoreKeywordRe(
    val name: String
)