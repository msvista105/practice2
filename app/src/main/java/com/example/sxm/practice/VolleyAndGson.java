package com.example.sxm.practice;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sxm.utils.LogUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

public class VolleyAndGson {
    private static final String TAG = "VolleyAndGson";
    private Context mContext;
    private RequestQueue mQueue = null;

    public VolleyAndGson(Context context) {
        mContext = context;
        mQueue = Volley.newRequestQueue(mContext);
    }

    public void useJsonRequest() {
        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://www.weather.com.cn/data/cityinfo/101010100.html ", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtils.d(TAG, "onResponse response:" + response.toString());
                        Article mArticle = new Gson().fromJson(response.toString(), Article.class);
                        Article.weatherinfo weatherinfos = mArticle.getWeatherinfo();
                        LogUtils.d(TAG, "------------------------------------------------------------------------------------");
                        LogUtils.d(TAG, "mWeatherinfos:" + ((weatherinfos == null) ? "null" : weatherinfos));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtils.d(TAG, "onErrorResponse error:" + error.getMessage());
                    }
                });
        mQueue.add(mJsonObjectRequest);
    }

    public void useImageLoader(ImageView imageView){
        ImageLoader imageLoader = new ImageLoader(mQueue,new BitmapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,R.mipmap.ic_lutou,R.mipmap.ic_launcher_round);
        imageLoader.get("https://ss.csdn.net/p?https://mmbiz.qpic.cn/mmbiz_jpg/ptp8P184xjxDRar7jGQ5YVE7bYnCFv03ssicSoLCHQoypl700BefU3sjs6yUSqWAxn0PlN8Y7hINPGWkakUXkxw/640?wx_fmt=jpeg",listener);
    }
}
