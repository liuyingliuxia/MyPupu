package me.vast.common.framework.delegate;

import android.support.annotation.NonNull;

import me.vast.common.eventbus.IMessageEvent;

/**
 * author : zhengz
 * time   : 2018/6/29
 * desc   : 内容绑定
 */
public interface BindContentCallback {
    /**
     * 布局资源id
     *
     * @return
     */
    @NonNull
    int getContentLayoutResId();

    /**
     * EventBus事件回调
     *
     * @return
     */
    IMessageEvent onMessageEvent();

    /**
     * EventBus全局事件回调
     *
     * @return
     */
    IMessageEvent onGlobalEvent();


}
