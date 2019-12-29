package com.mmm.mypupu.ui.fragment


import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_sort_test.*
import kotlinx.android.synthetic.main.fragment_sort_test.view.*
import kotlin.math.absoluteValue

class SortFragment : Fragment() {
    private var list :ArrayList<Catalog> = ArrayList ()

    private lateinit var sortContentAdapter: SortContentAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var lastposion: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val mView = inflater.inflate(R.layout.fragment_sort_test, container, false)

        //初始化右侧显示内容
        val mlist = getList()
        sortContentAdapter = SortContentAdapter(context!!, mlist )
        gridLayoutManager = GridLayoutManager(context,1)
        mView.rvContent.layoutManager =gridLayoutManager
        mView.rvContent.adapter = sortContentAdapter

        return mView
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mCatalogId :ArrayList<RadioButton> = arrayListOf(rbCatalog1,rbCatalog2,rbCatalog3,rbCatalog4,rbCatalog5,rbCatalog6,rbCatalog7,
            rbCatalog8,rbCatalog9,rbCatalog10,rbCatalog11,rbCatalog12,rbCatalog13,rbCatalog14,rbCatalog15,rbCatalog16,rbCatalog17)
            initCatalogText(mCatalogId)

            rbCatalog1.isChecked = true
            //一次只能滑动一页，不能快速滑动
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(rvContent)
            ivSearch.setOnClickListener{v->run{
                val intent  = Intent()
                intent.setClass(this@SortFragment.context!!, SearchActivity::class.java)
                startActivity(intent)

            }}
            //滑动时，左侧导航栏同步改变选中状态

            rvContent.addOnScrollListener(object :RecyclerView.OnScrollListener(){

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val manager = recyclerView!!.layoutManager as GridLayoutManager
                    // 当不滚动时
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //获取最后一个完全显示的ItemPosition
                        lastposion = manager.findLastCompletelyVisibleItemPosition().absoluteValue
                        mCatalogId[lastposion].isChecked = true
                        for (j in 0 until lastposion) {
                            mCatalogId[j].isChecked = false
                            goBackOtherRB(mCatalogId , lastposion )
                        }
                        for (k in lastposion + 1..16) {
                            mCatalogId[k].isChecked = false
                            goBackOtherRB(mCatalogId , lastposion )
                        }

                        if ( mCatalogId[lastposion].isChecked == false) {
                            goBackOtherRB(mCatalogId , lastposion )
                        }else{
                            mCatalogId[lastposion].setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            val mSize: Float = resources.getDimension(R.dimen.mmm_font_s5)
                            mCatalogId[lastposion].setTextSize(mSize)
                        }
                        Log.e("state" ,   mCatalogId[lastposion].isChecked.toString())
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                }
            })
    }

 /*   //测试数据完整性
    fun test (){
        for ( i in 0 until allItemImgList.size){
                val length= allItemImgList[i].size
                Log.e("图片数据长度第$i 个",length.toString())
        }
    }*/
    fun getList(): ArrayList<Catalog> {

        for (i in 0 until mContentHeadImg.size) {
            list.add( Catalog(mContentHeadImg[i],mAllItemImg1[i], mAllName1[i], mAllItemImg2[i], mAllName2[i],mAllItemImg3[i], mAllName3[i],
                mAllItemImg4[i], mAllName4[i], mAllItemImg5[i], mAllName5[i],mAllItemImg6[i], mAllName6[i],mAllItemImg7[i], mAllName7[i],
                mAllItemImg8[i], mAllName8[i],mAllItemImg9[i], mAllName9[i],mAllItemImg10[i], mAllName10[i],mAllItemImg11[i], mAllName11[i],
                mAllItemImg12[i], mAllName12[i],mAllItemImg13[i], mAllName13[i],mAllItemImg14[i], mAllName14[i],mAllItemImg15[i], mAllName15[i]) )
        }
        return list
    }


    fun initCatalogText (mList :ArrayList <RadioButton> ){
        val llm : LinearLayoutManager = rvContent.layoutManager as LinearLayoutManager

        for ( i in 0 .. 16 ){
            mList[i].setText(mCatalogText[i])
            mList[i].setOnClickListener { run{
               goBackOtherRB(mList , i)
                //切换到指定的item
                llm.scrollToPositionWithOffset(i, 0)
                llm.stackFromEnd = false

            } }
        }
    }

fun goBackOtherRB(mList: ArrayList<RadioButton> , i :Int) {
    //选中的按钮 变大
    mList[i].setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
    val mSize: Float = resources.getDimension(R.dimen.mmm_font_s5)
    mList[i].setTextSize(mSize)
    //让其他按钮变回原样
    for (j in 0 until i) {
        mList[j].setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        val mSizeNor: Float = resources.getDimension(R.dimen.mmm_font_s4)
        mList[j].setTextSize(mSizeNor)
    }
    for (k in i + 1..16) {
        mList[k].setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        val mSizeNor: Float = resources.getDimension(R.dimen.mmm_font_s4)
        mList[k].setTextSize(mSizeNor)
    }

}
}
