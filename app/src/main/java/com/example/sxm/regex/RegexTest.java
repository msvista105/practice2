package com.example.sxm.regex;

import com.example.sxm.utils.LogUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    private static final String TAG = "RegexTest";
    private static volatile RegexTest mInstance = new RegexTest();

    public static void testRegex() {
        String regex = "(\\bT[a-z]*\\b).*(\\bh\\w+?\\b)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("This is a happy thing ,haha!");
        while(matcher.find()){
//            matcher.group();
            LogUtils.d(TAG,"matcher:"+matcher.group());
        }
    }
}
