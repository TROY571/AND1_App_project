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
import com.outsideweather.cn.dao.CityDao;
import com.outsideweather.cn.db.DBManger;
import com.outsideweather.cn.Bean.CityBean;
import com.outsideweather.cn.R;
import com.outsideweather.cn.adpter.CityAdapter;
import com.outsideweather.cn.base.BaseActivity;
import com.outsideweather.cn.dialog.BaseDialog;
import com.outsideweather.cn.event.WeatherCityEvent;
import com.outsideweather.cn.view.ScrollListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * 日期：2022/4/2
 * email：
 * description：我收藏的城市
 */
public class WeatherCityActivity extends BaseActivity {
    public static void startMyCityActivity(Context context) {
        Intent intent = new Intent(context, WeatherCityActivity.class);
        context.startActivity(intent);
    }
    private RelativeLayout rlTitle;
    private ImageView ivBack;
    private TextView ivTitle;
    private ScrollListView listview;
    private FloatingActionButton ivAdd;
    public static WeatherCityActivity instance;
    private CityAdapter myCityAdapter;
    private List<CityBean> cityBeanArrayList = new ArrayList<>();

    public static WeatherCityActivity getInstance() {
        return instance;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_city);
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
                AddWeatherCityActivity.startAddCityActivity(WeatherCityActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void initData() {
        CityDao cityDao = DBManger.getInstance(this).cityDao();
        cityBeanArrayList = cityDao.cityQueryAll();
     //   cityBeanArrayList = SQLDBManger.getCityList();
        initListData();
    }
    public void initListData(){
        myCityAdapter =new CityAdapter(this, cityBeanArrayList);
        listview.setAdapter(myCityAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventBus.getDefault().post(new WeatherCityEvent(1, cityBeanArrayList.get(i).getCityName(),
                        cityBeanArrayList.get(i).getCityPostion()));//Adm2 代表市
                finish();
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new BaseDialog(WeatherCityActivity.this, getString(R.string.w_w15_18) , getString(R.string.note_tips), getString(R.string.note_cancer), getString(R.string.note_sure),  new BaseDialog.onSubClickBack() {
                    @Override
                    public void onClickBack(int status) {
                        CityDao cityDao = DBManger.getInstance(WeatherCityActivity.this).cityDao();
                        cityDao.cityDelete(cityBeanArrayList.get(i));
                        cityBeanArrayList.remove(i);
                        myCityAdapter.notifyDataSetChanged();
                    }
                }).show();


                return true;
            }
        });

    }
}
