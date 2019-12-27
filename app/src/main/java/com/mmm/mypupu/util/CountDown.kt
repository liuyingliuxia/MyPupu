package com.mmm.mypupu.util

import android.os.CountDownTimer
import java.text.ParsePosition
import java.text.SimpleDateFormat

object CountDown {
    val timer  = object :CountDownTimer(60000 , 1000 ) {
        override fun onFinish() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onTick(millisUntilFinished: Long) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
    //时间 字符串互转
    fun timeTransToString (time :Long ) :String {
        return SimpleDateFormat("HH:mm:ss").format(time)
    }

    fun transToTimeStamp(date:String):Long{
        return SimpleDateFormat("YY-MM-DD-hh-mm-ss").parse(date, ParsePosition(0)).time
    }
}