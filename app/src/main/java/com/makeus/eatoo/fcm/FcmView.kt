package com.makeus.eatoo.fcm

interface FcmView {
    fun onPostFcmSuccess()
    fun onPostFcmFail(message : String?)
}