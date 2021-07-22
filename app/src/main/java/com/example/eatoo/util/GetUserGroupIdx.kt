package com.example.eatoo.util

import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.config.ApplicationClass.Companion.USER_NICKNAME

fun getUserIdx() : Int =
    ApplicationClass.sSharedPreferences.getInt(ApplicationClass.USER_IDX, -1)

fun getGroupIdx() : Int =
    ApplicationClass.sSharedPreferences.getInt(ApplicationClass.GROUP_IDX, -1)

fun getUserNickName() : String =
    ApplicationClass.sSharedPreferences.getString(ApplicationClass.USER_NICKNAME, USER_NICKNAME).toString()