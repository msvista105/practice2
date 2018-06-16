package com.example.sxm.practice;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.example.sxm.utils.LogUtils;
import com.example.sxm.utils.MD5Translate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
            String str = MD5Translate.getMD5Str(url);
            return mCache.get(str);
        }
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        synchronized (mCache) {
            String str = MD5Translate.getMD5Str(url);
            if (mCache.get(str) == null) {
                mCache.put(str, bitmap);
            }
        }
    }
}
