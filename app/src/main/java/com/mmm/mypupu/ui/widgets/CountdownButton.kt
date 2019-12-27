package com.mmm.mypupu.ui.widgets

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.Button
import com.mmm.mypupu.R

/**
 * 创建时间： 2018/12/26 0026
 * 创建人：ldm
 * 功能描述：
 */
class CountdownButton(mContext: Context, attrSet: AttributeSet) : Button(mContext, attrSet) {
    private val mHandler: Handler = Handler();
    private var mCountTime = 60


    init {
        this.text = "点击获取验证码"
    }

    /*
           倒计时，并处理点击事件
        */
    fun sendVerifyCode() {
        mHandler.postDelayed(countDown, 0)
    }

    /*
        倒计时
     */
    private val countDown = object : Runnable {
        override fun run() {
            this@CountdownButton.text = mCountTime.toString() + "s "
            this@CountdownButton.setBackgroundColor(resources.getColor(R.color.color3))
            this@CountdownButton.setTextColor(resources.getColor(R.color.color1))
            this@CountdownButton.isEnabled = false

            if (mCountTime > 0) {
                mHandler.postDelayed(this, 1000)
            } else {
                resetCounter()
            }
            mCountTime--
        }
    }

    fun removeRunable() {
        mHandler.removeCallbacks(countDown)
    }

    //重置按钮状态
    fun resetCounter(vararg text: String) {
        this.isEnabled = true
        if (text.isNotEmpty() && "" != text[0]) {
            this.text = text[0]
        } else {
            this.text = "点击获取验证码"
        }
        this.setBackgroundColor(resources.getColor(R.color.transparent))
        this.setTextColor(resources.getColor(R.color.color9))
        mCountTime = 60
    }
}