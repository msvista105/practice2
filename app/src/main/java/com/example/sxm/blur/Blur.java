package com.example.sxm.blur;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;

import com.example.sxm.utils.LogUtils;

public class Blur {
    private static final String TAG = "Blur";

    public static Composer with(Context context) {
        return new Composer(context);
    }

    public static class Composer {
        Context context;
        BlurFactor factor;
        View blurView;

        private Composer(Context context) {
            this.context = context;
            blurView = new View(context);
            factor = new BlurFactor();
        }

        public Composer radius(int radius) {
            factor.radius = radius;
            return this;
        }

        public Composer sample(int size) {
            factor.sampleSize = size;
            return this;
        }

        public Composer color(int color) {
            factor.color = color;
            return this;
        }

        public void onto(ViewGroup target) {
            factor.width = target.getWidth();
            factor.height = target.getHeight();
            //设置blurView的宽高
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(factor.width, factor.height);
            blurView.setLayoutParams(lp);
            if (true) {
                BlurTask task = new BlurTask(target, factor, drawable -> {
                    LogUtils.d(TAG, "---- onto ---- drawable:" + (drawable == null ? "null" : "not null"));
                    addBlurView(target, drawable);
                });
                task.execute();
            }
        }

        private void addBlurView(ViewGroup target, BitmapDrawable drawable) {
            blurView.setBackground(drawable);
            target.addView(blurView);
        }
    }
}
