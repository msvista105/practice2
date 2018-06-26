package com.example.sxm.sort;

import android.util.Log;

public class InsertSearch {
    private static final String TAG = "InsertSearch";
    private static final boolean DEBUG = true;

    public static void sort(int[] array) {
        int size = array.length;
        for (int i = 1; i < size; i++) {
            int value = array[i];
            int index = i;
            while (index >= 1 && value < array[index - 1]) {
                array[index] = array[index - 1];
                index--;
            }
            array[index] = value;
        }
        if (DEBUG) {
            for (int i = 0; i < size; i++) {
                Log.d(TAG, String.format("InsertSearch sort array[%d]:%d", i, array[i]));
            }
        }
    }
}
