package com.outsideweather.cn.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
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
public class MianActivity extends BaseActivity {
    public static  void  statrHomeActivity(Context context){
        Intent intent=new Intent(context, MianActivity.class);
        context.startActivity(intent);

    }
    public static MianActivity instance;

    public static MianActivity getInstance() {
        return instance;
    }

    private TextView tvTab1;
    private TextView tvTab2;
    private FragmentManager fm;
    private RelativeLayout tab1;
    private RelativeLayout tab2;
    WeatherFragement weatherFragement;
    NoteFragement noteFragement;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        instance=this;
        initView();
    }

    private void initView() {
        tvTab1 = (TextView) findViewById(R.id.tv_tab1);
        tvTab2 = (TextView) findViewById(R.id.tv_tab2);
        tab1 = (RelativeLayout) findViewById(R.id.tab1);
        tab2 = (RelativeLayout) findViewById(R.id.tab2);
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
        switch (position) {
            case 0:
                tvTab1.setTextColor(Color.parseColor("#03DAC5"));
                hideFragment(noteFragement, fragmentTransaction);

                if (weatherFragement == null) {
                    weatherFragement = new WeatherFragement();
                    fragmentTransaction.add(R.id.content_layout, weatherFragement);
                } else {
                    fragmentTransaction.show(weatherFragement);
                }
                break;
            case 1:
                tvTab2.setTextColor(Color.parseColor("#03DAC5"));
                hideFragment(weatherFragement, fragmentTransaction);
                if (noteFragement == null) {
                    noteFragement = new NoteFragement();
                    fragmentTransaction.add(R.id.content_layout, noteFragement);
                } else {
                    fragmentTransaction.show(noteFragement);
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
