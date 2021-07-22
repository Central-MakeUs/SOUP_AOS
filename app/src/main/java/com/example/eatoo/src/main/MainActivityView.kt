package com.example.eatoo.src.main


import com.example.eatoo.src.main.model.UserResponse

interface MainActivityView {

    fun onGetUserDateSuccess(response : UserResponse)

    fun onGetUserDateFail(message : String)


}