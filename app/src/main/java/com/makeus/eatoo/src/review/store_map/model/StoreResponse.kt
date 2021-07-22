package com.makeus.eatoo.src.review.store_map.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class StoreResponse(
    @SerializedName("result") val result : List<Store>
) : BaseResponse()
