package com.mmm.mypupu.util

import android.content.Context
import android.widget.Toast
//简短的吐司工具类
object mT {
    fun t (context: Context, input :String){
        Toast.makeText( context , input ,Toast.LENGTH_SHORT).show()
    }
}