package com.mmm.mypupu.ui.search

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        //设置沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this,getColor(R.color.color1), true)
        initFragment()
        changeFragment(0)
        ivBack.setOnClickListener { v -> this.finish()}
        actSearch.addTextChangedListener(this)
        initInput(this)
      /*  var mAutoAdapter = ArrayAdapter<String> (this,R.layout.item_search_text_auto,searchAutoCompleteGoods)
        actSearch.setAdapter(mAutoAdapter)*/
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
                    else {//输入不为空时，跳转搜索页面
                        changeFragment(1)
                    }
                }
                return false
            }
        })

        ivClose.setOnClickListener { v -> run{
            actSearch.text = null
            ivClose.visibility  =View.INVISIBLE
            changeFragment(0)
          }
        }
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
