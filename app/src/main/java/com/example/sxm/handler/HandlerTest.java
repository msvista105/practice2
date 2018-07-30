package com.example.sxm.handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sxm.practice.R;
import com.example.sxm.utils.LogUtils;

public class HandlerTest extends AppCompatActivity {
    private static final String TAG = HandlerTest.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);
        handleMessage();
    }

    private void handleMessage() {
        Looper.prepare();
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                LogUtils.d(TAG,"handleMessage msg.what:"+msg.what+" obj:"+(String)msg.obj+" ------currentThread:"+Thread.currentThread().getName());
            }
        };

        for(int i=0;i<20;i++){
            final int id = i;
            new Thread(){
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = id;
                    message.obj = Thread.currentThread().getName();
                    handler.sendMessage(message);
                }
            }.start();
        }
        Looper.loop();
    }
}
