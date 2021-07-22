package com.example.eatoo.src.home.group.main

import com.example.eatoo.src.home.group.main.model.MateAttendResponse

interface MateAttendView {
    fun onGetMateAttendSuccess(response : MateAttendResponse)

    fun onGetMateAttendFail(message : String?)
}