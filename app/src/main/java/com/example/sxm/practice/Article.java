package com.example.sxm.practice;

import java.util.ArrayList;
import java.util.List;

public class Article {
    private static final String TAG = "Article";
    //    private String desc;
//    private String status;
    private weatherinfo weatherinfo;

    public weatherinfo getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(weatherinfo weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    public class weatherinfo {
        private String city;
        private String cityid;
        private String weather;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getCitiId() {
            return cityid;
        }

        public void setCitiId(String citiId) {
            this.cityid = citiId;
        }

        @Override
        public String toString() {
            return "weather:city:"+city+"  cityid:"+cityid+" weather:"+weather;
        }
    }
}
