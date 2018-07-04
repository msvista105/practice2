package com.example.sxm.practice;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sxm.provider.PracticeProvider;
import com.example.sxm.service.OtherThreadService;
import com.example.sxm.sort.BinarySearch2;
import com.example.sxm.sort.CocktailSearch;
import com.example.sxm.sort.HeapSearch;
import com.example.sxm.sort.InsertSearch;
import com.example.sxm.sort.MergeSort;
import com.example.sxm.sort.SelectionSearch;
import com.example.sxm.utils.ActivityState;
import com.example.sxm.utils.LogUtils;
import com.example.sxm.utils.StateFactory;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HMCT_MAIN";
    private static final int PERMISSION_REQUEST_CODE = 10;
    private String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private ImageView mImageView = null;
    private ImageView mImageView_2 = null;
    private ImageView mImageView_3 = null;
    private Button mServiceButton = null;
    private StateFactory mFactory = null;
    private ActivityState mActivityState = null;
    private int mNeedRequestPermissionCount = 0;
    private boolean mNeedWriteExternalStorege = false;
    private boolean mNeedReadExternalStorege = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        mImageView = findViewById(R.id.sample_image);
        mImageView_2 = findViewById(R.id.sample_image_2);
        mImageView_3 = findViewById(R.id.sample_image_3);
        mServiceButton = findViewById(R.id.service_button);

        mImageView_3.setOnClickListener(this::mContenProvider);

        mServiceButton.setOnClickListener(mServiceButtonListener);

        if (mFactory == null) {
            mFactory = new StateFactory().getInstance();
        }


