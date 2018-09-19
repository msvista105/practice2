package com.example.sxm.practice;

import android.app.Application;
import android.content.Context;

import com.example.sxm.crashcaught.CrashHandler;
import com.example.sxm.utils.LogUtils;
import com.example.sxm.utils.State;

public class PracticeApplication extends Application {
    private static final String TAG = "PracticeApplication";
    private Context mContext = null;
//    private State mState = null;
    @Override
    public void onCreate() {
        mContext = getApplicationContext();
        LogUtils.d(TAG,"onCreate");
        //crash捕获
        CrashHandler crashHandler = new CrashHandler();
        crashHandler.init(mContext);
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
//        mState = new State();
        super.onCreate();
    }
//    public State getState(){
//        return mState;
//    }
}
