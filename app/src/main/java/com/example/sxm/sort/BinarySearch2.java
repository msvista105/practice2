package com.example.sxm.sort;

import android.util.Log;

public class BinarySearch2 {
    private static final String TAG = "BinarySearch2";
    private static final boolean DEBUG = true;

    public static void sort(int[] array) {
        int size = array.length;
        for (int i = 1; i < size; i++) {
            int left = 0;
            int right = i - 1;
            int value = array[i];
            while (left <= right) {
                int middle = (left + right) / 2;
                if (array[middle] > value) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            }
            //TODO:边界值的选择
            for (int j = i - 1; j >= left; j--) {
                array[j + 1] = array[j];
            }
            array[left] = value;
        }
        if (DEBUG) {
            for (int i = 0; i < size; i++) {
                Log.d(TAG, String.format("BinarySearch2 sort array[%d]:%d", i, array[i]));
            }
        }
    }
}
