package com.water.xiaoshuweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.water.xiaoshuweather.db.ChinaCity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchCity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private List<String> datalist = new ArrayList<>();
    private EditText editCity;
    private ListView cityList;
    private TextView cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        editCity = (EditText) findViewById(R.id.edit_city_2);
        cityList = (ListView) findViewById(R.id.city_listview);
        cancel = (TextView) findViewById(R.id.cancel_edit);
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item_1, datalist);
        cityList.setAdapter(adapter);
        editCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                List<ChinaCity> cities = queryCities(text);
                if (cities.size() > 0) {
                    datalist.clear();
                    for (ChinaCity chinaCity : cities) {
                        datalist.add(chinaCity.getCityZh()  + "," + chinaCity.getProvinceZh() + "," + chinaCity.getCountryZh());
                    }
                    adapter.notifyDataSetChanged();
                    cityList.setSelection(0);
                }
            }
        });
    }
    private List<ChinaCity> queryCities(String text) {
        List<ChinaCity> city = DataSupport.select("cityZh", "leaderZh", "provinceZh", "countryZh").where("cityZh = ?", text).find(ChinaCity.class);
        return city;
    }


}
