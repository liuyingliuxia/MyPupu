package com.mmm.mypupu.util

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mmm.mypupu.R

object FragmentUtil {
     fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}