package com.outsideweather.cn.fragement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.outsideweather.cn.R;
import com.outsideweather.cn.adpter.NoteAdapter;
import com.outsideweather.cn.base.BaseLazyFragment;
import com.outsideweather.cn.dao.NoteDao;
import com.outsideweather.cn.db.AppDataBaseDB;
import com.outsideweather.cn.dialog.BaseDialog;
import com.outsideweather.cn.Bean.NoteBean;
import com.outsideweather.cn.activity.AddNoteActivity;
import com.outsideweather.cn.activity.NoteDetailActivity;
import com.outsideweather.cn.activity.SearchNoteActivity;
import com.outsideweather.cn.util.StatusBarUtil;
import com.ruffian.library.widget.RRelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * email：
 * description：Note detail
 */
public class NoteFragement extends BaseLazyFragment {

    private View view;
    private RelativeLayout rlTitle;
    private TextView ivTitle;
    private RRelativeLayout lySearch1;
    private ListView listView;
    private FloatingActionButton ivAdd;
    private NoteAdapter noteAdapter;
    private List<NoteBean> noteBeanList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup root, Bundle state) {
        if (view == null) {
            view = inf.inflate(R.layout.note_list, root, false);
            StatusBarUtil.setLightStatusBar(getActivity(), true);
            initView();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            // 隐藏当前的fragment
        } else {
            // 显示当前的fragment
            StatusBarUtil.setLightStatusBar(getActivity(), true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        rlTitle = (RelativeLayout) view.findViewById(R.id.rl_title);
        ivTitle = (TextView) view.findViewById(R.id.iv_title);
        lySearch1 = (RRelativeLayout) view.findViewById(R.id.lySearch1);
        listView = (ListView) view.findViewById(R.id.listView);
        ivAdd = (FloatingActionButton)view. findViewById(R.id.ivAdd);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteActivity.startAddNoteActivity(getActivity());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("TAG", " noteModelList.get(i).getNoteName()" + noteBeanList.get(i).getNoteName());
                NoteDetailActivity.statrNoteDetailActivity(getActivity(), Integer.valueOf(noteBeanList.get(i).getUid()));
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new BaseDialog(getActivity(), getString(R.string.note_delete), getString(R.string.note_tips), getString(R.string.note_cancer), getString(R.string.note_sure), new BaseDialog.onSubClickBack() {
                    @Override
                    public void onClickBack(int status) {
                        if (status == 1) {
                            NoteDao noteDao = AppDataBaseDB.getInstance(getActivity()).noteDao();
                            noteDao.deleteNote(noteBeanList.get(i));
                            noteBeanList.remove(i);
                            noteAdapter.notifyDataSetChanged();
                        }
                    }


                }).show();
                return true;
            }
        });
        lySearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to SearchNoteActivity
                SearchNoteActivity.startSearchNoteActivity(getActivity());
            }
        });

    }

    public void initData(){
        noteBeanList.clear();
        NoteDao noteDao = AppDataBaseDB.getInstance(getActivity()).noteDao();
        noteBeanList = noteDao.getAllNotes();
        noteAdapter = new NoteAdapter(getActivity(), noteBeanList);
        listView.setAdapter(noteAdapter);
    }

}

