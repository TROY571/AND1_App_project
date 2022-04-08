package com.outsideweather.cn.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.outsideweather.cn.Model.CityModel;
import com.outsideweather.cn.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期：2022/4/6
 * email：
 * description：
 */
public class MyCityAdapter extends BaseAdapter {

    private Context context;
    private List<CityModel> cityModelArrayList = new ArrayList<>();


    public MyCityAdapter(Context context, List<CityModel> cityModelArrayList) {
        this.context = context;
        this.cityModelArrayList = cityModelArrayList;

    }

    @Override
    public int getCount() {
        return cityModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city, parent, false);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
         holder.tvName.setText(cityModelArrayList.get(i).getCityName());
        return convertView;
    }

    private static class ViewHolder {
        private TextView tvName;
    }


}


