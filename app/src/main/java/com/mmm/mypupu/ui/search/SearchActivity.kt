package com.mmm.mypupu.ui.search

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.util.rangeTo
import androidx.fragment.app.Fragment
import com.githang.statusbar.StatusBarCompat
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.data.searchAutoCompleteGoods
import com.mmm.mypupu.ui.fragment.*
import com.mmm.mypupu.ui.widgets.CustomLayout
import com.mmm.mypupu.ui.widgets.SaveHistory
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_search_history.*
import kotlinx.android.synthetic.main.item_search_history.*
import java.nio.file.WatchEvent
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity(),TextWatcher {
    val mStack = Stack <Fragment> ()
    val HISTORY = 0
    val RESULT = 1
    val INPUT = 2
    val mHistoryFragmemt by lazy { SearchHistoryFragmemt() }
    val mResultFragment by lazy { SearchResultFragment() }
    val mInputFragment by lazy {SearchInputFragment()}
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        //设置沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this,getColor(R.color.color1), true)
        //第一次进入时 获取焦点并 强制显示软键盘
        initFirst()
        //输入框文字变化监听
        actSearch.addTextChangedListener(this)
        // 搜索功能
        search(this)
}
    private fun initFirst(){
        showKeyBoard()
        initFragment()
        //初始化显示第一个 fragment
        changeFragment(HISTORY)
        ivBack.setOnClickListener {  this.finish()}
    }

    private fun search (context: Context) {
        actSearch.setOnEditorActionListener { v, actionId, event ->
            //按下回车搜索时
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                if ( actSearch.text.isNullOrEmpty()) {
                    Toast.makeText(context , "输入不能为空！", Toast.LENGTH_SHORT).show()
                } else {
                    changeFragment(RESULT)
                    actSearch.isFocusable = false
                    hideKeyforard(actSearch)
                    //添加历史记录
                    SaveHistory.saveSearchHistory(actSearch.text.toString(),context)
                    ivClean.visibility = View.VISIBLE
                    tvNoHistory.visibility = View.INVISIBLE
                }
            }
            false
        }

        ivClose.setOnClickListener { run{
            actSearch.text = null
            showKeyBoard()
            changeFragment(HISTORY)
          }
        }
    }

    //强制隐藏软键盘
    private fun hideKeyforard (view:View) {
        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
    }

    // 获取焦点并 强制显示软键盘
    private fun showKeyBoard (){
        actSearch.isFocusable = true
        actSearch.isFocusableInTouchMode = true
        actSearch.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    private fun initFragment () {
        val manager = supportFragmentManager.beginTransaction()
        manager.add (R.id.llContainer,mHistoryFragmemt)
        manager.add (R.id.llContainer,mResultFragment)
        manager.add (R.id.llContainer,mInputFragment)
        manager.commit()
        mStack.add(mHistoryFragmemt)
        mStack.add(mResultFragment)
        mStack.add(mInputFragment)
    }

    private fun changeFragment (position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack){
            manager.hide (fragment)
        }
        manager.show (mStack[position])
        manager.commit()
    }
    //输入框文字变化后
    override fun afterTextChanged(s: Editable?) {
        if (actSearch.text.isNullOrEmpty()){
            changeFragment(HISTORY)
            ivClose.visibility = View.INVISIBLE
        }
        else {
           ivClose.visibility = View.VISIBLE
            //弹出自动补全框
             changeFragment(INPUT)
        }
    }

    //输入框文字变化前
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        if (actSearch.text.isNullOrEmpty()){
            changeFragment(HISTORY)
            ivClose.visibility = View.INVISIBLE
        }
        else {
            ivClose.visibility = View.VISIBLE
            //弹出自动补全框
            changeFragment(INPUT)
        }
    }

    //输入框文字变化时
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (actSearch.text.isNullOrEmpty()){
            changeFragment(0)
            ivClose.visibility = View.INVISIBLE
        }
        else {
            ivClose.visibility = View.VISIBLE
            //弹出自动补全框
            changeFragment(INPUT)
        }
    }
}
