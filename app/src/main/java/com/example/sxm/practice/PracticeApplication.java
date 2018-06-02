package com.example.sxm.practice;

import android.app.Application;

import com.example.sxm.utils.LogUtils;
import com.example.sxm.utils.State;

public class PracticeApplication extends Application {
    private static final String TAG = "PracticeApplication";
    private State mState = null;
    @Override
    public void onCreate() {
        LogUtils.d(TAG,"onCreate");
        mState = new State();
        super.onCreate();
    }
    public State getState(){
        return mState;
    }
}
