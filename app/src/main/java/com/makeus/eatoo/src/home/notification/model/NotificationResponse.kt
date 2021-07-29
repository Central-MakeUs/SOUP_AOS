package com.makeus.eatoo.src.home.notification.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class NotificationResponse(
   @SerializedName("result") val result: List<NotificationResult>
) : BaseResponse()

data class NotificationResult(
    @SerializedName("createdAt")  val createdAt: String,
    @SerializedName("getMemberRes")  val getMemberRes: List<GetMemberRe>,
    @SerializedName("isChecked")  val isChecked: String,
    @SerializedName("mateIdx")  val mateIdx: Int,
    @SerializedName("mateName")   val mateName: String,
    @SerializedName("noticeName")   val noticeName: String,
    @SerializedName("noticeStatus")  val noticeStatus: Int,
    @SerializedName("storeName")  val storeName: String,
    @SerializedName("time")  val time: String
)

data class GetMemberRe(
    @SerializedName("characters")  val characters: Int,
    @SerializedName("color")  val color: Int,
    @SerializedName("nickName")  val nickName: String,
    @SerializedName("singleStatus")  val singleStatus: String,
    @SerializedName("userIdx")  val userIdx: Int
)