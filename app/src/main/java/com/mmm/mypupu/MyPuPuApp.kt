package com.mmm.mypupu

import android.app.Application
import org.litepal.LitePal


class MyPuPuApp : Application(){
    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
    }
}