package com.example.sxm.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.sxm.practice.PracticeApplication;
import com.example.sxm.utils.ActivityState;
import com.example.sxm.utils.LogUtils;
import com.example.sxm.utils.ServiceState;
import com.example.sxm.utils.State;
import com.example.sxm.utils.StateFactory;

public class OtherThreadService extends Service {
    private static final String TAG = "OtherThreadService";
    private StateFactory mFactory = null;
    @Override
    public void onCreate() {
        super.onCreate();
        if(mFactory == null){
            mFactory = new StateFactory().getInstance();
        }


//        State state = ((PracticeApplication)(this.getApplication())).getState();
        ServiceState state = mFactory.getState(ServiceState.class);
        LogUtils.d(TAG,"onCreate state.name:"+state.getClass().getName()+" ---- mFactory:"+mFactory);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
