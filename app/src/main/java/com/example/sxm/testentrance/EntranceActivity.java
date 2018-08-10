package com.example.sxm.testentrance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sxm.concurrent.Poster;
import com.example.sxm.genericity.GenericityDemo;
import com.example.sxm.practice.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class EntranceActivity extends AppCompatActivity {
    private Poster mPoster = Poster.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        TextView textview = (TextView) findViewById(R.id.spannablefoldtextview);
        textview.setText("固定的几个范围：320*480，480*800，720*1280，1080*1920等等；那么如何在同样的分辨率的显示器中增强显示清晰度呢？" +
                "亚像素的概念就油然而生了，亚像素就是把两个相邻的两个像素之间的距离再细分，再插入一些像素，这些通过程序加入的像素就是亚像素。在两个像素间插 入的像素个数是通过程序计算出来的，一般是插入两个、三个或四个");
        testPosters();
//        startTestThread();
    }

    private void startTestThread() {
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(() -> {
            new GenericityDemo().test();
        });
    }

    private void testPosters() {
        for (int i = 0; i < 6; i++) {
            ExecutorService executor = Executors.newCachedThreadPool();
            executor.execute(mPoster);
        }
    }
}
