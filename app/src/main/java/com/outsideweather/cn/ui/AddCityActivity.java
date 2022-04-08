package com.outsideweather.cn.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.outsideweather.cn.manger.SQLDBManger;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Range;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.view.QWeather;
import com.ruffian.library.widget.RRelativeLayout;
import com.ruffian.library.widget.RTextView;
import com.outsideweather.cn.Model.CityModel;
import com.outsideweather.cn.R;
import com.outsideweather.cn.adpter.SearchCityAdapter;
import com.outsideweather.cn.base.BaseActivity;
import com.outsideweather.cn.event.CityEvent;
import com.outsideweather.cn.util.TostUtil;
import com.outsideweather.cn.view.RecyclerViewAnimation;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * 日期：2022/4/2
 * email：
 * description：add city
 */
public class AddCityActivity extends BaseActivity {
    private RelativeLayout rlTitle;
    private ImageView ivBack;
    private RRelativeLayout lySearch1;
    private EditText etSearch;
    private RTextView tvSub;
    private RecyclerView listview;
    private String serachKey="";
    private SearchCityAdapter searchCityAdapter;
    private List<GeoBean.LocationBean> locationBeans=new ArrayList<>();
    public static void startAddCityActivity(Context context) {
        Intent intent = new Intent(context, AddCityActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
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
                    TostUtil.showTost(getString(R.string.w_w15_16));
                }
                //https://dev.qweather.com/docs/android-sdk/android-geo/#%E5%9F%8E%E5%B8%82%E4%BF%A1%E6%81%AF%E6%9F%A5%E8%AF%A2
                serachKey=etSearch.getText().toString();
                initSearchData();
            }
        });
        searchCityAdapter = new SearchCityAdapter( locationBeans);
        listview.setLayoutManager(new LinearLayoutManager(this));
        listview.setAdapter(searchCityAdapter);

        searchCityAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //发送消息
            String key=locationBeans.get(position).getLon()+","+locationBeans.get(position).getLat();

        CityModel cityModel=    SQLDBManger.getCity(locationBeans.get(position).getName());
            if(!TextUtils.isEmpty(cityModel.getCityName())){
                EventBus.getDefault().post(new CityEvent(1,locationBeans.get(position).getName(),
                        key));//Adm2 代表市
                MyCityActivity.getInstance().finish();
                finish();
            }else{
                CityModel cityModel1=new CityModel();
                cityModel1.setCityPostion(key);
                cityModel1.setCityName(locationBeans.get(position).getName());
                SQLDBManger.addCity(cityModel1);
                EventBus.getDefault().post(new CityEvent(1,locationBeans.get(position).getName(),
                        key));//Adm2 代表市
                MyCityActivity.getInstance().finish();
                finish();
            }
        });
    }
    public void initSearchData(){
        Log.i("shinemao","开始搜索到了");
        QWeather.getGeoCityLookup(this, serachKey,Range.WORLD,20, Lang.EN, new QWeather.OnResultGeoListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("shinemao","搜索到了2"+throwable.toString());
                Log.i("shinemao","搜索到了2"+throwable.getMessage());
            }

            @Override
            public void onSuccess(GeoBean geoBean) {
                Log.i("shinemao","搜索到了3");
                if (geoBean.getCode() == Code.OK) {
                    Log.i("shinemao","搜索到了");
                    if(geoBean.getLocationBean().size()>=0){
                        locationBeans.clear();
                        locationBeans.addAll(geoBean.getLocationBean());
                    }else{
                       // TostUtil.showTost("暂无搜索结果");
                        locationBeans.clear();
                    }
                    searchCityAdapter.notifyDataSetChanged();

                    RecyclerViewAnimation. runLayoutAnimation(listview);
                } else if (geoBean.getCode() == Code.NO_DATA) {
                    TostUtil.showTost(Code.NO_DATA.getTxt());
                } else if (geoBean.getCode() == Code.NO_MORE_REQUESTS) {
                    TostUtil.showTost(Code.NO_MORE_REQUESTS.getTxt());
                } else if (geoBean.getCode() == Code.TOO_FAST) {
                    TostUtil.showTost(Code.TOO_FAST.getTxt());
                } else if (geoBean.getCode() == Code.PERMISSION_DENIED) {
                    TostUtil.showTost(Code.PERMISSION_DENIED.getTxt());
                } else {
                    TostUtil.showTost(getString(R.string.w_no_more));
                }

            }
            
        }) ;
    }
}
