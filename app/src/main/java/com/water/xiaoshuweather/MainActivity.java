package com.water.xiaoshuweather;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.water.xiaoshuweather.json.Weather;
import com.water.xiaoshuweather.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public LocationClient mLocationClient;
    public static String locCity;
    private DrawerLayout drawerLayout;
    private ImageView imageView;

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
        mLocationClient = new LocationClient(MainActivity.this);
        mLocationClient.registerLocationListener(new MyLocationListener());
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }else {
            requestLocation();
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initView();
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setScanSpan(5*60*1000);
        locationClientOption.setIsNeedAddress(true);
        mLocationClient.setLocOption(locationClientOption);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "同意所有权限才能定位哦", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            locCity = location.getCity();
            Log.d("MainActivity", "百度返回的城市是：" + locCity);
            ChooseAreaFragment.addCityName(locCity.substring(0, locCity.length() - 1));

            setChoiceItem(0);
        }

        @Override
        public void onConnectHotSpotMessage(String string, int n) {

        }

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
            case R.id.title_nav:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
    }

    private void setChoiceItem(int index) {
        fragmentManager = getSupportFragmentManager();
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
        fragmentTransaction.commitAllowingStateLoss();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
