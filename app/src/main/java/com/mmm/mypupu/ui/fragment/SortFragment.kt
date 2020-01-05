package com.mmm.mypupu.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.example.lilingzhi.tworecyc.util.RecycUtil
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.QuickLeftAdapter
import com.mmm.mypupu.ui.bean.LeftBean
import com.mmm.mypupu.ui.bean.TwoBean
import com.mmm.mypupu.ui.data.*
import com.mmm.mypupu.ui.search.SearchActivity
import com.mmm.mypupu.ui.widgets.ChildRecyclerView
import com.mmm.mytestutil.rvInRecycler.SortVP2Adapter
import kotlinx.android.synthetic.main.fragment_sort.*

class SortFragment : Fragment() {
    //quick适配器
    private lateinit var quickLeftAdapter: QuickLeftAdapter
    private lateinit var vp2RightAdapter : SortVP2Adapter
    //quick用的布局管理器
    private lateinit var leftLayoutManager: LinearLayoutManager
  //  private lateinit var rightLayoutManager: LinearLayoutManager
    //数据实体
    var leftData: MutableList<LeftBean> = mutableListOf()
    var rightData :MutableList<TwoBean> = mutableListOf()

    var rightClick: Boolean = false
    lateinit var handler: Handler


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_sort, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        select(0) // 刚进入页面默认选中第一个
        //一次只能滑动一页，不能快速滑动
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(vp2Right)

        ivSearch.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@SortFragment.context!!, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    fun initData() {
        handler = Handler()
        val leftBean = LeftBean(0, "", 1)

        val rvView  = ChildRecyclerView(context!!)
        for (i in 0..mCatalogText.size) {
            //根据itemType不同添加不同的数据
            if (i < mCatalogText.size) {
                leftBean.type = LeftBean.TYPE_TEXT
                leftData.add(LeftBean(i, mCatalogText[i], 1))
                //传入 rv itme的数量

            } else if (i == mCatalogText.size) {
                leftBean.type = LeftBean.TYPE_EMPTY
                leftData.add(LeftBean((mCatalogText.size), "", 2))
            }
        }
        Log.e("left", leftData.toString())
        //绑定右侧 布局 -> 子recycler
        for (i in  0.. 16) {
            rightData.add(TwoBean( i ,rvView  ))
        }
        vp2RightAdapter = SortVP2Adapter(context!! , rightData )


    }

    fun initView() {
        //解决RecyclerView 内嵌套 RecyclerView 导致显示不全 的问题
        val rightLayoutManager: LinearLayoutManager = object : LinearLayoutManager(context) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }


        leftLayoutManager = LinearLayoutManager(context)
        leftLayoutManager.orientation = LinearLayoutManager.VERTICAL

        //rightLayoutManager = LinearLayoutManager(context)
        rightLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rvLeft.layoutManager = leftLayoutManager
        vp2Right.layoutManager = rightLayoutManager
        //vp2 不需要设置布局管理器 其子布局必须match
        quickLeftAdapter = QuickLeftAdapter(leftData)

        rvLeft.adapter = quickLeftAdapter
        vp2Right.adapter = vp2RightAdapter

        quickLeftAdapter.setOnItemClickListener { _, _, position ->
            rightClick = true
            select(position)
            //切换到指定的item
            rightLayoutManager.scrollToPositionWithOffset(position, 0)
            rightLayoutManager.stackFromEnd = false

        }

        vp2Right.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (rightClick == false && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val now :Int
                    val first = rightLayoutManager.findFirstVisibleItemPosition()
                    now = rightData.get(first).id
                    RecycUtil.moveToPositAndCenter(now , leftLayoutManager , rvLeft , handler )
                    select(now)
                }else if(rightClick==true&& newState == RecyclerView.SCROLL_STATE_IDLE){
                    rightClick=false
                }else if(rightClick==true&&newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    rightClick=false
                }
            }
        })


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