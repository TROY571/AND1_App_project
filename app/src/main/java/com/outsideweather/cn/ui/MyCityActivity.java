package com.outsideweather.cn.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.outsideweather.cn.manger.SQLDBManger;
import com.ruffian.library.widget.RTextView;
import com.outsideweather.cn.Model.CityModel;
import com.outsideweather.cn.R;
import com.outsideweather.cn.adpter.MyCityAdapter;
import com.outsideweather.cn.base.BaseActivity;
import com.outsideweather.cn.dialog.BaseDialog;
import com.outsideweather.cn.event.CityEvent;
import com.outsideweather.cn.util.ScrollListView;
import com.outsideweather.cn.util.TostUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * 日期：2022/4/2
 * email：
 * description：Favourite Citys
 */
public class MyCityActivity extends BaseActivity {
    public static void startMyCityActivity(Context context) {
        Intent intent = new Intent(context, MyCityActivity.class);
        context.startActivity(intent);
    }
    private RelativeLayout rlTitle;
    private ImageView ivBack;
    private TextView ivTitle;
    private ScrollListView listview;
    private FloatingActionButton ivAdd;
    public static MyCityActivity instance;
    private MyCityAdapter myCityAdapter;
    private List<CityModel> cityModelArrayList = new ArrayList<>();

    public static MyCityActivity getInstance() {
        return instance;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_city);
        instance=this;
        initView();
        initData();
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
        listview = (ScrollListView) findViewById(R.id.listview);
        ivAdd = (FloatingActionButton) findViewById(R.id.ivAdd);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCityActivity.startAddCityActivity(MyCityActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void initData() {
        cityModelArrayList= SQLDBManger.getCityList();
        initListData();
    }
    public void initListData(){
        myCityAdapter =new MyCityAdapter(this,cityModelArrayList);
        listview.setAdapter(myCityAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventBus.getDefault().post(new CityEvent(1,cityModelArrayList.get(i).getCityName(),
                        cityModelArrayList.get(i).getCityPostion()));//Adm2 代表市
                finish();
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new BaseDialog(MyCityActivity.this, getString(R.string.w_w15_18) , getString(R.string.note_ts), getString(R.string.note_cancer), getString(R.string.note_sure),  new BaseDialog.onSubClickBack() {
                    @Override
                    public void onClickBack(int status) {
                      SQLDBManger.deleteCity(cityModelArrayList.get(i).getId());
                    }
                }).show();


                return true;
            }
        });

    }
}
