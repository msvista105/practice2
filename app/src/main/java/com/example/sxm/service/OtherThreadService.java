package com.example.sxm.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.sxm.practice.PracticeApplication;
import com.example.sxm.utils.LogUtils;
import com.example.sxm.utils.State;

public class OtherThreadService extends Service {
    private static final String TAG = "OtherThreadService";
    @Override
    public void onCreate() {
        super.onCreate();
        State state = ((PracticeApplication)(this.getApplication())).getState();
        LogUtils.d(TAG,"onCreate state:"+state);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
