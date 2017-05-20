package com.water.xiaoshuweather.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by water on 2017/5/11.
 */

public class Suggestion {

    public Air air;
    public class Air {
        public String brf;
        public String txt;
    }

    public Comf comf;
    public class Comf {
        public String brf;
        public String txt;
    }

    public Drsg drsg;
    public class Drsg {
        public String brf;
        public String txt;
    }

    public Flu flu;
    public class Flu {
        public String brf;
        public String txt;
    }

    public Sport sport;
    public class Sport {
        public String brf;
        public String txt;
    }

    public Uv uv;
    public class Uv {
        public String brf;
        public String txt;
    }

}
