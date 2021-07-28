package com.makeus.eatoo.src.home.group.main.store_rec.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class StoreRecResponse(
    @SerializedName("result") val result: ArrayList<StoreRecResult>
) : BaseResponse()