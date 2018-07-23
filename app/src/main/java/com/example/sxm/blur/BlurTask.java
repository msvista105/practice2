package com.example.sxm.blur;


import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class BlurTask {
    private Resources res;
    private ViewGroup target;
    private BlurFactor factor;
    private Callback callback;
    private ExecutorService THREAD_POOL = Executors.newCachedThreadPool();

    interface Callback {
        void done(BitmapDrawable drawable);
    }

    public BlurTask(ViewGroup target, BlurFactor factor, @NonNull Callback callback) {
        this.res = target.getResources();
        this.target = target;
        this.factor = factor;
        this.callback = callback;
    }

    public void execute() {
        THREAD_POOL.execute(() -> {
            BitmapDrawable drawable = new BitmapDrawable(res, BlurCreator.of(factor, target));
            //需要放到主线程中运行，防止出现类似操作子线程不能操作UI的异常
            new Handler(Looper.getMainLooper()).post(() -> {
                callback.done(drawable);
            });
        });
    }
}