//        State state = ((PracticeApplication)(this.getApplication())).getState();
        mActivityState = mFactory.getState(ActivityState.class);
        LogUtils.d(TAG, "onCreate state.name:" + mActivityState.getClass().getName() + " ---- mFactory:" + mFactory);

        int vis = getWindow().getDecorView().getSystemUiVisibility();
        vis |= (View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().getDecorView().setSystemUiVisibility(vis);
        getWindow().setNavigationBarColor(Color.RED);
        getWindow().setStatusBarColor(Color.RED);

        //hide navbar
//        hideNavbar();


        //二分法插入排序
        int[] array = new int[]{-1, 2, 4, 5, 8, 7, 6, 6, 9, 9, 6, 11, 0, -1, 34, 6, 7, 99, 333333, 10, 10, 444, 56};
        int[] array2 = new int[]{2, 4, 66, 7, 5, 8, 7, 6, 6, 9, 9, 6, 11, 0, 34, 6, 7, 99, 3, 21, 99, 12, 1, 10, 10, 444, 56};
        HeapSearch.sort(array);
//        BubbleSearch.sort(array2);
//        QuickSearch.sort(array2);
//        BinarySearch.sort(array);
        /*
        //http test
        HttpTest test = new HttpTest();
        test.startTest();

        //volley and gson
        VolleyAndGson mVolley = new VolleyAndGson(getApplicationContext());
        mVolley.useJsonRequest();
        mVolley.useImageLoader(mImageView_3);

        //OkHttpEngine
        long maxSize = 8*1024*1024;
        ResultCallback callback = new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                LogUtils.d(TAG,"ResultCallback onError:"+request.body()+" e:"+e.toString());
            }

            @Override
            public void onResponse(Response response) {
                LogUtils.d(TAG,"ResultCallback onResponse:"+response.networkResponse().toString());
            }
        };
        OkHttpEngine okHttpEngine = OkHttpEngine.getInstance();
        okHttpEngine.setCache(maxSize,getApplicationContext());
        okHttpEngine.getAsynHttp("http://www.baidu.com",callback);
        */

//        long maxSize = Runtime.getRuntime().maxMemory();//这个应该是每个app可使用的内存，每一个app对应一个runtime实例
//        LogUtils.d(TAG, "maxSize:" + maxSize);
    }

    private void hideNavbar() {
        int uiOption = getWindow().getDecorView().getSystemUiVisibility();
        uiOption |= (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE//保持view布局的稳定，如出现导航栏的时候，界面不跳变resizing等
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
        getWindow().getDecorView().setSystemUiVisibility(uiOption);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //CTS test code
        /*
        Bitmap navbar = BitmapFactory.decodeResource(getResources(), R.mipmap.sxm_3);
        Stats s = evaluateLightBarBitmap(navbar, Color.RED);
        Bitmap home = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_sysbar_home_darkk);
        findAlphaValue(home);
        createTranslucentPng(home);
        */
    }


    /////////////////////////////////////////////////
    ///CTS test code
    private static class Stats {
        int backgroundPixels;
        int iconPixels;
        int sameHueDarkPixels;
        int sameHueLightPixels;
        int unexpectedHuePixels;

        int totalPixels() {
            return backgroundPixels + iconPixels + sameHueDarkPixels
                    + sameHueLightPixels + unexpectedHuePixels;
        }

        int foregroundPixels() {
            return iconPixels + sameHueDarkPixels
                    + sameHueLightPixels + unexpectedHuePixels;
        }

        @SuppressLint("DefaultLocale")
        @Override
        public String toString() {
            return String.format("{bg=%d, ic=%d, dark=%d, light=%d, bad=%d}",
                    backgroundPixels, iconPixels, sameHueDarkPixels, sameHueLightPixels,
                    unexpectedHuePixels);
        }
    }

    @Override
    public String toString() {
        return "MainActivity{" +
                "PERMISSIONS=" + Arrays.toString(PERMISSIONS) +
                ", mImageView=" + mImageView +
                ", mImageView_2=" + mImageView_2 +
                '}';
    }

    private Stats evaluateLightBarBitmap(Bitmap bitmap, int background) {
        int iconColor = 0x99000000;
        int iconPartialColor = 0x3d000000;

        int mixedIconColor = mixSrcOver(background, iconColor);
        int mixedIconPartialColor = mixSrcOver(background, iconPartialColor);

        int[] pixels = new int[bitmap.getHeight() * bitmap.getWidth()];
        int[] pixelss = new int[bitmap.getHeight() * bitmap.getWidth()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        Stats s = new Stats();
        float eps = 0.005f;

        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i] == background) {
                s.backgroundPixels++;
                pixelss[i] = Color.RED;
                continue;
            }

            // What we expect the icons to be colored according to the spec.
            if (pixels[i] == mixedIconColor || pixels[i] == mixedIconPartialColor) {
                s.iconPixels++;
                pixelss[i] = Color.GREEN;
                continue;
            }

            // Due to anti-aliasing, there will be deviations from the ideal icon color, but it
            // should still be mostly the same hue.
            float hueDiff = Math.abs(ColorUtils.hue(background) - ColorUtils.hue(pixels[i]));
//            LogUtils.d(TAG, "hmct-pixels[" + i + "]:0x" + Integer.toHexString(pixels[i]) + " Red:" + Color.red(pixels[i]));
            if (hueDiff < eps || hueDiff > 1 - eps) {
                // .. it shouldn't be lighter than the original background though.
                if (ColorUtils.brightness(pixels[i]) > ColorUtils.brightness(background)) {
                    s.sameHueLightPixels++;
                } else {
                    s.sameHueDarkPixels++;
//                    LogUtils.d("hmct_test","pixels[i]:0x"+Integer.toHexString(pixels[i]));
                }
                pixelss[i] = Color.BLUE;
                continue;
            }
            pixelss[i] = Color.WHITE;
            s.unexpectedHuePixels++;

        }
        Bitmap newBt = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        newBt.setPixels(pixelss, 0, newBt.getWidth(), 0, 0, newBt.getWidth(), newBt.getHeight());
        mImageView.setImageBitmap(newBt);
        mImageView_2.setImageBitmap(bitmap);
        mImageView_3.setAlpha(0.95f);
        LogUtils.d(TAG, " s.iconPixels:" + s.iconPixels
                + " s.sameHueLightPixels:" + s.sameHueLightPixels
                + " s.sameHueDarkPixels:" + s.sameHueDarkPixels
                + " s.unexpectedHuePixels:" + s.unexpectedHuePixels);
        return s;
    }

    private int mixSrcOver(int background, int foreground) {
        int bgAlpha = Color.alpha(background);
        int bgRed = Color.red(background);
        int bgGreen = Color.green(background);
        int bgBlue = Color.blue(background);

        int fgAlpha = Color.alpha(foreground);
        int fgRed = Color.red(foreground);
        int fgGreen = Color.green(foreground);
        int fgBlue = Color.blue(foreground);

        return Color.argb(fgAlpha + (255 - fgAlpha) * bgAlpha / 255,
                fgRed + (255 - fgAlpha) * bgRed / 255,
                fgGreen + (255 - fgAlpha) * bgGreen / 255,
                fgBlue + (255 - fgAlpha) * bgBlue / 255);
    }

    private void findAlphaValue(Bitmap bitmap) {
        LogUtils.d(TAG, " bitmap.width:" + bitmap.getWidth() + " height:" + bitmap.getHeight());
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int all = 0;
        int circle = 0;
        for (int c : pixels) {
            int alpha = Color.alpha(c);
            if (alpha != 0) {
                all++;
//                LogUtils.d(TAG, " alpha:0x" + Integer.toHexString(alpha));
                if (alpha == 0x99) {
                    circle++;
                }
            }
        }
        LogUtils.d(TAG, " circle:" + circle + " all:" + all);
    }

    private void createTranslucentPng(Bitmap bt) {
        Bitmap newBt = Bitmap.createBitmap(bt.getWidth(), bt.getHeight(), Bitmap.Config.ARGB_8888);
        int[] pixels = new int[bt.getHeight() * bt.getWidth()];
        int[] pixelss = new int[bt.getHeight() * bt.getWidth()];
        bt.getPixels(pixels, 0, bt.getWidth(), 0, 0, bt.getWidth(), bt.getHeight());
        for (int i = 0; i < pixels.length; i++) {
            if (Color.alpha(pixels[i]) == 0) {
                pixelss[i] = 0x00000000;
            } else {
                pixelss[i] = Color.argb(/*153*/255, Color.red(pixels[i]), Color.green(pixels[i]), Color.blue(pixels[i]));
            }
        }
        newBt.setPixels(pixelss, 0, newBt.getWidth(), 0, 0, newBt.getWidth(), newBt.getHeight());
        try {
            OutputStream os = new FileOutputStream("/sdcard/hmct-test.png");
            newBt.compress(Bitmap.CompressFormat.PNG, 100, os);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Copies of non-public {@link android.graphics.Color} APIs
     */
    public static class ColorUtils {

        public static float brightness(int argb) {
            int r = (argb >> 16) & 0xFF;
            int g = (argb >> 8) & 0xFF;
            int b = argb & 0xFF;

            int V = Math.max(b, Math.max(r, g));

            return (V / 255.f);
        }

        public static float hue(int argb) {
            int r = (argb >> 16) & 0xFF;
            int g = (argb >> 8) & 0xFF;
            int b = argb & 0xFF;

            int V = Math.max(b, Math.max(r, g));
            int temp = Math.min(b, Math.min(r, g));

            float H;

            if (V == temp) {
                H = 0;
            } else {
                final float vtemp = (float) (V - temp);
                final float cr = (V - r) / vtemp;
                final float cg = (V - g) / vtemp;
                final float cb = (V - b) / vtemp;

                if (r == V) {
                    H = cb - cg;
                } else if (g == V) {
                    H = 2 + cr - cb;
                } else {
                    H = 4 + cg - cr;
                }

                H /= 6.f;
                if (H < 0) {
                    H++;
                }
            }

            return H;
        }
    }

    private View.OnClickListener mServiceButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent service = new Intent();
            service.setClass(MainActivity.this, OtherThreadService.class);
            MainActivity.this.startService(service);
        }
    };

    private void mContenProvider(View v){
        ContentValues values = new ContentValues();
        values.put("_id", "1");
        values.put("type", "NIU");
        values.put("size", 3000);
        Uri uri = getApplicationContext().getContentResolver().insert(Uri.parse("content://" + PracticeProvider.URI + "/table2"), values);
        LogUtils.d(TAG, "uri:" + uri);
    }
    /**
     * 检查权限，并申请没有的权限
     */
    private void checkPermissions() {
        LogUtils.d(TAG, "checkPermissions");
        for (int i = 0; i < PERMISSIONS.length; i++) {
            if (checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
                mNeedRequestPermissionCount++;
                if (PERMISSIONS[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    mNeedWriteExternalStorege = true;
                } else if (PERMISSIONS[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    mNeedReadExternalStorege = true;
                }
                LogUtils.d(TAG, " dont have permission :" + PERMISSIONS[i]);
            }
        }
        String[] needRequestPermissions = new String[mNeedRequestPermissionCount];
        int index = 0;
        if (mNeedReadExternalStorege) {
            needRequestPermissions[index] = Manifest.permission.READ_EXTERNAL_STORAGE;
            index++;
        }
        if (mNeedWriteExternalStorege) {
            needRequestPermissions[index] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        }
        if (mNeedReadExternalStorege || mNeedWriteExternalStorege) {
            LogUtils.d(TAG, "---- request permission ----");
            requestPermissions(needRequestPermissions, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LogUtils.d(TAG, "permission result requestcode:" + requestCode);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    LogUtils.d(TAG, "permission result failed:" + permissions[i]);
                }
            }
        }
    }
}
