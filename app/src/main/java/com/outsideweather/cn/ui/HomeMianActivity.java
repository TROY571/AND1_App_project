package com.outsideweather.cn.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.outsideweather.cn.R;
import com.outsideweather.cn.base.BaseActivity;
import com.outsideweather.cn.fragement.WeatherFragement;
import com.outsideweather.cn.fragement.NoteFragement;
/**
 * email：
 * description：
 */
public class HomeMianActivity extends BaseActivity {
    public static  void  statrHomeActivity(Context context){
        Intent intent=new Intent(context, HomeMianActivity.class);
        context.startActivity(intent);

    }
    public static HomeMianActivity instance;

    public static HomeMianActivity getInstance() {
        return instance;
    }

    private ImageView ivIcon1;
    private TextView tvTab1;
    private ImageView ivIcon2;
    private TextView tvTab2;
    private ImageView ivIcon3;
    private TextView tvTab3;

    private ImageView ivIcon4;
    private TextView tvTab4;

    private FragmentManager fm;
    private RelativeLayout contentLayout;
    private LinearLayout ttsu;
    private RelativeLayout tab1;
    private RelativeLayout tab2;
    private RelativeLayout tab3;
    private RelativeLayout tab4;
    WeatherFragement fragementf1;
    NoteFragement fragementf2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        instance=this;
        initView();
    }

    private void initView() {
        ivIcon1 = (ImageView) findViewById(R.id.iv_icon_1);
        tvTab1 = (TextView) findViewById(R.id.tv_tab1);
        ivIcon2 = (ImageView) findViewById(R.id.iv_icon_2);
        tvTab2 = (TextView) findViewById(R.id.tv_tab2);
        ivIcon3 = (ImageView) findViewById(R.id.iv_icon_3);
        tvTab3 = (TextView) findViewById(R.id.tv_tab3);
        tvTab4 = (TextView) findViewById(R.id.tv_tab4);
        contentLayout = (RelativeLayout) findViewById(R.id.content_layout);
        tab1 = (RelativeLayout) findViewById(R.id.tab1);
        tab2 = (RelativeLayout) findViewById(R.id.tab2);
        tab3 = (RelativeLayout) findViewById(R.id.tab3);
        tab4 = (RelativeLayout) findViewById(R.id.tab4);
        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                labelSelection(0);
            }
        });
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelSelection(1);
            }
        });
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    labelSelection(2);

            }
        });
        tab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelSelection(3);
            }
        });
        fm = getSupportFragmentManager();
        labelSelection(0);
    }

    private void hideFragment(Fragment fragment, FragmentTransaction ft) {
        if (fragment != null) {
            ft.hide(fragment);
        }
    }
    public void labelSelection(int position) {

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        tvTab1.setTextColor(Color.parseColor("#999999"));
        tvTab2.setTextColor(Color.parseColor("#999999"));
        tvTab3.setTextColor(Color.parseColor("#999999"));
        tvTab4.setTextColor(Color.parseColor("#999999"));
        switch (position) {
            case 0:
                tvTab1.setTextColor(Color.parseColor("#03DAC5"));
                hideFragment(fragementf2, fragmentTransaction);

                if (fragementf1 == null) {
                    fragementf1 = new WeatherFragement();
                    fragmentTransaction.add(R.id.content_layout, fragementf1);
                } else {
                    fragmentTransaction.show(fragementf1);
                }
                break;
            case 1:
                tvTab2.setTextColor(Color.parseColor("#03DAC5"));
                hideFragment(fragementf1, fragmentTransaction);
                if (fragementf2 == null) {
                    fragementf2 = new NoteFragement();
                    fragmentTransaction.add(R.id.content_layout, fragementf2);
                } else {
                    fragmentTransaction.show(fragementf2);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();

    }
    long timeBack;

    @Override
    public boolean onKeyDown(int code, KeyEvent ev) {
        if (code == KeyEvent.KEYCODE_BACK && ev.getAction() == KeyEvent.ACTION_DOWN) {
            long time = System.currentTimeMillis();

            if (time - timeBack > 1000) {
                timeBack = time;
                Toast.makeText(this,"Press once more to exit", Toast.LENGTH_LONG).show();
            } else {
                finish();
                System.gc();
            }
            return true;
        }
        return super.onKeyDown(code, ev);
    }

}
