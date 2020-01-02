package me.vast.common.framework.delegate;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.trello.rxlifecycle3.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.subjects.BehaviorSubject;
import me.vast.common.eventbus.IMessageEvent;

/**
 * author : zhengz
 * time   : 2018/6/29
 * desc   :
 */
public class ActivityDelegate {

    public final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    /**
     * EventBus 全局事件
     */
    private IMessageEvent mGlobalEvent;

    /**
     * EventBus 业务事件
     */
    private IMessageEvent mMessageEvent;

    /**
     * MVP绑定回调
     */
    private BindMVPCallback mBMCallback;

    /**
     * 内容绑定回调
     */
    private BindContentCallback mCMCallback;

    public ActivityDelegate(@NonNull BindMVPCallback bmCallback, @NonNull BindContentCallback bcCallback) {
        this.mBMCallback = bmCallback;
        this.mCMCallback = bcCallback;
    }

    /**
     *
     */
    public void onCreate(Bundle bundle) {
        lifecycleSubject.onNext(ActivityEvent.CREATE);

        mBMCallback.setPresenter(mBMCallback.createPresenter());
        if (null != mBMCallback.getPresenter()) {
            mBMCallback.getPresenter().attachView(mBMCallback.getMVPView());
        }
        registerEventHandler();
    }

    /**
     *
     */
    public void onStart() {
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    /**
     *
     */
    public void onResume() {
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    /**
     *
     */
    public void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
    }

    /**
     *
     */
    public void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
    }

    /**
     *
     */
    public void onDestroy() {
        unregisterEventHandler();
        if (null != mBMCallback.getPresenter()) {
            mBMCallback.getPresenter().detachView();
        }
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
    }

    /**
     * {@link Activity#finish()}
     */
    public void finish() {
        unregisterEventHandler();
    }

    /**
     * 注册EventBus事件
     */
    private void registerEventHandler() {
        // 防止多次注册
        unregisterEventHandler();

        mMessageEvent = mCMCallback.onMessageEvent();
        if (mMessageEvent != null) {
            EventBus.getDefault().register(mMessageEvent);
        }
        mGlobalEvent = mCMCallback.onGlobalEvent();
        if (mGlobalEvent != null) {
            EventBus.getDefault().register(mGlobalEvent);
        }
    }

    /**
     * 移除EventBus事件
     */
    private void unregisterEventHandler() {
        if (mMessageEvent != null) {
            EventBus.getDefault().unregister(mMessageEvent);
        }
        if (mGlobalEvent != null) {
            EventBus.getDefault().unregister(mGlobalEvent);
        }
    }

}