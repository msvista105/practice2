package com.example.sxm.concurrent;


import com.example.sxm.utils.LogUtils;

public class Poster implements Runnable {
    private static final String TAG = "Poster";
    private static final Object mLock = new Object();
    private static Poster mInstance = null;
    public static Poster getInstance(){
        if (mInstance == null) {
            //注意锁是针对类的，即使是类的不同对象， 锁也起作用
            synchronized (Poster.class){
                if(mInstance == null){
                    mInstance = new Poster();
                }
            }
        }
        return  mInstance;
    }
    @Override
    public void run() {
        LogUtils.d(TAG,"Poster:run:currentThread:"+Thread.currentThread().getName()+" --- "+this);
    }
    @Override
    public String toString(){
        return "Poster:"+hashCode();
    }
}
