package com.example.hyunndymovieapp.util

class TimeHelper{

    val SEC = 60
    val MIN = 60
    val HOUR = 24
    val DAY = 30
    val MONTH = 12

    fun formatTimetoString(regTime:Long) : String{

        var curTime = System.currentTimeMillis()
        var diffTime = (curTime - regTime) / 1000
        var msg = ""

        if(diffTime < SEC) {
            msg = "방금 전"
        } else if( (diffTime / SEC) < MIN) {
            msg = diffTime.toString() + "분 전"
        } else if( (diffTime / MIN) < HOUR) {
            msg = diffTime.toString() + "시간 전"
        } else if( (diffTime / HOUR) < DAY) {
            msg = diffTime.toString() + "일 전"
        } else if( (diffTime / DAY) < MONTH) {
            msg = diffTime.toString() + "달 전"
        } else {
            msg = diffTime.toString() + "년 전"
        }
        return msg
    }
}