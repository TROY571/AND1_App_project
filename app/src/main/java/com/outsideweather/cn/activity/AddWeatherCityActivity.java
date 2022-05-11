package com.outsideweather.cn.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.outsideweather.cn.dao.CityDao;
import com.outsideweather.cn.db.AppDataBaseDB;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Range;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.view.QWeather;
import com.ruffian.library.widget.RRelativeLayout;
import com.ruffian.library.widget.RTextView;
import com.outsideweather.cn.Bean.CityBean;
import com.outsideweather.cn.R;
import com.outsideweather.cn.adpter.CitySearchAdapter;
import com.outsideweather.cn.base.BaseActivity;
import com.outsideweather.cn.event.WeatherCityEvent;
import com.outsideweather.cn.util.PublicTostUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * email：
 * description：AddWeatherCityActivity
 */
public class AddWeatherCityActivity extends BaseActivity {
    private RelativeLayout rlTitle;
    private ImageView ivBack;
    private RRelativeLayout lySearch1;
    private EditText etSearch;
    private RTextView tvSub;
    private RecyclerView listview;
    private String serachKey="";
    private CitySearchAdapter citySearchAdapter;
    private List<GeoBean.LocationBean> locationBeanList=new ArrayList<>();
    public static void startAddCityActivity(Context context) {
        Intent intent = new Intent(context, AddWeatherCityActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weather_city);
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
        lySearch1 = (RRelativeLayout) findViewById(R.id.lySearch1);
        etSearch = (EditText) findViewById(R.id.etSearch);
        tvSub = (RTextView) findViewById(R.id.tv_sub);
        listview = (RecyclerView) findViewById(R.id.listview);
        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etSearch.getText().toString())){
                    PublicTostUtil.showTost(getString(R.string.w_w15_16));
                }
                serachKey=etSearch.getText().toString();
                initData();
            }
        });
        citySearchAdapter = new CitySearchAdapter( locationBeanList);
        listview.setLayoutManager(new LinearLayoutManager(this));
        listview.setAdapter(citySearchAdapter);

        citySearchAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //send message
            String key=locationBeanList.get(position).getLon()+","+locationBeanList.get(position).getLat();
            CityDao cityDao = AppDataBaseDB.getInstance(this).cityDao();
        CityBean cityBean = cityDao.getCityByName(locationBeanList.get(position).getName());
            if(cityBean!=null&&!TextUtils.isEmpty(cityBean.getCityName())){
                EventBus.getDefault().post(new WeatherCityEvent(1,locationBeanList.get(position).getName(),
                        key));//Adm2 means city
                WeatherCityActivity.getInstance().finish();
                finish();
            }else{
                CityBean cityBean1 =new CityBean();
                cityBean1.setCityPostion(key);
                cityBean1.setCityName(locationBeanList.get(position).getName());
                cityDao.insertCity(cityBean1);
                EventBus.getDefault().post(new WeatherCityEvent(1,locationBeanList.get(position).getName(),
                        key));//Adm2 means city
                WeatherCityActivity.getInstance().finish();
                finish();
            }
        });
    }
    public void initData(){
        QWeather.getGeoCityLookup(this, serachKey,Range.WORLD,20, Lang.EN, new QWeather.OnResultGeoListener() {
            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onSuccess(GeoBean geoBean) {
                if (geoBean.getCode() == Code.OK) {
                    if(geoBean.getLocationBean().size()>=0){
                        locationBeanList.clear();
                        locationBeanList.addAll(geoBean.getLocationBean());
                    }else{
                        locationBeanList.clear();
                    }
                    citySearchAdapter.notifyDataSetChanged();
                } else if (geoBean.getCode() == Code.NO_DATA) {
                    PublicTostUtil.showTost(Code.NO_DATA.getTxt());
                } else if (geoBean.getCode() == Code.NO_MORE_REQUESTS) {
                    PublicTostUtil.showTost(Code.NO_MORE_REQUESTS.getTxt());
                } else if (geoBean.getCode() == Code.TOO_FAST) {
                    PublicTostUtil.showTost(Code.TOO_FAST.getTxt());
                } else if (geoBean.getCode() == Code.PERMISSION_DENIED) {
                    PublicTostUtil.showTost(Code.PERMISSION_DENIED.getTxt());
                } else {
                    PublicTostUtil.showTost(getString(R.string.w_no_more));
                }

            }
            
        }) ;
    }
}
