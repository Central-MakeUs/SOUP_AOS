package com.makeus.eatoo.src.wishlist.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class WishListResponse(
   @SerializedName("result") val result: ArrayList<WishListResult>
) : BaseResponse()

data class WishListResult(
    @SerializedName("getStoreKeywordRes") val getStoreKeywordRes: List<GetStoreKeywordRe>,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("isLiked") val isLiked: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("storeIdx") val storeIdx: Int,
    @SerializedName("storeName") val storeName: String
)

data class GetStoreKeywordRe(
    @SerializedName("name")  val name: String
)