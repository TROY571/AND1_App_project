package com.outsideweather.cn.fragement;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.outsideweather.cn.R;
import com.outsideweather.cn.adpter.Weather24HAdapter;
import com.outsideweather.cn.adpter.Weather7DayAdapter;
import com.outsideweather.cn.base.BaseAplication;
import com.outsideweather.cn.base.BaseLazyFragment;
import com.outsideweather.cn.dialog.WeatherWindow;
import com.outsideweather.cn.event.WeatherCityEvent;
import com.outsideweather.cn.BasePermit.PermitUtil;
import com.outsideweather.cn.util.BaseDateUtils;
import com.outsideweather.cn.util.StatusBarUtil;
import com.outsideweather.cn.util.PublicTostUtil;
import com.outsideweather.cn.util.HeFengWeatherUtil;
import com.outsideweather.cn.view.RoundProgressBar;
import com.outsideweather.cn.view.WhiteWindmills;
import com.qweather.sdk.bean.air.AirNowBean;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.QWeather;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * email：
 * description：天气主页
 */
public class WeatherFragement extends BaseLazyFragment {

    private View view;
    LocationClient mLocClient;
    boolean isFirstLoc = true;// 是否首次定位
    public MyLocationListenner myListener = new MyLocationListenner();
    private RelativeLayout rlTop;
    private ImageView ivRefresh;
    private TextView ivTitle;
    private ImageView ivAdd;
    private TextView tvTemperature;
    private TextView tvTempHeight;
    private TextView tvTempLow;
    private TextView tvOldTime;
    private TextView tvTempBody;
    private ImageView ivNewWhather;
    private TextView tvDay;
    private TextView tvDate;
    private TextView tvLocation;
    private String Plongitude;
    private String Platitude;
    private String postionData;
    private String city;
    private RecyclerView rv;
    private TextView tvMoreDaily;
    private TextView tvMoreAir;
    private RoundProgressBar rpbAqi;
    private TextView tvPm10;
    private TextView tvPm25;
    private TextView tvNo2;
    private TextView tvSo2;
    private TextView tvO3;
    private TextView tvCo;
    private RelativeLayout rlWind;
    private WhiteWindmills wwBig;
    private WhiteWindmills wwSmall;
    private TextView tvWindDirection;
    private TextView tvWindPower;
    private List<WeatherDailyBean.DailyBean> dailyBeanList7 = new ArrayList<>();
    private Weather7DayAdapter wheather7Adapter;
    private RecyclerView rvHourly;
    private List<WeatherHourlyBean.HourlyBean> hourlyBeans24 = new ArrayList<>();
    private Weather24HAdapter hourly24Adapter;
    private LinearLayout lyLocation;
    private ImageView ivLocation;
    private RelativeLayout rltop;
    private ImageView ivImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup root, Bundle state) {
        if (view == null) {
            view = inf.inflate(R.layout.weather_detail, root, false);
            StatusBarUtil.setLightStatusBar(getActivity(), false);
            initView();
            EventBus.getDefault().register(this);
            PermitUtil.checkPerms(getActivity(), null);
            LocationClient.setAgreePrivacy(true);
            SDKInitializer.initialize(BaseAplication.getContext());
            startLocation();
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
            StatusBarUtil.setLightStatusBar(getActivity(), false);

        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    public void startLocation() {
        // 地图初始化
        // 定位初始化
        try {
            mLocClient = new LocationClient(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mLocClient != null) {
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true); // 打开gps
            option.setCoorType("bd09ll"); // 设置坐标类型
            option.setScanSpan(2000);
            // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            // 可选，设置是否需要地址信息，默认不需要
            option.setIsNeedAddress(true);
            // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            option.setIsNeedLocationPoiList(true);
            mLocClient.setLocOption(option);
            mLocClient.registerLocationListener(myListener);
            mLocClient.start();// 启动sdk
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WeatherCityEvent event) {
        postionData = event.searchKey;
        city = event.name;
        ivTitle.setText(event.name);
        getNowWeather(postionData);
        get7DayWeather(postionData);
        get24HoursWheather(postionData);
        getAirNows(postionData);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        rlTop = view.findViewById(R.id.rlTop);//(RelativeLayout)
        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) rlTop.getLayoutParams();
        lp2.topMargin = StatusBarUtil.getStatusBarHeight(getContext());
        rlTop.setLayoutParams(lp2);
        ivRefresh = view.findViewById(R.id.ivRefresh);//(ImageView)
        ivTitle = view.findViewById(R.id.iv_title);//(TextView)
        ivAdd = view.findViewById(R.id.ivAdd);//(ImageView)
        tvTemperature = view.findViewById(R.id.tv_temperature);//(TextView)
        tvTempHeight = view.findViewById(R.id.tv_temp_height);//(TextView)
        tvTempLow = view.findViewById(R.id.tv_temp_low);//(TextView)
        tvOldTime = view.findViewById(R.id.tv_old_time);//(TextView)
        tvTempBody = view.findViewById(R.id.tv_temp_boday);//(TextView)
        ivNewWhather = view.findViewById(R.id.ivNewWhather);//(ImageView)
        tvDay = view.findViewById(R.id.tvDay);//(TextView)
        tvDate = view.findViewById(R.id.tvDate);//(TextView)
        tvLocation = view.findViewById(R.id.tvLocation);//(TextView)
        rv = view.findViewById(R.id.rv);//(RecyclerView)
        rpbAqi = view.findViewById(R.id.rpb_aqi);//(RoundProgressBar)
        tvPm10 = view.findViewById(R.id.tv_pm10);//(TextView)
        tvPm25 = view.findViewById(R.id.tv_pm25);//(TextView)
        tvNo2 = view.findViewById(R.id.tv_no2);//(TextView)
        tvSo2 = view.findViewById(R.id.tv_so2);//(TextView)
        tvO3 = view.findViewById(R.id.tv_o3);//(TextView)
        tvCo = view.findViewById(R.id.tv_co);//(TextView)
        rlWind = view.findViewById(R.id.rl_wind);//(RelativeLayout)
        wwBig = view.findViewById(R.id.ww_big);//(WhiteWindmills)
        wwSmall = view.findViewById(R.id.ww_small);//(WhiteWindmills)
        tvWindDirection = view.findViewById(R.id.tv_wind_direction);//(TextView)
        tvWindPower = view.findViewById(R.id.tv_wind_power);//(TextView)
        rvHourly = view.findViewById(R.id.rv_hourly);//(RecyclerView)
        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();//刷新天气 当前定位的天气
            }
        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new WeatherWindow(getActivity()).show(ivAdd);
            }
        });
        lyLocation = (LinearLayout) view.findViewById(R.id.lyLocation);
        ivLocation = (ImageView) view.findViewById(R.id.iv_location);
        lyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //回到定位城市
                isFirstLoc = true;
                startLocation();
            }
        });
        rltop = (RelativeLayout) view.findViewById(R.id.rltop);
        ivImage = (ImageView)view. findViewById(R.id.ivImage);
        initListBase();



    }

    public void initListBase() {
        //天气预报  7天
        wheather7Adapter = new Weather7DayAdapter(dailyBeanList7);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(wheather7Adapter);
        //天气预报 24小时
        hourly24Adapter = new Weather24HAdapter(hourlyBeans24);
        LinearLayoutManager managerHourly = new LinearLayoutManager(getActivity());
        managerHourly.setOrientation(RecyclerView.HORIZONTAL);//设置列表为横向
        rvHourly.setLayoutManager(managerHourly);
        rvHourly.setAdapter(hourly24Adapter);
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            if (isFirstLoc) {
                isFirstLoc = false;
                Plongitude = "" + location.getLongitude();
                Platitude = "" + location.getLatitude();
                if (!TextUtils.isEmpty(location.getDistrict())) {
                    city = location.getDistrict();
                } else {
                    city = location.getCity();
                }

                //获取天气
                postionData = location.getLongitude() + "," + location.getLatitude();
                tvLocation.setText(city);
                ivTitle.setText(city);
                initData();
            }
        }

    }

    public void initData() {
        getNowWeather(postionData);
        get7DayWeather(postionData);
        get24HoursWheather(postionData);
        getAirNows(postionData);
    }

    //获取当前天气 根据经纬度获取天气
    public void getNowWeather(String location) {
        QWeather.getWeatherNow(getActivity(), location, Lang.EN, Unit.METRIC, new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onSuccess(WeatherNowBean weatherNowBean) {
                if (weatherNowBean.getCode() == Code.OK) {
                    WeatherNowBean.NowBaseBean baseBean = weatherNowBean.getNow();
                    String time = BaseDateUtils.updateTime(baseBean.getObsTime());//截去前面的字符，保留后面所有的字符，就剩下 22:00
                    tvOldTime.setText(getString(R.string.w_time) + HeFengWeatherUtil.showTimeInfo(time) + time);
                    tvTempBody.setText(getString(R.string.w_boday_hot) + baseBean.getFeelsLike() + "℃");
                    tvTemperature.setText(baseBean.getTemp());
                    tvDay.setText(BaseDateUtils.getWeekOfDate(new Date()));//星期
                    tvDate.setText(BaseDateUtils.getNowDate());

                    int code = Integer.parseInt(baseBean.getIcon());
                    HeFengWeatherUtil.changeIcon(ivNewWhather, code);
                    HeFengWeatherUtil.changeBgIcon(ivImage, code);
                    tvWindDirection.setText(getString(R.string.w_wind_x) + "     " + baseBean.getWindDir());//风向
                    tvWindPower.setText(getString(R.string.w_wind_l) + "     " + baseBean.getWindScale());//风力
                    wwBig.startRotate();//大风车开始转动
                    wwSmall.startRotate();//小风车开始转动
                } else if (weatherNowBean.getCode() == Code.NO_DATA) {
                    PublicTostUtil.showTost(Code.NO_DATA.getTxt());
                } else if (weatherNowBean.getCode() == Code.NO_MORE_REQUESTS) {
                    PublicTostUtil.showTost(Code.NO_MORE_REQUESTS.getTxt());
                } else if (weatherNowBean.getCode() == Code.TOO_FAST) {
                    PublicTostUtil.showTost(Code.TOO_FAST.getTxt());
                } else if (weatherNowBean.getCode() == Code.PERMISSION_DENIED) {
                    PublicTostUtil.showTost(Code.PERMISSION_DENIED.getTxt());
                } else {
                    PublicTostUtil.showTost(getString(R.string.err_msg));
                }

            }
        });
    }

    //七天天气预报
    public void get7DayWeather(String lotion) {
        QWeather.getWeather7D(getActivity(), lotion, new QWeather.OnResultWeatherDailyListener() {
            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onSuccess(WeatherDailyBean weatherDailyBean) {
                if (weatherDailyBean.getCode() == Code.OK) {
                    WeatherDailyBean.DailyBean dailyBean = weatherDailyBean.getDaily().get(0);
                    tvTempHeight.setText(dailyBean.getTempMax() + "℃");
                    tvTempLow.setText(" / " + dailyBean.getTempMin() + "℃");
                    //clear old data
                    dailyBeanList7.clear();
                    //add new data
                    dailyBeanList7.addAll(weatherDailyBean.getDaily());
                    //refresh
                    wheather7Adapter.notifyDataSetChanged();
                } else if (weatherDailyBean.getCode() == Code.NO_DATA) {
                    PublicTostUtil.showTost(Code.NO_DATA.getTxt());
                } else if (weatherDailyBean.getCode() == Code.NO_MORE_REQUESTS) {
                    PublicTostUtil.showTost(Code.NO_MORE_REQUESTS.getTxt());
                } else if (weatherDailyBean.getCode() == Code.TOO_FAST) {
                    PublicTostUtil.showTost(Code.TOO_FAST.getTxt());
                } else if (weatherDailyBean.getCode() == Code.PERMISSION_DENIED) {
                    PublicTostUtil.showTost(Code.PERMISSION_DENIED.getTxt());
                } else {
                    PublicTostUtil.showTost(getString(R.string.err_msg));
                }

            }
        });
    }

    public void get24HoursWheather(String location) {
        QWeather.getWeather24Hourly(getActivity(), location, new QWeather.OnResultWeatherHourlyListener() {
            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onSuccess(WeatherHourlyBean weatherHourlyBean) {
                if (weatherHourlyBean.getCode() == Code.OK) {

                    //添加数据之前先清除
                    hourlyBeans24.clear();
                    //添加数据
                    hourlyBeans24.addAll(weatherHourlyBean.getHourly());
                    //刷新列表
                    hourly24Adapter.notifyDataSetChanged();
                } else if (weatherHourlyBean.getCode() == Code.NO_DATA) {
                    PublicTostUtil.showTost(Code.NO_DATA.getTxt());
                } else if (weatherHourlyBean.getCode() == Code.NO_MORE_REQUESTS) {
                    PublicTostUtil.showTost(Code.NO_MORE_REQUESTS.getTxt());
                } else if (weatherHourlyBean.getCode() == Code.TOO_FAST) {
                    PublicTostUtil.showTost(Code.TOO_FAST.getTxt());
                } else if (weatherHourlyBean.getCode() == Code.PERMISSION_DENIED) {
                    PublicTostUtil.showTost(Code.PERMISSION_DENIED.getTxt());
                } else {
                    PublicTostUtil.showTost(getString(R.string.err_msg));
                }

            }
        });
    }

    //今天天气状况
    public void getAirNows(String location) {
        QWeather.getAirNow(getActivity(), location, Lang.EN, new QWeather.OnResultAirNowListener() {
            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onSuccess(AirNowBean airNowBean) {
                if (airNowBean.getCode() == Code.OK) {
                    AirNowBean.NowBean nowBean = airNowBean.getNow();
                    rpbAqi.setMaxProgress(300);//最大进度，用于计算
                    rpbAqi.setMinText("0");//设置显示最小值
                    rpbAqi.setMinTextSize(32f);
                    rpbAqi.setMaxText("300");//设置显示最大值
                    rpbAqi.setMaxTextSize(32f);
                    rpbAqi.setProgress(Float.valueOf(nowBean.getAqi()));//当前进度
                    rpbAqi.setArcBgColor(getActivity().getResources().getColor(R.color.arc_bg_color));//圆弧的颜色
                    rpbAqi.setProgressColor(getActivity().getResources().getColor(R.color.arc_progress_color));//进度圆弧的颜色
                    rpbAqi.setFirstText(nowBean.getCategory());//空气质量描述
                    rpbAqi.setFirstTextSize(44f);//第一行文本的字体大小
                    rpbAqi.setSecondText(nowBean.getAqi());//空气质量值
                    rpbAqi.setSecondTextSize(64f);//第二行文本的字体大小
                    rpbAqi.setMinText("0");
                    rpbAqi.setMinTextColor(getActivity().getResources().getColor(R.color.arc_progress_color));
                    tvPm10.setText(nowBean.getPm10() + " μg/m³");//PM10
                    tvPm25.setText(nowBean.getPm2p5() + " μg/m³");//PM2.5
                    tvNo2.setText(nowBean.getNo2() + " mg/m³");//二氧化氮
                    tvSo2.setText(nowBean.getSo2() + " mg/m³");//二氧化硫
                    tvO3.setText(nowBean.getO3() + " mg/m³");//臭氧
                    tvCo.setText(nowBean.getCo() + " mg/m³");//一氧化碳

                } else if (airNowBean.getCode() == Code.NO_DATA) {
                    PublicTostUtil.showTost(Code.NO_DATA.getTxt());
                } else if (airNowBean.getCode() == Code.NO_MORE_REQUESTS) {
                    PublicTostUtil.showTost(Code.NO_MORE_REQUESTS.getTxt());
                } else if (airNowBean.getCode() == Code.TOO_FAST) {
                    PublicTostUtil.showTost(Code.TOO_FAST.getTxt());
                } else if (airNowBean.getCode() == Code.PERMISSION_DENIED) {
                    PublicTostUtil.showTost(Code.PERMISSION_DENIED.getTxt());
                } else {
                    PublicTostUtil.showTost(getString(R.string.err_msg));
                }
            }
        });
    }
}

