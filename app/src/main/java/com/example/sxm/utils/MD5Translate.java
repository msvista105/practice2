package com.example.sxm.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Translate {
    private static final String TAG = "MD5Translate";

    public static String getMD5Str(String url) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = url.getBytes();
            byte[] digest1 = digest.digest(bytes);
//            String md5Str = digest.toString();
            String md5Str = ByteToHex.getHexStr(bytes);
            LogUtils.d(TAG, "getMD5Str md5Str:" + md5Str);
            return md5Str;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
