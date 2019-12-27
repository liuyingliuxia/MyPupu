package com.mmm.mypupu.util

import android.content.Context
import android.widget.Toast

object myToast {
    fun myToast (context: Context, input :String){
        Toast.makeText( context , input ,Toast.LENGTH_SHORT).show()
    }
}