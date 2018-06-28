package com.example.sxm.sort;

import android.util.Log;

public class MergeSort {
    private static final boolean DEBUG = true;
    private static final String TAG = "MergeSort";
    public static void sort(int[] array) {
        int size = array.length;
        int left, right;
        left = 0;
        right = size-1;
        mergeRecursion(array,left,right);
        if (DEBUG) {
            for (int i = 0; i < size; i++) {
                Log.d(TAG, String.format("MergeSort sort array[%d]:%d", i, array[i]));
            }
        }
    }

    private static void mergeRecursion(int[] array, int left, int right) {
        if (left >= right) {//间隔为0，这个是最小间隔
            return;
        }
        int middle = (left + right) / 2;
        mergeRecursion(array,left,middle);
        mergeRecursion(array,middle + 1,right);
        merge(array,left,middle,right);
    }

    private static void merge(int[] array, int left, int middle, int right){
        int[] tempArray = new int[right - left + 1];
        int index0 = 0;//tempArray 起点
        int index1 = left;//数组1起始点
        int index2 = middle + 1;
        while (index1  <= middle && index2 <= right) {
            tempArray[index0 ++] = array[index1] < array[index2] ? array[index1 ++]:array[index2 ++];
        }
        while (index1 <= middle) {
            tempArray[index0 ++] = array[index1 ++];
        }
        while (index2 <= right) {
            tempArray[index0 ++] = array[index2 ++];
        }
        int tempIndex = 0;
        for(int i=left;i<=right;i++){
            array[i] = tempArray[tempIndex ++];
        }
    }
}
