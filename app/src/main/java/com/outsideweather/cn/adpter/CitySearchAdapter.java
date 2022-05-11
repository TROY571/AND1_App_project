package com.outsideweather.cn.adpter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qweather.sdk.bean.geo.GeoBean;
import com.outsideweather.cn.R;

import java.util.List;


public class CitySearchAdapter extends BaseQuickAdapter<GeoBean.LocationBean, BaseViewHolder> {
    public CitySearchAdapter(@Nullable List<GeoBean.LocationBean> data) {
        super(R.layout.item_search_city, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GeoBean.LocationBean item) {
        //set city name
        helper.setText(R.id.tv_city_name, item.getName());
        helper.addOnClickListener(R.id.tv_city_name);

    }
}
