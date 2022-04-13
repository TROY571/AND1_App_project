package com.outsideweather.cn.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.outsideweather.cn.Bean.CityBean;
import com.outsideweather.cn.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期：2022/4/6
 * email：
 * description：
 */
public class CityAdapter extends BaseAdapter {

    private Context context;
    private List<CityBean> cityBeanArrayList = new ArrayList<>();


    public CityAdapter(Context context, List<CityBean> cityBeanArrayList) {
        this.context = context;
        this.cityBeanArrayList = cityBeanArrayList;

    }

    @Override
    public int getCount() {
        return cityBeanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityBeanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_weather_city, parent, false);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
         holder.tvName.setText(cityBeanArrayList.get(i).getCityName());
        return convertView;
    }

    private static class ViewHolder {
        private TextView tvName;
    }


}


