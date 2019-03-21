package com.example.sxm.eventbus;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;

import com.example.sxm.practice.R;
import com.example.sxm.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusActivity extends AppCompatActivity {
    private static final String TAG = "EventBusActivity";
    private Handler mSubHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LogUtils.d(TAG,"main thread,tid:"+Process.myPid());

        HandlerThread subThread = new HandlerThread("sub_thread_01");
        subThread.start();
        Handler subHander = new Handler(subThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1314:
                        int tid = Process.myTid();
                        LogUtils.d(TAG,"subHandler receive Message(1314),tid:"+tid);
                        break;
                    default:
                        LogUtils.d(TAG,"subHandler receive Message(1314)");
                }
            }
        };
        subHander.sendMessage(subHander.obtainMessage(1314));
        subHander.post(new Runnable() {
            @Override
            public void run() {
                int tid = Process.myTid();
                LogUtils.d(TAG,"subHandler Runnable ,tid:"+tid);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        findViewById(R.id.button_eventbus).setOnClickListener((v)->{
            //启动新线程发送消息
//            new Thread("thread_sxm_1"){
//                @Override
//                public void run() {
//                    EventBus.getDefault().post(new MessageEvent("MessageEvent_Test_OtherThread"));
//                }
//            }.start();
            //在主线程发送消息
            EventBus.getDefault().post(new MessageEvent("MessageEvent_Test_MainThread"));
            LogUtils.d(TAG,"Is MainThread blocking ? Test");
        });
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * ThreadMode.POSTING 订阅者方法将在发布事件所在的线程中被调用。这是 默认的线程模式。事件的传递是同步的，一旦发布事件，所有该模式的订阅者方法都将被调用。这种线程模式意味着最少的性能开销，因为它避免了线程的切换。因此，对于不要求是主线程并且耗时很短的简单任务推荐使用该模式。使用该模式的订阅者方法应该快速返回，以避免阻塞发布事件的线程，这可能是主线程。
     ThreadMode.MAIN 订阅者方法将在主线程（UI线程）中被调用。会阻塞发布进程，等待发布调用返回，才会执行下面的语句。
     ThreadMode.MAIN_ORDERED 订阅者方法将在主线程（UI线程）中被调用。因此，可以在该模式的订阅者方法中直接更新UI界面。事件将先进入队列然后才发送给订阅者，所以发布事件的调用将立即返回。这使得事件的处理保持严格的串行顺序。使用该模式的订阅者方法必须快速返回，以避免阻塞主线程。
     ThreadMode.BACKGROUND 订阅者方法将在后台线程中被调用。如果发布事件的线程不是主线程，那么订阅者方法将直接在该线程中被调用。如果发布事件的线程是主线程，那么将使用一个单独的后台线程，该线程将按顺序发送所有的事件。使用该模式的订阅者方法应该快速返回，以避免阻塞后台线程。
     ThreadMode.ASYNC 订阅者方法将在一个单独的线程中被调用。因此，发布事件的调用将立即返回。如果订阅者方法的执行需要一些时间，例如网络访问，那么就应该使用该模式。避免触发大量的长时间运行的订阅者方法，以限制并发线程的数量。EventBus使用了一个线程池来有效地重用已经完成调用订阅者方法的线程。
     * */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onMessageEvent(MessageEvent event) {
        LogUtils.d(TAG,"onMessageEvent event.message:"+event.getMessage()+" thread:"+Thread.currentThread().getName());
        for(int i=0;i<200000000;i++){
            Math.atan(120f);
        }
        LogUtils.d(TAG,"onMessageEvent end");
    }

}
