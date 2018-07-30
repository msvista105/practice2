package com.example.sxm.handler;


public class Looper {
    private static ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    public MessageQueue mQueue;

    private Looper() {
        mQueue = new MessageQueue();
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public static void prepare() {
        if (sThreadLocal.get() == null) {
            sThreadLocal.set(new Looper());
        } else {
            throw new RuntimeException("only one looper per thread");
        }
    }

    public static void loop() {
        MessageQueue queue = sThreadLocal.get().mQueue;
        new Thread(){
            @Override
            public void run() {
                for (; ; ) {
                    Message msg = queue.next();
                    if (msg != null) {
                        msg.target.dispatchMessage(msg);
                    }
                }
            }
        }.start();
    }
}
