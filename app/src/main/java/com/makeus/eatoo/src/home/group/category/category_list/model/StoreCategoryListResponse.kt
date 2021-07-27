package com.makeus.eatoo.src.home.group.category.category_list.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class StoreCategoryListResponse(
    @SerializedName("result") val result : StoreCategoryListResult
) : BaseResponse()

data class StoreCategoryListResult(
    @SerializedName("getStoresRes")  val getStoresRes: List<GetStoresRe>,
    @SerializedName("singleStatus")  val singleStatus: String
)

data class GetStoresRe(
    @SerializedName("getStoreKeywordRes") val getStoreKeywordRes: List<GetStoreKeywordRe>,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("isLiked") val isLiked: String,
    @SerializedName("name") val name: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("storeIdx") val storeIdx: Int
)

data class GetStoreKeywordRe(
    @SerializedName("name") val name: String
)

