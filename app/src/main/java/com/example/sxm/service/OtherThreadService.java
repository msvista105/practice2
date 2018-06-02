package com.example.sxm.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.sxm.practice.PracticeApplication;
import com.example.sxm.utils.State;

public class OtherThreadService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        State state = ((PracticeApplication)(this.getApplication())).getState();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
