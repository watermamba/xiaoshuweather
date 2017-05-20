package com.water.xiaoshuweather.json;

/**
 * Created by water on 2017/5/11.
 */

public class Now {

    public Cond cond;

    public class Cond {
        public String code;
        public String txt;
    }


    public String hum;

    public String tmp;

    public Wind wind;
    public class Wind {
        public String dir;
        public String sc;
    }
}
