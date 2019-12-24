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
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_search_history.*
import kotlinx.android.synthetic.main.item_search_history.*
import java.nio.file.WatchEvent
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity(),TextWatcher {
    val mStack = Stack <Fragment> ()

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
        //进入视图时 获取焦点并 强制显示软键盘
        actSearch.isFocusable = true
        actSearch.isFocusableInTouchMode = true
        actSearch.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        initFragment()
        changeFragment(0)
        ivBack.setOnClickListener {  this.finish()}
        actSearch.addTextChangedListener(this)
        initInput(this)

    }

    //输入的判断 并 添加搜索记录
    private fun initInput (context: Context) {
        actSearch.setOnEditorActionListener(object :TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                //按下回车搜索时
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if ( actSearch.text.isNullOrEmpty()) {
                        Toast.makeText(context , "输入不能为空！", Toast.LENGTH_SHORT).show()
                    }
                    else {//输入不为空时，跳转搜索页面 并传值
                        val bundle = Bundle()
                        Log.e("输入的内容",actSearch.text.toString())
                        bundle.putString("Key",actSearch.text.toString())
                        val manager = supportFragmentManager.beginTransaction()
                        val rFragmemt = SearchResultFragment()
                        rFragmemt.arguments = bundle
                        manager.add(R.id.llContainer,rFragmemt)
                        manager.commit()
                        manager.show(rFragmemt)
                        hideKeyforard(actSearch)
                        return false
                    }
                    return false
                }
                return false
            }
        })

        ivClose.setOnClickListener { run{
            actSearch.text = null
            ivClose.visibility  =View.INVISIBLE
            changeFragment(0)
          }
        }
    }
    //强制隐藏软键盘
    private fun hideKeyforard (view:View) {
        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
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
    override fun afterTextChanged(s: Editable?) {
        if (actSearch.text.isNullOrEmpty()){
            changeFragment(0)
            ivClose.visibility = View.INVISIBLE
        }
        else {
           ivClose.visibility = View.VISIBLE
            changeFragment(2)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        if (actSearch.text.isNullOrEmpty()){
            changeFragment(0)
            ivClose.visibility = View.INVISIBLE
        }
        else {
            ivClose.visibility = View.VISIBLE
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (actSearch.text.isNullOrEmpty()){
            changeFragment(0)
            ivClose.visibility = View.INVISIBLE
        }
        else {
            ivClose.visibility = View.VISIBLE
            changeFragment(2)
        }
    }
}
