package com.example.sxm.sort;

import android.util.Log;

import com.example.sxm.utils.LogUtils;

/**
 * 二分插入排序
 */

public class BinarySearch {
    private static final boolean DEBUG = true;
    private static final String TAG = "BinarySearch";

    public static void sort(int[] array) {
        int length = array.length;
        for (int i = 1; i < length; i++) {
            int index = getIndex(array, 0, i - 1);
            int temp = array[i];
            for (int j = i - 1; j >= index; j--) {//注意边界值
                array[j + 1] = array[j];
            }
            array[index] = temp;
        }
        if (DEBUG) {
            for (int i = 0; i < length; i++) {
                Log.d(TAG, String.format("BinarySearch sort array[%d]:%d", i, array[i]));
            }
        }
    }

    private static int getIndex(int[] array, int start1, int end1) {
        int start = start1;
        int end = end1;
        int currentValue = array[end + 1];
        while (start <= end) {//TODO:防止进入死循环
            int middleIndex = (start + end) / 2;
            if (array[middleIndex] > currentValue) {
                end = middleIndex - 1;
            } else {
                start = middleIndex + 1;
            }
        }
        return start;
    }
}
