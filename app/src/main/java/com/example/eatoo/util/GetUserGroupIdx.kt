package com.example.eatoo.util

import com.example.eatoo.config.ApplicationClass

fun getUserIdx() : Int =
    ApplicationClass.sSharedPreferences.getInt(ApplicationClass.USER_IDX, -1)

fun getGroupIdx() : Int =
    ApplicationClass.sSharedPreferences.getInt(ApplicationClass.GROUP_IDX, -1)