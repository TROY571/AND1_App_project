package com.outsideweather.cn.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.outsideweather.cn.R;
import com.outsideweather.cn.base.BaseActivity;
import com.outsideweather.cn.Bean.NoteBean;
import com.outsideweather.cn.dao.NoteDao;
import com.outsideweather.cn.db.DBManger;
import com.outsideweather.cn.manger.SQLDBManger;
import com.ruffian.library.widget.RTextView;


/**
 * email：
 * description：记事本详情
 */
public class NoteDetailActivity extends BaseActivity {
    private RelativeLayout rlTitle;
    private ImageView ivBack;
    private TextView ivTitle;
    private RTextView tvSub;
    private TextView etTitle;
    private TextView tvTime;
    private TextView etContent;


    public static void statrNoteDetailActivity(Context context, int id) {
        Intent intent = new Intent(context,NoteDetailActivity.class);
        intent.putExtra("uid", String.valueOf(id));
        context.startActivity(intent);
    }

    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        uid = getIntent().getStringExtra("uid");
    //  NoteBean noteBean = SQLDBManger.getNote(uid);
        NoteDao noteDao = DBManger.getInstance(this).noteDao();
        NoteBean noteBean= noteDao.noteQueryByUid(Integer.valueOf(uid));
        initView(noteBean);

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {

           // NoteBean noteBean = SQLDBManger.getNote(uid);
            NoteDao noteDao = DBManger.getInstance(this).noteDao();
            NoteBean noteBean= noteDao.noteQueryByUid(Integer.valueOf(uid));
            etTitle.setText(noteBean.getNoteName());
            tvTime.setText(noteBean.getTime());
            etContent.setText(noteBean.getNoteContent());
        } catch (Exception e) {

        }

    }

    private void initView(NoteBean noteBean) {
        rlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivTitle = (TextView) findViewById(R.id.iv_title);
        tvSub = (RTextView) findViewById(R.id.tv_sub);
        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               EditNoteActivity.statrEditNoteActivity(NoteDetailActivity.this, Integer.valueOf(noteBean.getUid()));
            }
        });
        etTitle = (TextView) findViewById(R.id.et_title);
        tvTime = (TextView) findViewById(R.id.tv_time);
        etContent = (TextView) findViewById(R.id.et_content);
        etTitle.setText(noteBean.getNoteName());
        tvTime.setText(noteBean.getTime());
        etContent.setText(noteBean.getNoteContent());
    }
}
