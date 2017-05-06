package com.water.xiaoshuweather;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.water.xiaoshuweather.util.LogUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Fragment weatherfragment;
    private Fragment picturefragment;
    private Fragment infofragment;

    private RelativeLayout WeatherTab;
    private RelativeLayout PictureTab;
    private RelativeLayout InfoTab;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initView();
        setChoiceItem(0);
    }

    private void initView() {
        WeatherTab = (RelativeLayout) findViewById(R.id.weather_tab);
        PictureTab = (RelativeLayout) findViewById(R.id.picture_tab);
        InfoTab = (RelativeLayout) findViewById(R.id.info_tab);

        WeatherTab.setOnClickListener(MainActivity.this);
        PictureTab.setOnClickListener(MainActivity.this);
        InfoTab.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weather_tab:
                LogUtil.d("MainActivity", "点了第一个");
                setChoiceItem(0);
                break;
            case R.id.picture_tab:
                LogUtil.d("MainActivity", "点了第二个");
                setChoiceItem(1);
                break;
            case R.id.info_tab:
                LogUtil.d("MainActivity", "点了第三个");
                setChoiceItem(2);
                break;
            default:
                break;
        }
    }

    private void setChoiceItem(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (index) {
            case 0:
                LogUtil.d("MainActivity", "你点了天气");
                if (weatherfragment == null) {
                    weatherfragment = new WeatherFragment();
                    fragmentTransaction.add(R.id.content, weatherfragment);
                } else {
                    fragmentTransaction.show(weatherfragment);
                }
                break;

            case 1:
                LogUtil.d("MainActivity", "你点了时景");
                if (picturefragment == null) {
                    picturefragment = new PictureFragment();
                    fragmentTransaction.add(R.id.content, picturefragment);
                } else {
                    fragmentTransaction.show(picturefragment);
                }
                break;

            case 2:
                LogUtil.d("MainActivity", "你点了个人");
                if (infofragment == null) {
                    infofragment = new InfoFragment();
                    fragmentTransaction.add(R.id.content, infofragment);
                } else {
                    fragmentTransaction.show(infofragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (weatherfragment != null) {
            fragmentTransaction.hide(weatherfragment);
        }
        if (picturefragment != null) {
            fragmentTransaction.hide(picturefragment);
        }
        if (infofragment != null) {
            fragmentTransaction.hide(infofragment);
        }
    }
}
