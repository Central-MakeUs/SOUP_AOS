package com.makeus.eatoo.src.home.group.groupmatesuggestion

interface TimeDialogInterface {
    fun onSetStartTime( hour : String, minute : String)

    fun onSetEndTime( hour : String, minute : String)

    fun onSetLimitTime( hour : String, minute : String)
}