package com.example.sxm.practice;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sxm.service.OtherThreadService;
import com.example.sxm.utils.LogUtils;
import com.example.sxm.utils.State;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HMCT_MAIN";
    private String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private ImageView mImageView = null;
    private ImageView mImageView_2 = null;
    private ImageView mImageView_3 = null;
    private Button mServiceButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.sample_image);
        mImageView_2 = findViewById(R.id.sample_image_2);
        mImageView_3 = findViewById(R.id.sample_image_3);
        mServiceButton = findViewById(R.id.service_button);

        mServiceButton.setOnClickListener(mServiceButtonListener);

        State state = ((PracticeApplication)(this.getApplication())).getState();
        LogUtils.d(TAG,"onCreate state:"+state);

        int vis = getWindow().getDecorView().getSystemUiVisibility();
        vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        getWindow().getDecorView().setSystemUiVisibility(vis);
        getWindow().setNavigationBarColor(Color.RED);

        //二分法插入排序
        int[] array = new int[]{2, 4, 5, 8, 7, 6, 6, 9, 9, 6, 11, 0, 34, 6, 7, 99, 333333, 10, 10, 444, 56};
        int[] array2 = new int[]{2, 4, 66, 7, 5, 8, 7, 6, 6, 9, 9, 6, 11, 0, 34, 6, 7, 99, 3, 21, 99, 12, 1, 10, 10, 444, 56};
        QuickSearch.sort(array2);
//        BinarySearch.sort(array);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //CTS test code
        Bitmap navbar = BitmapFactory.decodeResource(getResources(), R.mipmap.lightstatustestttt);
        Stats s = evaluateLightBarBitmap(navbar, Color.RED /* background */);
        Bitmap home = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_sysbar_home_darkk);
        findAlphaValue(home);
        createTranslucentPng(home);
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

    public void requestPermission() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        PERMISSIONS, 1234);
            }
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
    private View.OnClickListener mServiceButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent service = new Intent();
            service.setClass(MainActivity.this,OtherThreadService.class);
            MainActivity.this.startService(service);
        }
    };


}
