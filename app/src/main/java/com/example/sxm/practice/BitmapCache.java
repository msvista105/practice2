package com.example.sxm.practice;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * 内存缓存
 */

public class BitmapCache implements ImageCache {
    private static final String TAG = "BitmapCache";
    private static final int MAX_SIZE = 8 * 1024 * 1024;//8M
    private LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(MAX_SIZE) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight();
        }
    };

    @Override
    public Bitmap getBitmap(String url) {
        synchronized (mCache) {
            return mCache.get(url);
        }
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        synchronized (mCache) {
            if (mCache.get(url) == null) {
                mCache.put(url, bitmap);
            }
        }
    }
}
