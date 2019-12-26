package com.mmm.mypupu.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmm.mypupu.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmm.mypupu.ui.adapter.CrazyDiscountAdapter
import com.mmm.mypupu.ui.adapter.FlashSaleAdapter
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import kotlinx.android.synthetic.main.fragment_tab_crazy_discount.view.*
import kotlinx.android.synthetic.main.fragment_tab_flash_sale.view.*
import kotlinx.android.synthetic.main.fragment_tab_fruit.view.*
import kotlinx.android.synthetic.main.item_flash_sale_head.*
import java.util.*
import kotlin.collections.ArrayList

class TabFlashSaleFragment : Fragment(),View.OnClickListener{

    private var list :MutableList<Goods > = ArrayList ()
    val mStack = Stack <Fragment> ()
    private lateinit var flashSaleAdapter: FlashSaleAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val flashTimeFragment = FlashTimeFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_tab_flash_sale, container, false)

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        llTime9.setOnClickListener(this)
        llTime11.setOnClickListener(this)
        llTime14.setOnClickListener(this)
        llTime16.setOnClickListener(this)
    }
    private fun initFragment () {
        val manager = activity!!.supportFragmentManager.beginTransaction()
        manager.add (R.id.llFlashSale,flashTimeFragment)
        manager.add (R.id.llFlashSale,flashTimeFragment)
        manager.add (R.id.llFlashSale,flashTimeFragment)
        manager.add (R.id.llFlashSale,flashTimeFragment)
        manager.commit()
        mStack.add(flashTimeFragment)

    }

    private fun changeFragment (position: Int) {
        val manager = activity!!.supportFragmentManager.beginTransaction()
        for (fragment in mStack){
            manager.hide (fragment)
        }
        manager.show (mStack[position])
        manager.commit()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.llTime11 -> {
                changeFragment(0)
            }

            R.id.llTime14 -> {
                changeFragment(1)
            }
            R.id.llTime9 -> {
                changeFragment(0)
            }

            R.id.llTime16 -> {
                changeFragment(1)
            }
        }
    }

}
