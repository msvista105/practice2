package com.example.sxm.practice;

import com.example.sxm.utils.LogUtils;

/**
 * 快速排序
 */
public class QuickSearch {
    private static final String TAG = "QuickSearch";
    private static final boolean DEBUG = true;

    public static void sort(int[] array) {
        int length = array.length;
        quickSearch(array, 0, length - 1);
        if (DEBUG) {
            for (int i = 0; i < array.length; i++) {
                LogUtils.d(TAG, " i:" + i + " -- " + array[i] + " -- ");
            }
        }
    }

    private static void quickSearch(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        int middle = partitial(array, left, right);
        quickSearch(array, left, middle - 1);
        quickSearch(array, middle + 1, right);
    }

    private static int partitial(int[] array, int left, int right) {
        int pivot = right;
        int tail = left - 1;
        for (int i = left; i < right; i++) {
            if (array[i] <= array[pivot]) {
                swap(array, ++tail, i);
            }
        }
        swap(array, ++tail, right);
        return tail;
    }

    private static void swap(int[] array, int j, int i) {
        int temp = array[j];
        array[j] = array[i];
        array[i] = temp;
    }
}
