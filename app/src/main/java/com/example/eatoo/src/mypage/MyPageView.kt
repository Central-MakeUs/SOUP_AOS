package com.example.eatoo.src.mypage

import com.example.eatoo.src.mypage.model.MyPageResponse

interface MyPageView {
    fun onGetMyPageSuccess(response : MyPageResponse)
    fun onGetMyPageFail(message : String?)
}