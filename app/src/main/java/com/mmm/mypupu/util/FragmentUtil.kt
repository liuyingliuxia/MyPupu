package com.mmm.mypupu.util

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mmm.mypupu.R

object FragmentUtil {
    //Fragment的拓展函数 /备用
    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) = beginTransaction().func().commit()

    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) = supportFragmentManager.inTransaction { add(frameId, fragment) }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) = supportFragmentManager.inTransaction{replace(frameId, fragment)}

}