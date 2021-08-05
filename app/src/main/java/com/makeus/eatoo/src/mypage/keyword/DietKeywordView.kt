package com.makeus.eatoo.src.mypage.keyword

import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.src.mypage.keyword.model.DietKeywordResponse

interface DietKeywordView {
    fun onPostDietKeywordSuccess(response : BaseResponse)
    fun onPostDietKeywordFail(message : String?)

    fun onGetDietKeywordSuccess(response : DietKeywordResponse)
    fun onGetDietKeywordFail(message: String?)
}