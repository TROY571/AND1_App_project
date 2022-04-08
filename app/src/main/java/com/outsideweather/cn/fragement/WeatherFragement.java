package com.outsideweather.cn.fragement;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.outsideweather.cn.adpter.Hourly24Adapter;
import com.outsideweather.cn.adpter.Weather7Adapter;
import com.outsideweather.cn.base.BaseAplication;
import com.outsideweather.cn.base.LazyFragment;
import com.outsideweather.cn.dialog.PopupMoreWindow;
import com.outsideweather.cn.event.CityEvent;
import com.outsideweather.cn.permit.PermitUtil;
import com.outsideweather.cn.ui.Weather15Activity;
import com.outsideweather.cn.util.DateUtils;
import com.outsideweather.cn.util.StatusBarUtil;
import com.outsideweather.cn.util.TostUtil;
import com.outsideweather.cn.util.WeatherUtil;
import com.outsideweather.cn.view.RecyclerViewAnimation;
import com.outsideweather.cn.view.RoundProgressBar;
import com.outsideweather.cn.view.WhiteWindmills;
import com.qweather.sdk.bean.IndicesBean;
import com.qweather.sdk.bean.air.AirNowBean;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.IndicesType;
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
public class WeatherFragement extends LazyFragment {

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
    private LinearLayout LyWord;
    private TextView tvTempBoday;
    private ImageView ivNewWhather;
    private TextView tvDay;
    private TextView tvMouth;
    private TextView tvLocation;
    private TextView tvWord;
    private TextView tvWordTranslate;//location.getLongitude() + "," + location.getLatitude()
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
    private TextView tvMoreLifestyle;
    private TextView tvUv;
    private TextView tvComf;
    private TextView tvTrav;
    private TextView tvSport;
    private TextView tvCw;
    private TextView tvAir;
    private TextView tvDrsg;
    private TextView tvFlu;
    private List<WeatherDailyBean.DailyBean> dailyBeanList7 = new ArrayList<>();
    private Weather7Adapter wheather7Adapter;
    private RecyclerView rvHourly;
    private List<WeatherHourlyBean.HourlyBean> hourlyBeans24 = new ArrayList<>();
    private Hourly24Adapter hourly24Adapter;
    private LinearLayout lyLocation;
    private ImageView ivLocation;
    private RelativeLayout rltop;
    private ImageView ivImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup root, Bundle state) {
        if (view == null) {
            view = inf.inflate(R.layout.fragement1, root, false);
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
    protected void loadData() {

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
    public void onEvent(CityEvent event) {
        postionData = event.searchKey;
        city = event.name;
        ivTitle.setText(event.name);
        Log.i("shinemao", "postionData" + postionData + "event.name" + event.name);
        getNewWeather(postionData);
        get7DayWeather(postionData);
        get24HoursWheather(postionData);
        getAirNows(postionData);
        // getDayIndices(postionData);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        rlTop = (RelativeLayout) view.findViewById(R.id.rlTop);
        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) rlTop.getLayoutParams();
        lp2.topMargin = StatusBarUtil.getStatusBarHeight(getContext());
        rlTop.setLayoutParams(lp2);
        ivRefresh = (ImageView) view.findViewById(R.id.ivRefresh);
        ivTitle = (TextView) view.findViewById(R.id.iv_title);
        ivAdd = (ImageView) view.findViewById(R.id.ivAdd);
        tvTemperature = (TextView) view.findViewById(R.id.tv_temperature);
        tvTempHeight = (TextView) view.findViewById(R.id.tv_temp_height);
        tvTempLow = (TextView) view.findViewById(R.id.tv_temp_low);
        tvOldTime = (TextView) view.findViewById(R.id.tv_old_time);
        LyWord = (LinearLayout) view.findViewById(R.id.LyWord);
        tvTempBoday = (TextView) view.findViewById(R.id.tv_temp_boday);
        ivNewWhather = (ImageView) view.findViewById(R.id.ivNewWhather);
        tvDay = (TextView) view.findViewById(R.id.tvDay);
        tvMouth = (TextView) view.findViewById(R.id.tvMouth);
        tvLocation = (TextView) view.findViewById(R.id.tvLocation);
        tvWord = (TextView) view.findViewById(R.id.tvWord);
        tvWordTranslate = (TextView) view.findViewById(R.id.tvWordTranslate);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        tvMoreDaily = (TextView) view.findViewById(R.id.tv_more_daily);
        tvMoreAir = (TextView) view.findViewById(R.id.tv_more_air);
        rpbAqi = (RoundProgressBar) view.findViewById(R.id.rpb_aqi);
        tvPm10 = (TextView) view.findViewById(R.id.tv_pm10);
        tvPm25 = (TextView) view.findViewById(R.id.tv_pm25);
        tvNo2 = (TextView) view.findViewById(R.id.tv_no2);
        tvSo2 = (TextView) view.findViewById(R.id.tv_so2);
        tvO3 = (TextView) view.findViewById(R.id.tv_o3);
        tvCo = (TextView) view.findViewById(R.id.tv_co);
        rlWind = (RelativeLayout) view.findViewById(R.id.rl_wind);
        wwBig = (WhiteWindmills) view.findViewById(R.id.ww_big);
        wwSmall = (WhiteWindmills) view.findViewById(R.id.ww_small);
        tvWindDirection = (TextView) view.findViewById(R.id.tv_wind_direction);
        tvWindPower = (TextView) view.findViewById(R.id.tv_wind_power);
        tvMoreLifestyle = (TextView) view.findViewById(R.id.tv_more_lifestyle);
        tvUv = (TextView) view.findViewById(R.id.tv_uv);
        tvComf = (TextView) view.findViewById(R.id.tv_comf);
        tvTrav = (TextView) view.findViewById(R.id.tv_trav);
        tvSport = (TextView) view.findViewById(R.id.tv_sport);
        tvCw = (TextView) view.findViewById(R.id.tv_cw);
        tvAir = (TextView) view.findViewById(R.id.tv_air);
        tvDrsg = (TextView) view.findViewById(R.id.tv_drsg);
        tvFlu = (TextView) view.findViewById(R.id.tv_flu);
        rvHourly = (RecyclerView) view.findViewById(R.id.rv_hourly);
        LyWord.setVisibility(View.GONE);
        tvMoreDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(postionData)) {
                    TostUtil.showTost(getString(R.string.w_no_more));
                } else {
                    Weather15Activity.startWeather15Activity(getActivity(), city, postionData);
                }
            }
        });
        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();//刷新天气 当前定位的天气
                //isFirstLoc=true;
                // startLocation();
            }
        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PopupMoreWindow(getActivity(), new PopupMoreWindow.onWorldRefress() {
                    @Override
                    public void refuress() {
                        LyWord.setVisibility(View.GONE);
                    }
                }).show(ivAdd);
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
        wheather7Adapter = new Weather7Adapter(dailyBeanList7);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(wheather7Adapter);
        //天气预报 24小时
        hourly24Adapter = new Hourly24Adapter(hourlyBeans24);
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
            //   Log.i("shinemao","位置信息");
            if (isFirstLoc) {
                Log.i("shinemao", "位置信息1");
                isFirstLoc = false;
                Plongitude = "" + location.getLongitude();
                Platitude = "" + location.getLatitude();
                if (!TextUtils.isEmpty(location.getDistrict())) {
                    city = location.getDistrict();
                } else {
                    city = location.getCity();
                }

                //定位到了立马获取天气
                postionData = location.getLongitude() + "," + location.getLatitude();
                tvLocation.setText(city);
                ivTitle.setText(city);
                initData();
            }
        }

    }

    public void initData() {
        getNewWeather(postionData);
        get7DayWeather(postionData);
        get24HoursWheather(postionData);
        getAirNows(postionData);
        // getDayIndices(postionData);
    }

    //获取当前天气 根据经纬度获取天气
    public void getNewWeather(String lotion) {
        QWeather.getWeatherNow(getActivity(), lotion, Lang.EN, Unit.METRIC, new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("shinemao", "weatherNowBean错误");
            }

            @Override
            public void onSuccess(WeatherNowBean weatherNowBean) {
                Log.i("shinemao", "weatherNowBean" + weatherNowBean.getCode());
                if (weatherNowBean.getCode() == Code.OK) {
                    WeatherNowBean.NowBaseBean baseBean = weatherNowBean.getNow();
                    String time = DateUtils.updateTime(baseBean.getObsTime());//截去前面的字符，保留后面所有的字符，就剩下 22:00
                    tvOldTime.setText(getString(R.string.w_time) + WeatherUtil.showTimeInfo(time) + time);
                    tvTempBoday.setText(getString(R.string.w_boday_hot) + baseBean.getFeelsLike() + "℃");
                    tvTemperature.setText(baseBean.getTemp());
                    tvDay.setText(DateUtils.getWeekOfDate(new Date()));//星期
                    tvMouth.setText(DateUtils.getNowDate());

                    int code = Integer.parseInt(baseBean.getIcon());
                    WeatherUtil.changeIcon(ivNewWhather, code);
                    WeatherUtil.changeBgIcon(ivImage, code);
                    tvWindDirection.setText(getString(R.string.w_wind_x) + "     " + baseBean.getWindDir());//风向
                    tvWindPower.setText(getString(R.string.w_wind_l) + "     " + baseBean.getWindScale());//风力
                    wwBig.startRotate();//大风车开始转动
                    wwSmall.startRotate();//小风车开始转动
                } else if (weatherNowBean.getCode() == Code.NO_DATA) {
                    TostUtil.showTost(Code.NO_DATA.getTxt());
                } else if (weatherNowBean.getCode() == Code.NO_MORE_REQUESTS) {
                    TostUtil.showTost(Code.NO_MORE_REQUESTS.getTxt());
                } else if (weatherNowBean.getCode() == Code.TOO_FAST) {
                    TostUtil.showTost(Code.TOO_FAST.getTxt());
                } else if (weatherNowBean.getCode() == Code.PERMISSION_DENIED) {
                    TostUtil.showTost(Code.PERMISSION_DENIED.getTxt());
                } else {
                    TostUtil.showTost(getString(R.string.err_msg));
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
                    //添加数据之前先清除
                    dailyBeanList7.clear();
                    //添加数据
                    dailyBeanList7.addAll(weatherDailyBean.getDaily());
                    //刷新列表
                    wheather7Adapter.notifyDataSetChanged();
                    //底部动画展示
                    RecyclerViewAnimation.runLayoutAnimation(rv);
                } else if (weatherDailyBean.getCode() == Code.NO_DATA) {
                    TostUtil.showTost(Code.NO_DATA.getTxt());
                } else if (weatherDailyBean.getCode() == Code.NO_MORE_REQUESTS) {
                    TostUtil.showTost(Code.NO_MORE_REQUESTS.getTxt());
                } else if (weatherDailyBean.getCode() == Code.TOO_FAST) {
                    TostUtil.showTost(Code.TOO_FAST.getTxt());
                } else if (weatherDailyBean.getCode() == Code.PERMISSION_DENIED) {
                    TostUtil.showTost(Code.PERMISSION_DENIED.getTxt());
                } else {
                    TostUtil.showTost(getString(R.string.err_msg));
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
                    //底部动画展示
                    RecyclerViewAnimation.runLayoutAnimationRight(rvHourly);
                } else if (weatherHourlyBean.getCode() == Code.NO_DATA) {
                    TostUtil.showTost(Code.NO_DATA.getTxt());
                } else if (weatherHourlyBean.getCode() == Code.NO_MORE_REQUESTS) {
                    TostUtil.showTost(Code.NO_MORE_REQUESTS.getTxt());
                } else if (weatherHourlyBean.getCode() == Code.TOO_FAST) {
                    TostUtil.showTost(Code.TOO_FAST.getTxt());
                } else if (weatherHourlyBean.getCode() == Code.PERMISSION_DENIED) {
                    TostUtil.showTost(Code.PERMISSION_DENIED.getTxt());
                } else {
                    TostUtil.showTost(getString(R.string.err_msg));
                }

            }
        });
    }

    //今天天气状况
    public void getAirNows(String location) {
        QWeather.getAirNow(getActivity(), location, Lang.ZH_HANS, new QWeather.OnResultAirNowListener() {
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
                    rpbAqi.setFirstText(nowBean.getCategory());//空气质量描述 取值范围：优，良，轻度污染，中度污染，重度污染，严重污染
                    rpbAqi.setFirstTextSize(44f);//第一行文本的字体大小
                    rpbAqi.setSecondText(nowBean.getAqi());//空气质量值
                    rpbAqi.setSecondTextSize(64f);//第二行文本的字体大小
                    rpbAqi.setMinText("0");
                    rpbAqi.setMinTextColor(getActivity().getResources().getColor(R.color.arc_progress_color));

                    //  tvAirInfo.setText("空气" + data.getCategory());

                    tvPm10.setText(nowBean.getPm10());//PM10
                    tvPm25.setText(nowBean.getPm2p5());//PM2.5
                    tvNo2.setText(nowBean.getNo2());//二氧化氮
                    tvSo2.setText(nowBean.getSo2());//二氧化硫
                    tvO3.setText(nowBean.getO3());//臭氧
                    tvCo.setText(nowBean.getCo());//一氧化碳

                } else if (airNowBean.getCode() == Code.NO_DATA) {
                    TostUtil.showTost(Code.NO_DATA.getTxt());
                } else if (airNowBean.getCode() == Code.NO_MORE_REQUESTS) {
                    TostUtil.showTost(Code.NO_MORE_REQUESTS.getTxt());
                } else if (airNowBean.getCode() == Code.TOO_FAST) {
                    TostUtil.showTost(Code.TOO_FAST.getTxt());
                } else if (airNowBean.getCode() == Code.PERMISSION_DENIED) {
                    TostUtil.showTost(Code.PERMISSION_DENIED.getTxt());
                } else {
                    TostUtil.showTost(getString(R.string.err_msg));
                }

            }
        });

    }


    public void getDayIndices(String location) {
        List<IndicesType> indicesTypes = new ArrayList<>();
        indicesTypes.add(IndicesType.ALL);
        QWeather.getIndices1D(getActivity(), location, Lang.ZH_HANS, indicesTypes, new QWeather.OnResultIndicesListener() {
            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onSuccess(IndicesBean indicesBean) {
                if (indicesBean.getCode() == Code.OK) {
                    List<IndicesBean.DailyBean> data = indicesBean.getDailyList();

                    for (int i = 0; i < data.size(); i++) {
                        switch (data.get(i).getType()) {
                            case "5":
                                tvUv.setText("紫外线：" + data.get(i).getText());
                                break;
                            case "8":
                                tvComf.setText("舒适度：" + data.get(i).getText());
                                break;
                            case "3":
                                tvDrsg.setText("穿衣指数：" + data.get(i).getText());
                                break;
                            case "9":
                                tvFlu.setText("感冒指数：" + data.get(i).getText());
                                break;
                            case "1":
                                tvSport.setText("运动指数：" + data.get(i).getText());
                                break;
                            case "6":
                                tvTrav.setText("旅游指数：" + data.get(i).getText());
                                break;
                            case "2":
                                tvCw.setText("洗车指数：" + data.get(i).getText());
                                break;
                            case "10":
                                tvAir.setText("空气指数：" + data.get(i).getText());
                                break;
                            default:
                                break;
                        }
                    }

                } else if (indicesBean.getCode() == Code.NO_DATA) {
                    TostUtil.showTost(Code.NO_DATA.getTxt());
                } else if (indicesBean.getCode() == Code.NO_MORE_REQUESTS) {
                    TostUtil.showTost(Code.NO_MORE_REQUESTS.getTxt());
                } else if (indicesBean.getCode() == Code.TOO_FAST) {
                    TostUtil.showTost(Code.TOO_FAST.getTxt());
                } else if (indicesBean.getCode() == Code.PERMISSION_DENIED) {
                    TostUtil.showTost(Code.PERMISSION_DENIED.getTxt());
                } else {
                    TostUtil.showTost(getString(R.string.err_msg));
                }

            }
        });
    }

}

