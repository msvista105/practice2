package com.example.sxm.collection;

import android.support.annotation.NonNull;

import com.example.sxm.utils.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SetTest {
    private static final String TAG = "SetTest";
    private static final boolean DEBUG = true;
    private Set<Integer> treeSet = new TreeSet<>();
    private Set<Integer> hasSet = new HashSet<>();

    public void test(Integer[] array) {
        List<Integer> list = Arrays.asList((Integer[]) array);
        treeSet.clear();
        hasSet.clear();
        list.forEach(setElement -> {
            LogUtils.d(TAG, "list:" + setElement);
            treeSet.add(setElement);
            hasSet.add(setElement);
        });
        treeSet.forEach(treeSetElement -> {
            LogUtils.d(TAG, "treeSet:" + treeSetElement);
        });
        hasSet.forEach(hasSetElement -> {
            LogUtils.d(TAG, "hasSet:" + hasSetElement);
        });
    }

    public void test() {
        Set<SetElement> sets = new TreeSet<>();
        sets.add(new SetElement(10, "a"));
        sets.add(new SetElement(2, "b"));
        sets.add(new SetElement(5, "c"));
        sets.add(new SetElement(3, "d"));
        sets.add(new SetElement(4, "e"));
        sets.forEach(element -> {
            LogUtils.d(TAG, "test TreeSet object name:" + element.name + " age:" + element.age);
        });
        Set<SetElement> sets2 = new HashSet<>();
        sets2.add(new SetElement(10, "a"));
        sets2.add(new SetElement(2, "b"));
        sets2.add(new SetElement(10, "a"));
        sets2.add(new SetElement(3, "d"));
        sets2.add(new SetElement(4, "e"));
        sets2.forEach(element -> {
            LogUtils.d(TAG, "test HashSet object name:" + element.name + " age:" + element.age);
        });
    }

    class SetElement implements Comparable<SetElement> {
        private int age = 0;
        private String name = "";

        public SetElement(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public int compareTo(@NonNull SetElement o) {
//            return this.age - o.age;
            return 0;
        }

        @Override
        public int hashCode() {
            //return super.hashCode();
            return 0x55555555;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj){
                return true;
            }
            if (!(obj instanceof SetElement)) {
                return false;
            }

            if (obj == null) {
                return false;
            }
            if (this.age == ((SetElement)obj).age && this.name.equals(((SetElement)obj).name)) {
                return true;
            }
            return false;
        }
    }
}
