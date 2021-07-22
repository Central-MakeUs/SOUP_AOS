package com.makeus.eatoo.util

import com.makeus.eatoo.config.ApplicationClass

fun putSharedPref(jwt: String, userIdx: Int) {
    ApplicationClass.sSharedPreferences.edit()
        .putString(ApplicationClass.X_ACCESS_TOKEN, jwt)
        .putInt(ApplicationClass.USER_IDX, userIdx).apply()
}

fun putSharedPrefUser(nickname : String) {
    ApplicationClass.sSharedPreferences.edit()
        .putString(ApplicationClass.USER_NICKNAME, nickname).apply()
}

