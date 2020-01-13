package com.mmm.mypupu.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.RecommendationAdapter
import com.mmm.mypupu.ui.bean.Goods
import kotlinx.android.synthetic.main.fragment_tab_recommend.*

class TabRecommendFragment : Fragment() {
    var LOAD_COUNT = 0
    private var list: ArrayList<Goods> = ArrayList()
    private lateinit var recommendAdapter: RecommendationAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mLoadingDialog: MaterialDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_recommend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val randNum = (1..10).random()
        list = Goods.newGoodsList(randNum)
        recommendAdapter = RecommendationAdapter(list, context!!, LOAD_COUNT)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvRecommend.layoutManager = linearLayoutManager
        rvRecommend.adapter = recommendAdapter

        srlRecommend.setOnRefreshListener {
            list.clear()
            list.addAll(Goods.newGoodsList(3))
            recommendAdapter.notifyDataSetChanged()
            LOAD_COUNT = 0
            srlRecommend.postDelayed({
                srlRecommend.isRefreshing = false
            }, 50)

        }

        rvRecommend.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //1代表底部,返回true表示没到底部,还可以滑
                val isBottom = rvRecommend.canScrollVertically(1)
                if (isBottom == false && LOAD_COUNT < 3) {
                    list.addAll(Goods.newGoodsList(3))
                    LOAD_COUNT++
                    try {
                        rvRecommend.post {
                            showLoadingDialog(context!!)
                        }
                        rvRecommend.postDelayed({
                            hideLoadingDialog()
                        }, 1000)

                    } catch (e: Exception) {
                        Log.e("异常", e.toString())
                    } finally {
                        hideLoadingDialog()
                    }

                    recommendAdapter.notifyDataSetChanged()
                } else if (isBottom == false && LOAD_COUNT == 3) {
                    //  myUtil.talk(context!! , "到底了哦~" + "共有"+ list.size + "条数据" )
                    recommendAdapter.notifyDataSetChanged()
                    LOAD_COUNT++
                }
            }
        })
    }

    fun showLoadingDialog(context: Context) {
        mLoadingDialog = MaterialDialog.Builder(context)
            .widgetColorRes(R.color.colorPrimary)
            .progress(true, 0)
            .cancelable(false)
            .build()
        mLoadingDialog.setContent("正在加载...")
        mLoadingDialog.show()
    }

    fun hideLoadingDialog() {
        if (this::mLoadingDialog.isInitialized && mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        } else return
    }
}
