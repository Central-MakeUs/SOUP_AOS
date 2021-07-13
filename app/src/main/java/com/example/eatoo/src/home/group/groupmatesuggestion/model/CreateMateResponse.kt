package com.example.eatoo.src.home.group.groupmatesuggestion.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class CreateMateResponse(
        @SerializedName("result")
        val result : Result
):BaseResponse()

data class Result(
        @SerializedName("mateIdx")
        val mateIdx : Int
)