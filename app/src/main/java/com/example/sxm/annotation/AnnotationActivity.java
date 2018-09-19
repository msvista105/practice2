package com.example.sxm.annotation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.sxm.practice.R;
import com.example.sxm.regex.RegexTest;
import com.example.sxm.utils.LogUtils;

import java.lang.reflect.Method;

public class AnnotationActivity extends AppCompatActivity {
    private static final String TAG = "AnnotationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Regex test
        RegexTest.testRegex();
    }

    @Override
    protected void onResume() {
        super.onResume();
        processDemo(Demo.class.getName());
        //hmct-test ,imitate crash
        throw new RuntimeException();
    }

    private void processDemo(String clsName){
        LogUtils.d(TAG,"processDemo clsName:"+clsName);
        Class target = null;
        Class annotation = MyTag.class;
        try {
            target = Class.forName(clsName);
            for (Method m:target.getDeclaredMethods()){
                if(m.isAnnotationPresent(annotation)){
                    LogUtils.d(TAG,String.format("processDemo %s annotated by %s",m.getName(),annotation));
                }else {
                    LogUtils.d(TAG,String.format("processDemo %s  not annotated by %s",m.getName(),annotation));
                }
            }
        }catch (Exception e){}
    }

}
