package com.outsideweather.cn.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.outsideweather.cn.util.StatusBarUtil;


/**
 * email：
 * description：初始化Fragement
 */
public class BaseFragement extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StatusBarUtil.transparencyBar2(getActivity());
        StatusBarUtil.setLightStatusBar(getActivity(), true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
