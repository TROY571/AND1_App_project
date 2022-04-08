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
import com.outsideweather.cn.Model.NoteModel;
import com.outsideweather.cn.manger.SQLDBManger;
import com.ruffian.library.widget.RTextView;


/**
 * email：
 * description：edit note pad
 */
public class EditNoteActivity extends BaseActivity {
    private LinearLayout lyImage;
    private ImageView ivAdd3;

    public static void statrEditNoteActivity(Context context, int id) {
        Intent intent = new Intent(context, EditNoteActivity.class);
        intent.putExtra("uid", String.valueOf(id));
        context.startActivity(intent);
    }

    private RelativeLayout rlTitle;
    private ImageView ivBack;
    private TextView ivTitle;
    private RTextView tvSub;
    private EditText etTitle;
    private TextView tvTime;
    private EditText etContent;

    private String uid;
    private NoteModel noteModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        uid = getIntent().getStringExtra("uid");
        noteModel = SQLDBManger.getNote(uid);
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
        etTitle.setText(noteModel.getNoteName());
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvTime.setText(noteModel.getTime());
        etContent = (EditText) findViewById(R.id.et_content);
        etContent.setText(noteModel.getNoteContent());

        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etTitle.getText().toString())) {
                    Toast.makeText(EditNoteActivity.this, getString(R.string.note_add_title_input), Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(etContent.getText().toString())) {
                    Toast.makeText(EditNoteActivity.this,  getString(R.string.note_add_content_input), Toast.LENGTH_LONG).show();
                    return;
                }

                noteModel.setNoteContent(etContent.getText().toString());
                noteModel.setNoteName(etTitle.getText().toString());
                SQLDBManger.updateNote(noteModel);
                finish();
            }
        });


    }



}
