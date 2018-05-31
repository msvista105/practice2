package com.example.sxm.practice;

import com.example.sxm.utils.LogUtils;

/**
 * 希尔排序
 * */
public class ShellSearch {
    private static final String TAG = "ShellSearch";
    public static void sort(int[] array){
        int length = array.length;
        //获取合适的步长
        int n = 0;
        while(n <= length){
            n = n*3+1;
        }
        LogUtils.d(TAG," length:"+length);
        while(n >= 1){
            LogUtils.d(TAG,"---------------- n:"+n);
            for(int i=n;i<length;i++){
                int index = i-n;
                int temp = array[i];
                while(index >= 0 && array[index] > temp){//TODO:
                    array[index+n] = array[index];
                    index = index - n;
                }
                array[index+n] = temp;
            }
            n = (n-1)/3;
        }
        LogUtils.d(TAG,"002 length:"+length);
        for (int i = 0; i < length; i++) {
            LogUtils.d(TAG, " i:"+i+" -- " + array[i] + " -- ");
        }
    }
}
