package com.example.eatoo.src.review.store_map.domain

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class StoreResponse(
    @SerializedName("result") val result : List<Store>
) : BaseResponse()
