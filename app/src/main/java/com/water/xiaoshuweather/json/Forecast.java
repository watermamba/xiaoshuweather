package com.water.xiaoshuweather.json;

/**
 * Created by water on 2017/5/11.
 */

public class Forecast {

    public Cond cond;

    public class Cond{
        public String code_n;
        public String code_d;
        public String txt_d;
        public String txt_n;
    }

    public String date;
    public String hum;

    public Tmp tmp;

    public class Tmp {
        public String max;
        public String min;
    }

    public Wind wind;

    public class Wind {
        public String dir;
        public String sc;
    }

}
