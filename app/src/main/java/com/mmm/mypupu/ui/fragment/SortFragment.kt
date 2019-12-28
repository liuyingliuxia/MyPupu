package com.mmm.mypupu.ui.fragment


import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.*

import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.SortContentAdapter
import com.mmm.mypupu.ui.bean.Catalog
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import com.mmm.mypupu.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_sort.*
import kotlinx.android.synthetic.main.fragment_sort.ivSearch
import kotlinx.android.synthetic.main.fragment_sort.view.*
import kotlinx.android.synthetic.main.fragment_sort.vp2SortContext
import kotlinx.android.synthetic.main.fragment_sort_test.*
import kotlinx.android.synthetic.main.fragment_sort_test.view.*

class SortFragment : Fragment() {
    private var list :ArrayList<Catalog> = ArrayList ()

    private lateinit var sortContentAdapter: SortContentAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val mView = inflater.inflate(R.layout.fragment_sort_test, container, false)

        //初始化右侧显示内容
        val mlist = getList()
        sortContentAdapter = SortContentAdapter(context!!, mlist )
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mView.rvContent.layoutManager =linearLayoutManager
        mView.rvContent.adapter = sortContentAdapter

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mCatalogId :ArrayList<RadioButton> = arrayListOf(rbCatalog1,rbCatalog2,rbCatalog3,rbCatalog4,rbCatalog5,rbCatalog6,rbCatalog7,
            rbCatalog8,rbCatalog9,rbCatalog10,rbCatalog11,rbCatalog12,rbCatalog13,rbCatalog14,rbCatalog15,rbCatalog16,rbCatalog17)

        initCatalogText(mCatalogId)

        //一次只能滑动一页，不能快速滑动
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(rvContent)
        ivSearch.setOnClickListener{v->run{
            val intent  = Intent()
            intent.setClass(this@SortFragment.context!!, SearchActivity::class.java)
            startActivity(intent)

        }}
    }

    fun getList(): ArrayList<Catalog> {

        for (i in 0 until mContentHeadImg.size) {
            list.add( Catalog(mContentHeadImg[i] ) )
        }
        return list
    }


    fun initCatalogText (mList :ArrayList <RadioButton> ){
        val llm : LinearLayoutManager = rvContent.layoutManager as LinearLayoutManager

        for ( i in 0 .. 16 ){
            mList[i].setText(mCatalogText[i])
            mList[i].setOnClickListener { run{
                //让其他按钮变回原样
                mList[i].setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                val mSize: Float = resources.getDimension(R.dimen.mmm_font_s5)
                mList[i].setTextSize(mSize)
                for ( j in 0 until i  )
                {mList[j].setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
                    val mSizeNor: Float = resources.getDimension(R.dimen.mmm_font_s4)
                    mList[j].setTextSize(mSizeNor)}
                for ( k in i+1 .. 16  )
                {
                    mList[k].setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
                    val mSizeNor: Float = resources.getDimension(R.dimen.mmm_font_s4)
                    mList[k].setTextSize(mSizeNor)
                }
                //切换到指定的item
             /*同样效果
              llm.scrollToPositionWithOffset( i , 0 )
                llm.stackFromEnd = false*/
                moveToPosition(i)
            } }
        }
    }


    private var move = false
    fun moveToPosition(index : Int){
        val firstItem = linearLayoutManager.findFirstVisibleItemPosition()
        val lastItem = linearLayoutManager.findLastVisibleItemPosition()
        if ( index <= firstItem){
            rvContent.scrollToPosition(index)
        }else if ( index <= lastItem){
            val top = rvContent.getChildAt(index - firstItem).top
            rvContent.scrollBy(0,top)
        }else {
            rvContent.scrollToPosition(index)
            move = true
        }
    }



}
