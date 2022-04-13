package com.outsideweather.cn.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.outsideweather.cn.R;
import com.outsideweather.cn.Bean.NoteBean;

import java.util.ArrayList;
import java.util.List;

/**
 * email：
 * description：笔记适配器
 */
public class NoteAdapter extends BaseAdapter {
    private Context context;
    private List<NoteBean> noteBeanList =new ArrayList<>();

    public NoteAdapter(Context context, List<NoteBean> noteBeanList){
        this.context=context;
        this.noteBeanList = noteBeanList;
    }



    @Override
    public int getCount() {
        return noteBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return noteBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_note, null);
            holder = new ViewHolder();
            holder. title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(noteBeanList.get(i).getNoteName());
        holder.content.setText(noteBeanList.get(i).getTime());
        return convertView;
    }

    private static class ViewHolder {
        private TextView title;
        private TextView content;

    }
}
