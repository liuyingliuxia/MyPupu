package com.mmm.mypupu.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment

class myUtil {
    companion object{

        fun jumpView(context: Context , newActivity:Activity){
            val intent = Intent()
                intent.setClass(context!!, newActivity::class.java)
            context.startActivity(intent)
        }

        fun jumpView(context: Context , newFragment:Fragment){
            val intent = Intent()
            intent.setClass(context!!, newFragment::class.java)
            context.startActivity(intent)
        }

        fun talk (context: Context, input :String){
            Toast.makeText( context , input , Toast.LENGTH_SHORT).show()
        }
    }
}