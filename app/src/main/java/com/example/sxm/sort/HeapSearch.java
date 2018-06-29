package com.example.sxm.sort;

import android.util.Log;

public class HeapSearch {
    private static final String TAG = "HeapSearch";
    private static final boolean DEBUG = true;

    public static void sort(int[] array) {
        int size = array.length;
        int nodeNotLeaf = (size - 1) / 2;//最后一个非叶节点,最后一个节点的父节点
        for (int i = size - 1; i > 0; i--) {
            int nodeNotLeaf2 = (i - 1) / 2;
            while (nodeNotLeaf2 >= 0) {
                //左子节点
                int left = nodeNotLeaf2 * 2 + 1;
                int right = nodeNotLeaf2 * 2 + 2;
//                Log.d(TAG, String.format("HeapSearch sort array[%d]:%d  ---  array[%d]:%d  ---  array[nodeNotLeaf2]:%d", left, array[left], right, array[right], array[nodeNotLeaf2]));
                if (left >= 0 && left <= i && array[left] > array[nodeNotLeaf2]) {
                    swap(array, left, nodeNotLeaf2);
                }
                if (right >= 0 && right <= i && array[right] > array[nodeNotLeaf2]) {
                    swap(array, right, nodeNotLeaf2);
                }
                nodeNotLeaf2--;
            }
//            Log.d(TAG, String.format("HeapSearch sort array[%d]:%d  ---  array[0]:%d", i, array[i], array[0]));
            swap(array, i, 0);
        }
        if (DEBUG) {
            for (int i = 0; i < size; i++) {
                Log.d(TAG, String.format("HeapSearch sort array[%d]:%d", i, array[i]));
            }
        }
    }

    public static void swap(int[] array, int a1, int a2) {
        int temp = array[a1];
        array[a1] = array[a2];
        array[a2] = temp;
    }
}
