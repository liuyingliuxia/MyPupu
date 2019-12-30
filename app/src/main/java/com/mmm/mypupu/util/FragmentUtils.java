package com.mmm.mypupu.util;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;

import static androidx.core.util.Preconditions.checkNotNull;

public class FragmentUtils {

    private final HashMap<String, Fragment> fragments = new HashMap<>();

    private final FragmentActivity activity;
    private final FragmentManager manager;
    private final int container;

    private String current;

    public FragmentUtils(FragmentActivity activity, @IdRes int container) {
        this.activity = activity;
        this.container = container;
        this.manager = activity.getSupportFragmentManager();
    }


    /**
     *  one add show
     * @param fragment
     */
    public void switchTo(Class<? extends Fragment> fragment) {
        String name = fragment.getName();
        if (null != current) {
            manager.beginTransaction().hide(fragments.get(current)).commit();
        }
        if (null == manager.findFragmentByTag(name)) {
            Fragment instance = Fragment.instantiate(activity, name);
            fragments.put(name, instance);
            manager.beginTransaction().add(container, instance, name).addToBackStack(name).commit();
        } else {
            manager.beginTransaction().show(fragments.get(name)).commit();
        }
        current = name;
    }



    @Nullable
    public Fragment getFragment(Class<? extends Fragment> fragment) {
        return fragments.get(fragment.getName());
    }

    /**
     *  two replace
     */
    public void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = manager
                .beginTransaction();
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.commit();
    }

    /**
     *
     * 单纯添加 用的比较少
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public void addFragmentToActivity (@NonNull Fragment f) {
        Fragment fragment = checkNotNull(f);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(container, fragment);
        transaction.commit();
    }
}
