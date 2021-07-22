package com.makeus.eatoo.util

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.ApplicationClass.Companion.USER_NICKNAME

fun getUserIdx() : Int =
    ApplicationClass.sSharedPreferences.getInt(ApplicationClass.USER_IDX, -1)

fun getGroupIdx() : Int =
    ApplicationClass.sSharedPreferences.getInt(ApplicationClass.GROUP_IDX, -1)

fun getUserNickName() : String =
    ApplicationClass.sSharedPreferences.getString(ApplicationClass.USER_NICKNAME, USER_NICKNAME).toString()

fun getGroupName() : String? =
    ApplicationClass.sSharedPreferences.getString(ApplicationClass.GROUP_NAME, "")