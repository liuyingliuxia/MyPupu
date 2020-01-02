package com.mmm.mypupu.ui.adapter
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
//import com.bumptech.glide.Glide
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.RightBean
import com.mmm.mypupu.ui.bean.RightItemBean
import com.mmm.mypupu.ui.data.*
import kotlinx.android.synthetic.main.item_sort_right.view.*

//分类页面 右侧内容，使用vp2, RightBean 包括 头部图片 和 recycler

class SortVP2Adapter (context: Context?, data: List<RightBean>, vp2: ViewPager2) : RecyclerView.Adapter<SortVP2Adapter.ViewHolder>() {
    private val mContext = context
    private val mData: List<RightBean>
    private val mInflater: LayoutInflater
    private val vp2: ViewPager2



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_sort_right, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val h = holder.itemView
        val rightBean = mData[position]
          //配置 嵌套的适配器
        initRecycler(rightBean,position )

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        mInflater = LayoutInflater.from(context)
        mData = data
        this.vp2 = vp2
    }

    private lateinit var quickRightAdapter: SortRightCatalogAdapter
    private lateinit var gridLM :GridLayoutManager

    fun initRecycler(rightBean: RightBean , position: Int){
        //初始化适配器
        val bean1 = RightItemBean(position , mContentHeadImg[position], mCatalog1[position], mCatalogName1[position] )
        val bean2 = RightItemBean(position , mContentHeadImg[position], mCatalog2[position], mCatalogName2[position] )
        val bean3 = RightItemBean(position , mContentHeadImg[position],mCatalog3[position], mCatalogName3[position] )
        val bean4 = RightItemBean(position , mContentHeadImg[position], mCatalog4[position], mCatalogName4[position] )
        val bean5 = RightItemBean(position , mContentHeadImg[position], mCatalog5[position], mCatalogName5[position] )
        val bean6 = RightItemBean(position , mContentHeadImg[position],mCatalog6[position], mCatalogName6[position] )
        val itemList = arrayListOf(bean1,bean2,bean3 , bean4 , bean5 , bean6 )
        Log.e("子 rv 的bean " , itemList.toString())
        quickRightAdapter = SortRightCatalogAdapter(mContext!! , itemList)
        //设置布局管理器
        gridLM = GridLayoutManager(mContext , 3 )
        gridLM.orientation = GridLayoutManager.VERTICAL

        rightBean.rvRightCatalog.adapter = quickRightAdapter
        rightBean.rvRightCatalog.layoutManager = gridLM
    }

}