package com.example.sxm.utils;

public class ByteToHex {
    public static String getHexStr(byte[] bytes) {
        String hexStr = "";
        for (int i = 0; i < bytes.length; i++) {
            String stmpStr = Integer.toHexString(bytes[i] & 0xFF);
//            String stmpStr = Integer.toHexString(bytes[i]);
            if (stmpStr.length() == 1) {
                hexStr += ("0" + stmpStr);
            } else {
                hexStr += stmpStr;
            }
            hexStr += ":";
        }
        return hexStr;
    }
}
