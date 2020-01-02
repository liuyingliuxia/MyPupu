package me.vast.common.widgets.adapter;

import android.view.View;

/**
 * author : zhengz
 * time   : 2018/1/10
 * desc   :
 */

public interface IItemClickLitener<T> {
    void onItemClick(View view, T data);
}
