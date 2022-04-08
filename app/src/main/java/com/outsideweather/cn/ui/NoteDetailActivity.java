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
import com.outsideweather.cn.Model.NoteModel;
import com.outsideweather.cn.manger.SQLDBManger;
import com.ruffian.library.widget.RTextView;


/**
 * email：
 * description：noote pad detail
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
        NoteModel noteModel = SQLDBManger.getNote(uid);
        initView(noteModel);

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {

            NoteModel noteModel = SQLDBManger.getNote(uid);
            etTitle.setText(noteModel.getNoteName());
            tvTime.setText(noteModel.getTime());
            etContent.setText(noteModel.getNoteContent());
        } catch (Exception e) {

        }

    }

    private void initView(NoteModel noteModel) {
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
               EditNoteActivity.statrEditNoteActivity(NoteDetailActivity.this, Integer.valueOf(noteModel.getUid()));
            }
        });
        etTitle = (TextView) findViewById(R.id.et_title);
        tvTime = (TextView) findViewById(R.id.tv_time);
        etContent = (TextView) findViewById(R.id.et_content);
        etTitle.setText(noteModel.getNoteName());
        tvTime.setText(noteModel.getTime());
        etContent.setText(noteModel.getNoteContent());
    }
}
