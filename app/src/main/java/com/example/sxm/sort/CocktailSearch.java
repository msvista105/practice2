package com.example.sxm.sort;

import android.util.Log;

public class CocktailSearch {
    private static final boolean DEBUG = true;
    private static final String TAG = "CocktailSearch";

    public static void sort(int[] array) {
        int size = array.length;
        for (int i = size - 1; i > 0; i--) {
            int left = size - i - 1;
            int right = i;
            for (int j = left; j < right; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            for (int j = right - 1; j > left; j--) {
                if (array[j - 1] > array[j]) {
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }
        if (DEBUG) {
            for (int i = 0; i < size; i++) {
                Log.d(TAG, String.format("CocktailSearch sort array[%d]:%d", i, array[i]));
            }
        }
    }
}
