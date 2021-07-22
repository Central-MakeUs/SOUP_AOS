package com.makeus.eatoo.src.home.group.groupmatesuggestion.model

import com.google.gson.annotations.SerializedName

data class CreateMateRequest(
        @SerializedName("groupIdx") val groupIdx : Int,
        @SerializedName("name") val name : String,
        @SerializedName("storeName") val storeName : String,
        @SerializedName("startTime") val startTime : String,
        @SerializedName("endTime") val endTime : String,
        @SerializedName("headCount") val headCount : Int,
        @SerializedName("timeLimit") val timeLimit : String,
        @SerializedName("imgUrl") val imgUrl : String
)