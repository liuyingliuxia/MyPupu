package com.mmm.mypupu.util

import android.view.View
import android.widget.RadioButton
import com.mmm.mypupu.R

object ItemState {

    //三种点击状态的切换
    fun changedItemState3 (rb : RadioButton, v:View){
        var rbClickTiems = 0
        when (rbClickTiems){
            0 -> {
                rbClickTiems ++
                rb.setTextColor(v.resources.getColor(R.color.color23))
                val drawable = v.resources.getDrawable(R.drawable.icon_indicate_arrow_top_selected)
                drawable.setBounds(0,0,drawable.minimumWidth,drawable.minimumHeight)
                rb.setCompoundDrawables(null,null,drawable,null)
            }
            1 -> {
                rbClickTiems ++
                rb.setTextColor(v.resources.getColor(R.color.color23))
                val drawable = v.resources.getDrawable(R.drawable.icon_indicate_arrow_bottom_selected)
                drawable.setBounds(0,0,drawable.minimumWidth,drawable.minimumHeight)
                rb.setCompoundDrawables(null,null,drawable,null)
            }
            2 -> {
                rbClickTiems = 0
                rb.setTextColor(v.resources.getColor(R.color.color5))
                val drawable = v.resources.getDrawable(R.drawable.icon_indicate_arrow_no_selected)
                drawable.setBounds(0,0,drawable.minimumWidth,drawable.minimumHeight)
                rb.setCompoundDrawables(null,null,drawable,null)
            }
        }
    }
}