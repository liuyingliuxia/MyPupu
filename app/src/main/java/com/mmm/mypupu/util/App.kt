package com.mmm.mypupu.util

import android.annotation.SuppressLint
import android.app.Application


/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/21 下午10:13
 * 描述:
 */
@SuppressLint("Registered")
object App : Application() {
       var sInstance  = App
        override fun onCreate() {
            super.onCreate()
            sInstance = this
        }

        fun getInstance() :App{
          return sInstance
    }
}