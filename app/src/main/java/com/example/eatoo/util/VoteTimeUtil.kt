package com.example.eatoo.util

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDate() : String { //yyyy년 mm월 dd일 (0 안 붙임)
    val year = Calendar.getInstance().get(Calendar.YEAR)
    val month = Calendar.getInstance().get(Calendar.MONTH) + 1
    val date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    return "${year}년 ${month}월 ${date}일"
}

fun formatTo12HTimeStamp(hour : Int, minute : Int) : String { //오전(오후) hh:mm
    var hourDisplayed = hour
    var isAfterNoon = false

    if(hour == 12) {
        isAfterNoon = true
    }
    if(hour in 13..24) {
        hourDisplayed -= 12
        isAfterNoon = true
    }
    val time12H = "$hourDisplayed:$minute"

    val timeFormat = SimpleDateFormat("HH:mm")
    val time = timeFormat.parse(time12H)
    val timeStamp12H = timeFormat.format(time!!) //0 붙인 12H time stamp.

    return if(isAfterNoon) "오후 $timeStamp12H"
    else "오전 $timeStamp12H"
}

fun formatTo24HTimeStamp(dueDateTime : String) : String { //서버 전송용 변환 //HH:mm
    val timeNumPart = dueDateTime.split(" ")[1]
    var hourPart = timeNumPart.split(":")[0].toInt()
    val minPart = timeNumPart.split(":")[1]
    val amPmPart = dueDateTime.split(" ")[0]

    if(amPmPart == "오후" && hourPart != 12) {
            hourPart += 12
    }
    val timeFormat = SimpleDateFormat("HH:mm")
    val time = timeFormat.parse("${hourPart}:$minPart")
    return timeFormat.format(time!!) //0 붙인 24H time stamp.
}

fun formatDateToTimeStamp(date : String) : String {
    val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
    val timeStampFormat = SimpleDateFormat("yyyy-MM-dd")

    val parsedDate = dateFormat.parse(date)
    return timeStampFormat.format(parsedDate!!)
}
