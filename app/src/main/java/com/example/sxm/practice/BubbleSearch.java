package com.example.sxm.practice;

import com.example.sxm.utils.LogUtils;

public class BubbleSearch {
    private static final String TAG = "BubbleSearch";
    private static final boolean DEBUG = true;
    public static void sort(int[] array){
        int size = array.length;
        for (int i = size - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        if (DEBUG){
            for (int i = 0; i < size; i++) {
                LogUtils.d(TAG,"BubbleSearch sort array[i]:"+array[i]);
            }
        }
    }
}
