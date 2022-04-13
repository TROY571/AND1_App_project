package com.outsideweather.cn.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.outsideweather.cn.R;
import com.outsideweather.cn.base.BaseActivity;
import com.outsideweather.cn.Bean.NoteBean;
import com.outsideweather.cn.dao.NoteDao;
import com.outsideweather.cn.db.DBManger;
import com.outsideweather.cn.manger.SQLDBManger;
import com.outsideweather.cn.util.BaseDateUtils;
import com.ruffian.library.widget.RTextView;


/**
 * email：
 * description 新增记事本
 */
public class AddNoteActivity extends BaseActivity {
    private LinearLayout settingItemAutoPlay;
    public static void startAddNoteActivity(Context context) {
        Intent intent = new Intent(context, AddNoteActivity.class);
        context.startActivity(intent);
    }

    private RelativeLayout rlTitle;
    private ImageView ivBack;
    private TextView ivTitle;
    private RTextView tvSub;
    private EditText etTitle;
    private TextView tvTime;
    private EditText etContent;
    private String picUr="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initView();
    }

    private void initView() {
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
        etTitle = (EditText) findViewById(R.id.et_title);
        String times = BaseDateUtils.getNowDateTime();
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvTime.setText(times);
        etContent = (EditText) findViewById(R.id.et_content);
        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etTitle.getText().toString())) {
                    Toast.makeText(AddNoteActivity.this, getString(R.string.note_add_title_input), Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(etContent.getText().toString())) {
                    Toast.makeText(AddNoteActivity.this,  getString(R.string.note_add_content_input), Toast.LENGTH_LONG).show();
                    return;
                }
                String times = BaseDateUtils.getNowDateTime();
                NoteDao noteDao= DBManger.getInstance(AddNoteActivity.this).noteDao();
                noteDao.noteInsert(new NoteBean(etTitle.getText().toString(),etContent.getText().toString(),times));
                finish();
               // SQLDBManger.addNote(new NoteBean(etTitle.getText().toString(), etContent.getText().toString(), times));
                finish();
            }
        });


    }

}
