package com.example.eatoo.util

import com.example.eatoo.config.ApplicationClass

fun getUserName() : String? =
    ApplicationClass.sSharedPreferences.getString(ApplicationClass.USER_NAME, "")

fun getGroupName() : String? =
    ApplicationClass.sSharedPreferences.getString(ApplicationClass.GROUP_NAME, "")