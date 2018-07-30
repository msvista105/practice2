package com.example.sxm.handler;

import android.util.Log;

import com.example.sxm.utils.LogUtils;

public class Handler {
    private static final String TAG = Handler.class.getSimpleName();
    private MessageQueue mQueues = null;
    private Looper mLooper = null;

    public Handler() {
        mLooper = Looper.myLooper();
        mQueues = mLooper.mQueue;
    }

    public void sendMessage(Message msg) {
        msg.target = this;
        mQueues.enqueueMessage(msg);
    }

    public void handleMessage(Message msg){}

    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }

}
