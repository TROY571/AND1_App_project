package com.outsideweather.cn.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.Fragment;

/**
 * email：
 * description：进度弹框
 */
public class ShowTosetDialog {

    private Activity mActivity;
    private Fragment mFragment;

    public ShowTosetDialog(Activity activity, Fragment fragment) {
        mActivity = activity;
        mFragment = fragment;
    }

    public Context getContext() {
        if (mActivity != null) return mActivity;
        return mFragment.getContext();
    }

    public Activity getActivity() {
        if (mActivity != null) return mActivity;
        return mFragment.getActivity();
    }


}
