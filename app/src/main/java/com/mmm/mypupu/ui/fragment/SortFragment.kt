package com.mmm.mypupu.ui.fragment


import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.SortContentAdapter
import com.mmm.mypupu.ui.adapter.SortLeftAdapter
import com.mmm.mypupu.ui.bean.Catalog
import com.mmm.mypupu.ui.data.*
import com.mmm.mypupu.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_sort.view.*
import kotlinx.android.synthetic.main.fragment_sort_test.*
import kotlinx.android.synthetic.main.fragment_sort_test.view.*
import kotlinx.android.synthetic.main.fragment_sort_test.view.rvContent

class SortFragment : Fragment() {
    private var list :ArrayList<Catalog> = ArrayList ()
    private var tvLeftList: ArrayList<TextView?>  = ArrayList()
    private lateinit var sortLeftAdapter: SortLeftAdapter
    private lateinit var sortContentAdapter: SortContentAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var lastposition: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val mView = inflater.inflate(R.layout.fragment_sort, container, false)
        //初始化左侧内容
        val mLeftText = mCatalogText

        sortLeftAdapter = SortLeftAdapter (context!!, mLeftText,mView.rvContent)
        gridLayoutManager = GridLayoutManager(context,1)
        mView.rvLeft.layoutManager =gridLayoutManager
        mView.rvLeft.adapter = sortLeftAdapter

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
        //val mCatalogId :ArrayList<RadioButton> = arrayListOf(rbCatalog1,rbCatalog2,rbCatalog3,rbCatalog4,rbCatalog5,rbCatalog6,rbCatalog7,
          //  rbCatalog8,rbCatalog9,rbCatalog10,rbCatalog11,rbCatalog12,rbCatalog13,rbCatalog14,rbCatalog15,rbCatalog16,rbCatalog17)
            //initCatalogText(mCatalogId)

          //  rbCatalog1.isChecked = true
            //一次只能滑动一页，不能快速滑动
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(rvContent)
            ivSearch.setOnClickListener{v->run{
                val intent  = Intent()
                intent.setClass(this@SortFragment.context!!, SearchActivity::class.java)
                startActivity(intent)

            }}

       /* val llmLeft = view.rvLeft.layoutManager
        val llmRight = view.rvContent.layoutManager
        for ( i in 0 until mCatalogText.size)
        {
            tvLeftList[i] = llmLeft!!.findViewByPosition(i)
            tvLeftList[i]!!.setOnClickListener { kotlin.run {
                tvLeftList[i]!!.isEnabled = false
                goBackOther(tvLeftList , i )
                //切换到指定的item
                llmRight!!.scrollToPosition(i)
            } }
        }*/
            //滑动时，左侧导航栏同步改变选中状态

       /*     rvContent.addOnScrollListener(object :RecyclerView.OnScrollListener(){

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val manager = recyclerView.layoutManager as GridLayoutManager
                    // 当不滚动时
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //获取最后一个完全显示的ItemPosition
                        lastposition = manager.findFirstVisibleItemPosition()
                        //滑动到特殊的位置会报错 闪退     java.lang.ArrayIndexOutOfBoundsException: length=17; index=-1
                        Log.e("position", lastposition.toString())
                        if (lastposition == -1) {
                            Log.e("position", lastposition.toString())
                        } else {
                            mCatalogId[lastposition].isChecked = true
                            for (j in 0 until lastposition) {
                                mCatalogId[j].isChecked = false
                                goBackOtherRB(mCatalogId, lastposition)
                            }
                            for (k in lastposition + 1..16) {
                                mCatalogId[k].isChecked = false
                                goBackOtherRB(mCatalogId, lastposition)
                            }

                            if (mCatalogId[lastposition].isChecked == false) {
                                goBackOtherRB(mCatalogId, lastposition)
                            } else {
                                mCatalogId[lastposition].setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                val mSize: Float = resources.getDimension(R.dimen.mmm_font_s5)
                                mCatalogId[lastposition].setTextSize(mSize)
                            }
                            Log.e("state", mCatalogId[lastposition].isChecked.toString())
                      }
                    }
                }
            })*/
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

/*
    fun initCatalogText (mList :ArrayList <RadioButton> ){
        val llm : LinearLayoutManager = rvContent.layoutManager as LinearLayoutManager

        for ( i in 0 until mCatalogText.size ){
            mList[i].setText(mCatalogText[i])

            mList[i].setOnClickListener { run{
               goBackOtherRB(mList , i)
                //切换到指定的item
                llm.scrollToPositionWithOffset(i, 0)
                llm.stackFromEnd = false

            } }
        }
    }*/

fun goBackOther(mList: ArrayList<TextView?> , i :Int) {
    //选中的按钮 变大
    mList[i]?.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
    val mSize: Float = resources.getDimension(R.dimen.mmm_font_s5)
    mList[i]?.setTextSize(mSize)
    //让其他按钮变回原样
    for (j in 0 until i) {
        mList[j]?.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        val mSizeNor: Float = resources.getDimension(R.dimen.mmm_font_s4)
        mList[j]?.setTextSize(mSizeNor)
        mList[j]?.isEnabled = true
    }
    for (k in i + 1..16) {
        mList[k]?.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        val mSizeNor: Float = resources.getDimension(R.dimen.mmm_font_s4)
        mList[k]?.setTextSize(mSizeNor)
        mList[k]?.isEnabled = true
        }
    }
}
