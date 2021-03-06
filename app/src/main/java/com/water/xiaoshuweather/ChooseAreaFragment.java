package com.water.xiaoshuweather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.water.xiaoshuweather.adapter.CityAdapter;
import com.water.xiaoshuweather.db.ChinaCity;
import com.water.xiaoshuweather.entity.City;
import com.water.xiaoshuweather.util.HttpUtil;
import com.water.xiaoshuweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by water on 2017/5/6.
 */


public class ChooseAreaFragment extends Fragment {

    private TextView edit_city;
    private TextView add_city;
    public static HashSet<String> cityName = new HashSet<>();
    public static List<City> cityList = new ArrayList<>();

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.choose_area, container, false);
        edit_city = (TextView) view.findViewById(R.id.edit);
        add_city = (TextView) view.findViewById(R.id.add);
        addCities(new City( "上海", R.drawable.tab_picture, "10℃/20℃"));
        CityAdapter adapter = new CityAdapter(getContext(), R.layout.city_item, cityList);
        ListView listView = (ListView) view.findViewById(R.id.city_list);
        listView.setAdapter(adapter);
        return view;
    }

    public static void addCities(City city) {
        cityList.add(city);
    }

    public static void addCityName(String city) {
        cityName.add(city);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        add_city.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String address = "https://cdn.heweather.com/china-city-list.json";
                /*
                测试数据库是否有数据，有数据就直接进入，不然就加载数据
                 */
                ChinaCity chinaCityList = DataSupport.findFirst(ChinaCity.class);
                if (chinaCityList == null) {
                    showProgressDialog();
                    HttpUtil.sendOkhttpRequest(address, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    closeProgressDialog();
                                    Toast.makeText(getActivity(), "加载失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseText = response.body().string();
                            boolean result = Utility.handleCityData(responseText);
                            if (result) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        closeProgressDialog();
                                        Intent intent = new Intent(getActivity(), SelectAreaActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                Looper.prepare();
                                Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    });
                }else {
                    Intent intent = new Intent(getActivity(), SelectAreaActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * 显示对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("正在加载城市数据,请稍等几十秒...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭对话框
     */
    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
