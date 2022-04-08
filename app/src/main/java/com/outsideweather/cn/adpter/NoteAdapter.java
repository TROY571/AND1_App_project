package com.outsideweather.cn.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.outsideweather.cn.R;
import com.outsideweather.cn.Model.NoteModel;

import java.util.ArrayList;
import java.util.List;

/**
 * email：
 * description：笔记适配器
 */
public class NoteAdapter extends BaseAdapter {
    private Context context;
    private List<NoteModel> noteModelList=new ArrayList<>();

    public NoteAdapter(Context context, List<NoteModel> noteModelList){
        this.context=context;
        this.noteModelList=noteModelList;
    }



    @Override
    public int getCount() {
        return noteModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return noteModelList.get(i);
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
        holder.title.setText(noteModelList.get(i).getNoteName());
        holder.content.setText(noteModelList.get(i).getTime());
        return convertView;
    }

    private static class ViewHolder {
        private TextView title;
        private TextView content;

    }
}
