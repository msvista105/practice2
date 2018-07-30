package com.example.sxm.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isChineseMobileNum(String str){
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String regex = "^(((86|0|\\+86|)1[345678]\\d{9})|(\\d[3,4]-\\d[7,8]))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
