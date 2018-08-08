package com.example.sxm.testentrance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
