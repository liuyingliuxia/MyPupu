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
    private var list :ArrayList<Catalog> = ArrayList ()
    private lateinit var sortLeftAdapter: SortLeftAdapter
    private lateinit var sortContentAdapter: SortContentAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    var leftData :MutableList<LeftBean> = mutableListOf()
    var rightData :MutableList<RightBean> = mutableListOf()
    private var firstposition: Int = 0
    private var SCROLL_STATE = 0
    var rightClick :Boolean = false

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
                    }
                }


       /*    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
               super.onScrolled(recyclerView, dx, dy)
               val manager = recyclerView.layoutManager as GridLayoutManager
               val topPosition  = manager.findFirstVisibleItemPosition()

           }*/

            })
    }

/*    fun initView () {
        val leftLayoutManager  = LinearLayoutManager(context)
          leftLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val rightLayoutManager = GridLayoutManager(context, 1)
          rightLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rvLeft.layoutManager = leftLayoutManager
        rvContent.layoutManager = rightLayoutManager

        val leftItemD = DividerItemDecoration ( context , DividerItemDecoration.VERTICAL)
        val rightItemD = DividerItemDecoration ( context , DividerItemDecoration.VERTICAL)

        rvLeft.addItemDecoration(leftItemD)
        rvContent.addItemDecoration(rightItemD)

        val leftAdapter = QuickLeftAdapter(R.layout.item_sort_left , leftData)
        val rightAdapter = QuickRightAdapter(R.layout.item_sort_content , rightData)

        rvLeft.adapter = leftAdapter
        rvContent.adapter = rightAdapter

        leftAdapter.setOnItemChildClickListener { adapter, view, position ->
            rightClick = true
            select( position )

            var twoI=0
            while (twoI<rightData.size){
                if(rightData.get(twoI).id==leftData.get(position).id){
                    break;
                }
                twoI++
            }

            RecycUtil.moveToPositAndTop(twoI,twoLayoutM,rvContent,handler)

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

/*    fun select(position:Int){
        var i=.index
        QuickLeftAdapter.index=position
        if(i>=0){
            sortLeftAdapter.notifyItemChanged(i)
        }
        sortLeftAdapter.notifyItemChanged(position)
    }*/

}
