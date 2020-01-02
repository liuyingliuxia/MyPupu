package me.vast.common.widgets.adapter;

import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import me.vast.common.util.CommonUtil;

/**
 * author : zhengz
 * time   : 2018/1/10
 * desc   :
 */

public abstract class SimpleViewHolder<T> extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    protected T data;
    private IItemClickLitener itemClickLitener;
    private IItemLongClickLitener itemLongClickLitener;


    public SimpleViewHolder(View view) {
        super(view);
        mViews = new SparseArray<>();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!CommonUtil.allowClick()) {
                return;
            }
            if (null != itemClickLitener) {
                itemClickLitener.onItemClick(view, data);
            }
        }
    };

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if (null != itemLongClickLitener) {
                itemLongClickLitener.onItemLongClick(view, data);
            }
            return true;
        }
    };

    void bindListener(T data, final IItemClickLitener itemClickLitener, IItemLongClickLitener itemLongClickLitener) {
        this.data = data;
        this.itemClickLitener = itemClickLitener;
        this.itemLongClickLitener = itemLongClickLitener;
        if (null != itemClickLitener) {
            itemView.setOnClickListener(onClickListener);
        }
        if (null != itemLongClickLitener) {
            itemView.setOnLongClickListener(onLongClickListener);
        }
    }


    public <VT extends View> VT getView(int id) {
        View v = mViews.get(id);
        if (v == null) {
            v = itemView.findViewById(id);
            mViews.put(id, v);
        }
        return (VT) v;
    }


    public abstract void bindData(int position, T data);


    /////////  暂时用处不大 ///////////////////////////
//    public void addOnClickListener(View view) {
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onViewClick(v, SimpleViewHolder.this, data);
//            }
//        });
//    }
//
//    public void removeOnClickListener(View view) {
//        view.setOnClickListener(null);
//    }
//
//    public void onViewClick(View view, SimpleViewHolder holder, T data) {
//
//    }
}
