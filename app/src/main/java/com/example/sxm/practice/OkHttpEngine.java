package com.example.sxm.practice;

import android.content.Context;
import android.os.Handler;
import android.service.carrier.CarrierMessagingService;
import android.support.annotation.NonNull;

import com.example.sxm.utils.LogUtils;
import com.example.sxm.utils.ResultCallback;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpEngine {
    private static final String TAG = "OkHttpEngine";
    private static final Object mLock = new Object();
    private OkHttpClient mOkHttpClient;
    //线程安全？？？volatile保证指令不重排，这样在创建mInstance的时候会按照分配内存、初始化对象、返回内存地址来实现
    private static volatile OkHttpEngine mInstance ;
    private Handler mHandler;
//    public static OkHttpEngine getInstance(){//TODO:单例模式的几种写法？？资源消耗？？线程安全？？
//        if (mInstance == null){
//            synchronized (OkHttpEngine.class){
//                if(mInstance == null){
//                    mInstance = new OkHttpEngine();
//                }
//            }
//        }
//        return mInstance;
//    }
    public static OkHttpEngine getInstance(){
        return OkHttpEngineHolder.mInstance;
    }
    public static OkHttpEngine getInstance2() {
        if(mInstance == null){
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new OkHttpEngine();
                }
            }
        }
        return mInstance;
    }
    private OkHttpEngine(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS);
        mOkHttpClient = builder.build();
        mHandler = new Handler();
    }
    public OkHttpEngine setCache (long maxsize, Context context){
        //int maxSize = 8*1024*1024;//字节
        File cacheFile = context.getExternalCacheDir();
        assert cacheFile != null;
        File absoluteCacheFile = cacheFile.getAbsoluteFile();
        mOkHttpClient = mOkHttpClient.newBuilder()
                .cache(new Cache(absoluteCacheFile,maxsize))
                .build();
        LogUtils.d(TAG,"setCache absoluteCacheFile:"+absoluteCacheFile.toString()
                +" mOkHttpClient:"+mOkHttpClient);
        return mInstance;
    }
    public void getAsynHttp(String url, @NonNull ResultCallback callback){
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        dealResult(call,callback);
    }
    private void dealResult(Call call, final ResultCallback callback){
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedCallBack(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendSuccessCallback(call,response);
            }

            private void sendFailedCallBack(Call call,Exception e){
                mHandler.post(()->{
                    callback.onError(call.request(),e);
                });
            }
            private void sendSuccessCallback(Call call,Response response){
                mHandler.post(()->{
                   callback.onResponse(response);
                });
            }
        });
    }

    private static class OkHttpEngineHolder{
        public static final OkHttpEngine mInstance = new OkHttpEngine();
    }

}
