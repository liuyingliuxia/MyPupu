package com.mmm.mypupu.ui.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.RightBean2
import com.mmm.mypupu.ui.bean.RightItemBean
import kotlinx.android.synthetic.main.item_sort_right2.view.*


class SortVP2Adapter (context: Context?, data: List<RightBean2>, vp2: ViewPager2) : RecyclerView.Adapter<SortVP2Adapter.ViewHolder>() {
    private val mContext = context
    private val mData: List<RightBean2>
    private val mInflater: LayoutInflater
    private val vp2: ViewPager2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_sort_right2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val h = holder.itemView
        val rightBean = mData[position]
        Glide.with(mContext!!).load(rightBean.ivHead).placeholder(R.drawable.place_holder_banner_home).error(R.drawable.icon_coupon_all_not_usable).into(h.ivHead)
       // h.rvRightCatalog.adapter = SortRightCatalogAdapter(mContext ,)
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
}