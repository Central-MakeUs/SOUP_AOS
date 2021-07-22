package com.makeus.eatoo.src.mypage.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class MyPageResponse(
   @SerializedName("result") val result: MyPageResult
): BaseResponse()

data class MyPageResult(
    @SerializedName("characters")  val characters: Int,
    @SerializedName("color")  val color: Int,
    @SerializedName("getUserKeywordRes")  val getUserKeywordRes: List<GetUserKeywordRe>,
    @SerializedName("nickName")  val nickName: String,
    @SerializedName("userIdx")  val userIdx: Int
)

data class GetUserKeywordRe(
    @SerializedName("isNew") val isNew: String,
    @SerializedName("isPrefer") val isPrefer: String,
    @SerializedName("name") val name: String,
    @SerializedName("size") val size: Int,
    @SerializedName("userKeywordIdx") val userKeywordIdx: Int
)