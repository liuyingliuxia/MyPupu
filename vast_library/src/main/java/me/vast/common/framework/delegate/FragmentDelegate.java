package me.vast.common.framework.delegate;

import android.content.Context;
import android.os.Bundle;

import android.view.View;

import androidx.annotation.NonNull;

import com.trello.rxlifecycle3.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.subjects.BehaviorSubject;
import me.vast.common.eventbus.IMessageEvent;

/**
 * author : zhengz
 * time   : 2018/6/29
 * desc   :
 */
public class FragmentDelegate {
    public final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    /**
     * EventBus 全局事件
     */
    private IMessageEvent mGlobalEvent;

    /**
     * EventBus 业务事件
     */
    private IMessageEvent mMessageEvent;


    private BindMVPCallback mMvpCallback;

    private BindContentCallback mContentCallback;

    public FragmentDelegate(@NonNull BindMVPCallback bmcallback, @NonNull BindContentCallback bcCallback) {
        this.mMvpCallback = bmcallback;
        this.mContentCallback = bcCallback;
    }

    /**
     *
     */
    public void onAttach(Context context) {
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    /**
     *
     */
    public void onCreate(Bundle savedState) {
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    /**
     *
     */
    public void onViewCreated(View view, Bundle savedState) {
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);

        mMvpCallback.setPresenter(mMvpCallback.createPresenter());
        if (null != mMvpCallback.getPresenter()) {
            mMvpCallback.getPresenter().attachView(mMvpCallback.getMVPView());
        }
        registerEventHandler();
    }

    /**
     *
     */
    public void onStart() {
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    /**
     *
     */
    public void onResume() {
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    /**
     *
     */
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
    }

    /**
     *
     */
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
    }

    /**
     *
     */
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        if (null != mMvpCallback.getPresenter()) {
            mMvpCallback.getPresenter().detachView();
        }
    }

    /**
     *
     */
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        unregisterEventHandler();
    }

    /**
     *
     */
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
    }

    /**
     * 注册EventBus事件
     */
    private void registerEventHandler() {
        // 防止多次注册
        unregisterEventHandler();

        mMessageEvent = mContentCallback.onMessageEvent();
        if (mMessageEvent != null) {
            EventBus.getDefault().register(mMessageEvent);
        }
        mGlobalEvent = mContentCallback.onGlobalEvent();
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
