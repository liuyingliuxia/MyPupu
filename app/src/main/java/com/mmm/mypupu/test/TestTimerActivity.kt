package com.mmm.mypupu.test

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.mmm.mypupu.R
import kotlinx.android.synthetic.main.activity_test_timer.*
import kotlinx.android.synthetic.main.item_flash_sale_head.*

class TestTimerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_timer)
      /*  btnGo.setOnClickListener{
            kotlin.run {
                ctTime.base = SystemClock.elapsedRealtime()
                ctTime.start()
            }
        }
        ctTime.base = SystemClock.elapsedRealtime()
        ctTime.start()

        ctTime.setOnChronometerTickListener {
            kotlin.run {
                if (SystemClock.elapsedRealtime() - ctTime.base > 10 * 1000){
                    ctTime.stop()
                }
            }
        }*/
    /*    btnCountDown.setOnClickListener { run{
                btnCountDown!!.sendVerifyCode()
          }
        }*/
    CountDown(tvSecond)

    }

    fun  CountDown(v : TextView) {
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onFinish() {

            }

            override fun onTick(millisUntilFinished: Long) {
                v.setText(millisUntilFinished.div(1000).toString())
                }

            }.start()
        }

    private fun initCountDown (){
         var mHandler:Handler =  Handler{
                when (it.what ){
                    5 -> {
                        tvSecond.isEnabled = true
                    }
                }
             false
            }
        }
}
