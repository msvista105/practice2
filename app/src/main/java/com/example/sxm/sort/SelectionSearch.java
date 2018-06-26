package com.example.sxm.sort;

import android.util.Log;
/**
 * 选择排序，不稳定
 * */
public class SelectionSearch {
    private static final String TAG = "SelectionSearch";
    private static final boolean DEBUG = true;

    public static void sort(int[] array) {
        int size = array.length;

        for (int i = 0; i < size; i++) {
            int index = i;
            int mix = array[i];
            for (int j = i; j <= size - 1; j++) {
                if (array[j] < mix) {
                    index = j;
                    mix = array[j];
                }
            }
            //swap
            int temp = array[i];//只是最小值跟已排好队列的末尾交换
            array[i] = mix;
            array[index] = temp;
        }
        if (DEBUG) {
            for (int i = 0; i < size; i++) {
                Log.d(TAG, String.format("SelectionSearch sort array[%d]:%d", i, array[i]));
            }
        }
    }
}
