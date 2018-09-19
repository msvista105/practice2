package com.example.sxm.crashcaught;

import android.content.Context;
import android.os.Process;
import android.widget.Toast;

import com.example.sxm.handler.Looper;
import com.example.sxm.utils.LogUtils;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private Context mContext;
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LogUtils.d(TAG,"---- uncaughtException ----");
        LogUtils.d(TAG,"---- thread is "+t.toString()+"\n exception is "+e.toString()+" ----");
        Process.killProcess(Process.myPid());//退出进程
//        handleException(t,e);
    }

    private boolean handleException(final Thread t,Throwable e){
        if (e == null) return false;
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext,"PRACTICE is crash! Thread is "+t.toString(),Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        saveEx2File();
        return false;
    }

    private void saveEx2File() {
        //
    }

    public void init(Context context){
        mContext = context;
    }
}
