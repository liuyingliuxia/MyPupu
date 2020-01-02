package me.vast.common.widgets.adapter;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * zhengz
 */
public abstract class SimpleAdapter<T> extends RecyclerView.Adapter<SimpleViewHolder> {
    private ArrayList<T> items = new ArrayList<>();
    private IItemClickLitener itemClickLitener;
    private IItemLongClickLitener itemLongClickLitener;

    public SimpleAdapter() {
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        T t = getItem(position);
        if (null != t) {
            return t.hashCode();
        }
        return -1;
    }

    public void add(T object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, T object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends T> collection) {
        if (collection != null) {
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(T... items) {
        addAll(Arrays.asList(items));
    }

    public void set(Collection<? extends T> collection) {
        items.clear();
        if (collection != null) {
            items.addAll(collection);
        }
        notifyDataSetChanged();
    }

    /**
     * 直接赋值队列
     * @param collection
     */
    public void setAssign(ArrayList<T> collection) {
        items = collection;
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public boolean remove(T object) {
        boolean isRemoved = items.remove(object);
        if (isRemoved) {
            notifyDataSetChanged();
        }
        return isRemoved;
    }

    public void remove(int index) {
        items.remove(index);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (position >= 0 && items.size() > position) {
            return items.get(position);
        }
        return null;
    }

    public T getLastItem() {
        if (items.size() > 0) {
            return items.get(items.size() - 1);
        }
        return null;
    }

    public ArrayList<T> getAll() {
        return items;
    }

    public int size() {
        return items.size();
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public final void onBindViewHolder(SimpleViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public final void onBindViewHolder(SimpleViewHolder holder, int position) {
        T data = getItem(position);
        holder.bindListener(data, itemClickLitener, itemLongClickLitener);
        holder.bindData(position, data);
    }

    public void setOnItemClickLitener(IItemClickLitener<T> litener) {
        this.itemClickLitener = litener;
    }

    public void setOnItemLongClickListener(IItemLongClickLitener<T> litener) {
        this.itemLongClickLitener = litener;
    }



}
