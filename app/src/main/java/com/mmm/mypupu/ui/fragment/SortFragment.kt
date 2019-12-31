package com.mmm.mypupu.ui.fragment


import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.example.lilingzhi.tworecyc.util.RecycUtil
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.QuickLeftAdapter
import com.mmm.mypupu.ui.adapter.QuickRightAdapter
import com.mmm.mypupu.ui.adapter.SortContentAdapter
import com.mmm.mypupu.ui.adapter.SortLeftAdapter
import com.mmm.mypupu.ui.bean.Catalog
import com.mmm.mypupu.ui.bean.LeftBean
import com.mmm.mypupu.ui.bean.RightBean
import com.mmm.mypupu.ui.data.*
import com.mmm.mypupu.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_sort.*
import kotlinx.android.synthetic.main.fragment_sort.view.*
import kotlinx.android.synthetic.main.fragment_sort_test.*
import kotlinx.android.synthetic.main.fragment_sort_test.ivSearch
import kotlinx.android.synthetic.main.fragment_sort_test.rvContent
import kotlinx.android.synthetic.main.fragment_sort_test.view.*
import kotlinx.android.synthetic.main.fragment_sort_test.view.rvContent
import kotlinx.android.synthetic.main.item_sort_left.*
import kotlinx.android.synthetic.main.item_sort_left.view.*

class SortFragment : Fragment() {
    private var list: ArrayList<Catalog> = ArrayList()
    //原装适配器写法
    private lateinit var sortLeftAdapter: SortLeftAdapter
    private lateinit var sortRightAdapter: SortContentAdapter
    //quick适配器
    private lateinit var quickLeftAdapter: QuickLeftAdapter
    private lateinit var quickRightAdapter: QuickRightAdapter
    //quick用的布局管理器
    private lateinit var leftLayoutManager: LinearLayoutManager
    private lateinit var rightLayoutManager: LinearLayoutManager
    //原装布局管理器
    private lateinit var gridLayoutManager: GridLayoutManager
    //数据实体
    var leftData: MutableList<LeftBean> = mutableListOf()
    var rightData: MutableList<RightBean> = mutableListOf()
    private var firstposition: Int = 0
    private var SCROLL_STATE = 0

    var rightClick: Boolean = false
    lateinit var handler: Handler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

         return inflater.inflate(R.layout.fragment_sort, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        //一次只能滑动一页，不能快速滑动
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(rvContent)
        ivSearch.setOnClickListener {
            run {
                val intent = Intent()
                intent.setClass(this@SortFragment.context!!, SearchActivity::class.java)
                startActivity(intent)

            }
        }

        //滑动时，左侧导航栏同步改变选中状态
        rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                //使用原始适配器写法
                /*           super.onScrollStateChanged(recyclerView, newState)
                           val manager = recyclerView.layoutManager as GridLayoutManager
                           // 当不滚动时
                           if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                               //获取最后一个完全显示的ItemPosition
                               firstposition = manager.findFirstVisibleItemPosition()
                               //滑动到特殊的位置会报错 闪退     java.lang.ArrayIndexOutOfBoundsException: length=17; index=-1
                               if (firstposition == -1) {
                                   Log.e("滑到了奇怪的位置", firstposition.toString())
                               } else {
                                   tvCatalog.isEnabled = false
                                   SCROLL_STATE = firstposition
                                   sortLeftAdapter.notifyDataSetChanged()
                              }
                               //设置样式
                               if ( SCROLL_STATE == firstposition )
                               {
                                   tvCatalog.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                   val mSize: Float =context!!.resources.getDimension(R.dimen.mmm_font_s5)
                                   tvCatalog.setTextSize(mSize)
                                   tvCatalog.isEnabled = false
                               }
                               else {//未选中
                                   tvCatalog.setTypeface(Typeface.DEFAULT)
                                   val mSizeNor: Float =context!!.resources.getDimension(R.dimen.mmm_font_s4)
                                   tvCatalog.setTextSize(mSizeNor)
                                   tvCatalog.isEnabled = true
                               }
                           }*/

                //使用quickBase适配器写法
                if (rightClick == false && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val now: Int
                    val first = rightLayoutManager.findFirstVisibleItemPosition()
                    if (rightData.get(first).isTitle) {
                        now = rightData.get(first).id
                    } else {
                        if (rightData.get(first).id + 1 > leftData.get(leftData.size - 1).id) {
                            now = rightData.get(first).id
                        } else {
                            now = rightData.get(first).id + 1
                        }
                    }
                    //滑动右侧列表时 ：
                    RecycUtil.moveToPositAndCenter(now, leftLayoutManager, rvLeft, handler)
                    select(now)
                } else if (rightClick == true && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    rightClick = false
                } else if (rightClick == true && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    rightClick = false
                }
            }

        })
    }

    fun initData() {


        handler = Handler()

        var i = 0
        var j: Int
        while (i < mCatalogText.size - 1) {
            leftData.add(LeftBean(i, mCatalogText[i]))
            j = 0
            rightData.add(RightBean(i, mContentHeadImg[i], true))
            while (j < mContentHeadImg.size - 1) {
                rightData.add(RightBean(i, mContentHeadImg[i], false))
                j++
            }
            i++

        }
    }

    fun initView() {
        leftLayoutManager = LinearLayoutManager(context)
        leftLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rightLayoutManager = GridLayoutManager(context, 1)
        rightLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rvLeft.layoutManager = leftLayoutManager
        rvContent.layoutManager = rightLayoutManager

        val leftItemD = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        val rightItemD = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        rvLeft.addItemDecoration(leftItemD)
        rvContent.addItemDecoration(rightItemD)

        quickLeftAdapter = QuickLeftAdapter(R.layout.item_sort_left, leftData)
        quickRightAdapter = QuickRightAdapter(R.layout.item_sort_content, rightData)

//        rvLeft.adapter = sortLeftAdapter
//        rvContent.adapter = sortRightAdapter

        quickLeftAdapter.setOnItemChildClickListener { _, _, position ->
            rightClick = true
            select(position)

            var rightI = 0
            while (rightI < rightData.size) {
                if (rightData.get(rightI).id == leftData.get(position).id) {
                    break;
                }
                rightI++
            }

            RecycUtil.moveToPositAndTop(rightI, rightLayoutManager, rvContent, handler)

        }
    }


    fun getList(): ArrayList<Catalog> {

        for (i in 0 until mContentHeadImg.size) {
            list.add(
                Catalog(
                    mContentHeadImg[i], mAllItemImg1[i], mAllName1[i], mAllItemImg2[i], mAllName2[i], mAllItemImg3[i], mAllName3[i],
                    mAllItemImg4[i], mAllName4[i], mAllItemImg5[i], mAllName5[i], mAllItemImg6[i], mAllName6[i], mAllItemImg7[i], mAllName7[i],
                    mAllItemImg8[i], mAllName8[i], mAllItemImg9[i], mAllName9[i], mAllItemImg10[i], mAllName10[i], mAllItemImg11[i], mAllName11[i],
                    mAllItemImg12[i], mAllName12[i], mAllItemImg13[i], mAllName13[i], mAllItemImg14[i], mAllName14[i], mAllItemImg15[i], mAllName15[i]
                )
            )
        }
        return list
    }

    fun select(position: Int) {
        val i = quickLeftAdapter.index
        quickLeftAdapter.index = position
        if (i >= 0) {
            quickLeftAdapter.notifyItemChanged(i)
        }
        quickLeftAdapter.notifyItemChanged(position)
    }

}
