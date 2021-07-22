package com.makeus.eatoo.src.main


import com.makeus.eatoo.src.main.model.UserResponse

interface MainActivityView {

    fun onGetUserDateSuccess(response : UserResponse)

    fun onGetUserDateFail(message : String)


}