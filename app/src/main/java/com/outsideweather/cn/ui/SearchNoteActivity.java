package com.outsideweather.cn.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.outsideweather.cn.Model.NoteModel;
import com.outsideweather.cn.R;
import com.outsideweather.cn.adpter.NoteAdapter;
import com.outsideweather.cn.base.BaseActivity;
import com.outsideweather.cn.manger.SQLDBManger;
import com.ruffian.library.widget.RRelativeLayout;
import com.ruffian.library.widget.RTextView;


import java.util.ArrayList;
import java.util.List;

/**
 * description：
 */
public class SearchNoteActivity extends BaseActivity {
    private RelativeLayout rlTitle;
    private ImageView ivBack;
    private RRelativeLayout lySearch1;
    private RTextView tvSub;
    private ListView listView;
    private EditText etSearch;
    private NoteAdapter noteAdapter;
    private List<NoteModel> noteModelList = new ArrayList<>();
    public static void startSearchNoteActivity(Context context) {
        Intent intent = new Intent(context, SearchNoteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    private void initView() {
        rlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        lySearch1 = (RRelativeLayout) findViewById(R.id.lySearch1);
        tvSub = (RTextView) findViewById(R.id.tv_sub);
        listView = (ListView) findViewById(R.id.listView);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etSearch = (EditText) findViewById(R.id.etSearch);
        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etSearch.getText().toString())){
                    Toast.makeText(SearchNoteActivity.this,getString(R.string.note_search),Toast.LENGTH_SHORT).show();
                    return;
                }

                noteModelList.clear();
                noteModelList =  SQLDBManger.noteQueryByNoteName(etSearch.getText().toString());;
                noteAdapter = new NoteAdapter(SearchNoteActivity.this, noteModelList);
                listView.setAdapter(noteAdapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("TAG", " noteModelList.get(i).getNoteName()" + noteModelList.get(i).getNoteName());
                NoteDetailActivity.statrNoteDetailActivity(SearchNoteActivity.this, Integer.valueOf(noteModelList.get(i).getUid()));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
