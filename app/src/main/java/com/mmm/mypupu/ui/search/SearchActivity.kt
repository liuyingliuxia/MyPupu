package com.mmm.mypupu.ui.search

import android.annotation.TargetApi
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.githang.statusbar.StatusBarCompat
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.SearchHistoryBean
import com.mmm.mypupu.ui.fragment.*
import com.mmm.mypupu.util.HistoryUtil
import com.mmm.mypupu.util.myUtil
import com.mmm.mypupu.util.myUtil.Companion.hideKeyforard
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(){

    val mHistoryFragmemt = SearchHistoryFragmemt()
    val mResultFragment = SearchResultFragment()
    val mInputFragment = SearchInputFragment()
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        //设置沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this,getColor(R.color.color1), true)
        //第一次进入时 获取焦点并 强制显示软键盘
        initFirst()
        initView()
}
    //各种事件
    private fun initView(){
        //输入框文字变化监听
        etSearch.addTextChangedListener(object : TextWatcher{
            //输入框文字变化后
            override fun afterTextChanged(s: Editable?) {
                if (etSearch.text.isNullOrEmpty()){
                    replaceFragment(mHistoryFragmemt,R.id.llContainer)
                    ivClose.visibility = View.INVISIBLE
                }
                else {
                    ivClose.visibility = View.VISIBLE
                    //弹出自动补全框
                    replaceFragment(mInputFragment,R.id.llContainer)
                }
            }

            //输入框文字变化前
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (etSearch.text.isNullOrEmpty()){
                    replaceFragment(mHistoryFragmemt,R.id.llContainer)
                    ivClose.visibility = View.INVISIBLE
                }
                else {
                    ivClose.visibility = View.VISIBLE
                    //弹出自动补全框
                    replaceFragment(mInputFragment,R.id.llContainer)
                }
            }

            //输入框文字变化时
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (etSearch.text.isNullOrEmpty()){
                    replaceFragment(mHistoryFragmemt,R.id.llContainer)
                    ivClose.visibility = View.INVISIBLE
                }
                else {
                    ivClose.visibility = View.VISIBLE
                    //弹出自动补全框
                    replaceFragment(mInputFragment,R.id.llContainer)
                }
            }
        })

        // 搜索功能
        etSearch.setOnEditorActionListener { v, actionId, event ->
            //按下回车搜索时
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                if ( etSearch.text.isNullOrEmpty()) {
                    Toast.makeText(this , "输入不能为空！", Toast.LENGTH_SHORT).show()
                } else {
                    etSearch.isFocusable = false
                    hideKeyforard(etSearch , this)
                    //添加历史记录
                    val historyBean = SearchHistoryBean( etSearch.text.toString() ,true )
                    HistoryUtil.saveSearchHistory(historyBean.title!!,this)
                    replaceFragment(mResultFragment,R.id.llContainer)
                }
            }
            false
        }
        //每次点击清空输入内容，都弹出键盘
        ivClose.setOnClickListener {
            etSearch.text = null
            myUtil.showKeyBoard(etSearch , window )
        }

        etSearch.setOnClickListener {
            myUtil.showKeyBoard(etSearch , window )
        }

        tvSearch.setOnClickListener {
            if ( etSearch.text.isNullOrEmpty()) {
                Toast.makeText(this , "输入不能为空！", Toast.LENGTH_SHORT).show()
            } else {
                //  etSearch.isFocusable = false
                hideKeyforard(etSearch , this)
                //添加历史记录
                HistoryUtil.saveSearchHistory(etSearch.text.toString(),this)
                replaceFragment(mResultFragment,R.id.llContainer)
            }
        }
    }

    private fun initFirst(){
        myUtil.showKeyBoard(etSearch , window )
        //初始化显示第一个 fragment
        addFragment(mHistoryFragmemt,R.id.llContainer)
        ivBack.setOnClickListener {  this.finish()}
    }

    //Fragment 的扩展函数
    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) = beginTransaction().func().commit()

    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) = supportFragmentManager.inTransaction { add(frameId, fragment) }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) = supportFragmentManager.inTransaction{replace(frameId, fragment)}

}
