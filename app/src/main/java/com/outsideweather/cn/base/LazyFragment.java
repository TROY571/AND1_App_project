package com.outsideweather.cn.base;

/**
 * email：
 * description：
 */
public abstract class LazyFragment extends BaseFragement {
    //是否可见
    protected boolean isVisible;
    //标志位，标志Fragment已经初始化完成
    public boolean isPrepared = false;

    @Override
    public void onResume() {
        super.onResume();
        if (!isPrepared) {
            loadData();
            isPrepared = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isPrepared = false;
    }

    protected abstract void loadData();
}
