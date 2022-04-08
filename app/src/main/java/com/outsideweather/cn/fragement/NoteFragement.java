package com.outsideweather.cn.fragement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.outsideweather.cn.R;
import com.outsideweather.cn.adpter.NoteAdapter;
import com.outsideweather.cn.base.LazyFragment;
import com.outsideweather.cn.dialog.BaseDialog;
import com.outsideweather.cn.Model.NoteModel;
import com.outsideweather.cn.manger.SQLDBManger;
import com.outsideweather.cn.ui.AddNoteActivity;
import com.outsideweather.cn.ui.NoteDetailActivity;
import com.outsideweather.cn.ui.SearchNoteActivity;
import com.outsideweather.cn.util.StatusBarUtil;
import com.ruffian.library.widget.RRelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * description：note pad
 */
public class NoteFragement extends LazyFragment {

    private View view;
    private RelativeLayout rlTitle;
    private TextView ivTitle;
    private RRelativeLayout lySearch1;
    private ListView listView;
    private FloatingActionButton ivAdd;
    private NoteAdapter noteAdapter;
    private List<NoteModel> noteModelList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup root, Bundle state) {
        if (view == null) {
            view = inf.inflate(R.layout.fragement2, root, false);
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
    protected void loadData() {

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
                Log.i("TAG", " noteModelList.get(i).getNoteName()" + noteModelList.get(i).getNoteName());
                NoteDetailActivity.statrNoteDetailActivity(getActivity(), Integer.valueOf(noteModelList.get(i).getUid()));
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new BaseDialog(getActivity(), getString(R.string.note_delete), getString(R.string.note_ts), getString(R.string.note_cancer), getString(R.string.note_sure), new BaseDialog.onSubClickBack() {
                    @Override
                    public void onClickBack(int status) {
                        if (status == 1) {
                            SQLDBManger.deleteNote(Integer.valueOf(noteModelList.get(i).getUid()));
                           // Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_LONG).show();
                            noteModelList.remove(i);
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
                //去搜索界面
                SearchNoteActivity.startSearchNoteActivity(getActivity());
            }
        });

    }

    public void initData(){
        noteModelList.clear();
        noteModelList = SQLDBManger.getNoteList();
        noteAdapter = new NoteAdapter(getActivity(), noteModelList);
        listView.setAdapter(noteAdapter);
    }

}

