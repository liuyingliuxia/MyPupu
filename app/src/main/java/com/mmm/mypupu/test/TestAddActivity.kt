package com.mmm.mypupu.test

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.data.goodsTitle
import com.mmm.mypupu.ui.data.test
import com.mmm.mypupu.ui.widgets.SaveHistory
import kotlinx.android.synthetic.main.activity_test_add.*

class TestAddActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_add)
        btnAdd.setOnClickListener{run{
            for ( i in 0 ..5)
            {
                saveSearchHistory(test[i],this)
                val mTextView = TextView(this)
                val m = getSearchHistory(this)
                mTextView.setText(m[i])
                mTextView.background = resources.getDrawable(R.drawable.mmm_c90_s01_s07_w1_r9006)
                mTextView.setTextColor(resources.getColor(R.color.color3))
                mTextView.setPadding(20, 10, 20, 10)
                llAddContainer.addView(mTextView)
            }
        }}

        btnSub.setOnClickListener{run{
            llAddContainer.removeAllViews()
           }
        }
    }

    fun saveSearchHistory ( inputText: String, sContext: Context){
        val sp : SharedPreferences = sContext.getSharedPreferences("history", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sp.edit()
        editor.putString("item",inputText)
        editor.apply()
    }

    fun getSearchHistory (gContext: Context):ArrayList<String>{
        val sp : SharedPreferences = gContext.getSharedPreferences("history", Context.MODE_PRIVATE)
        val mList = ArrayList<String>()
        for ( i in 0..5){
            mList.add(sp.getString("item"," ")!!)
        }
        return mList
    }
}
