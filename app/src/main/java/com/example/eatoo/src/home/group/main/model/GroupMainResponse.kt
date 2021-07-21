package com.example.eatoo.src.home.group.main.model

import com.example.eatoo.config.BaseResponse
import com.example.eatoo.src.home.group.member.model.GroupMemberResult
import com.google.gson.annotations.SerializedName

data class GroupMainResponse(
    @SerializedName("result") val result: GroupMainResult
) : BaseResponse()

data class GroupMainResult(
    @SerializedName("singleStatus") val singleStatus: String,
    @SerializedName("getMateRes") val getMateRes: ArrayList<GroupMateResponse>,
    @SerializedName("getStoreRes") val getStoreRes: ArrayList<GroupStoreResponse>
)

data class GroupMateResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("mateIdx") val mateIdx: Int,
    @SerializedName("isAttended") val isAttended: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("mateName") val mateName: String,
    @SerializedName("storeName") val storeName: String,
    @SerializedName("time") val time: String,
    @SerializedName("membersNumber") val membersNumber: Int

)

data class GroupStoreResponse(
    @SerializedName("storeIdx") val storeIdx: Int,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("isLiked") val isLiked: String,
    @SerializedName("storeName") val storeName: String,
    @SerializedName("address") val address: String,
    @SerializedName("rating") val rating: Double
)
