package com.water.xiaoshuweather.util;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.water.xiaoshuweather.json.Weather;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.System.out;

/**
 * Created by water on 2017/5/6.
 */

public class HttpUtil {


    public static void sendOkhttpRequest(String url, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }


}
