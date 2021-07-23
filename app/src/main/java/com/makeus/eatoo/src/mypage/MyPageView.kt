package com.makeus.eatoo.src.mypage

import com.makeus.eatoo.src.mypage.model.MyPageResponse

interface MyPageView {
    fun onGetMyPageSuccess(response : MyPageResponse)
    fun onGetMyPageFail(message : String?)
}