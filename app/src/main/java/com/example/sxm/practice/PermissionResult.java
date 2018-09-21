package com.example.sxm.practice;

import com.example.sxm.annotation.OnPermissionGranted;
import com.example.sxm.utils.LogUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PermissionResult {
    private static final String TAG = "PermissionResult";
    static void permissionGranted(Object obj, int request_code, Class clz) throws InvocationTargetException, IllegalAccessException {
        Method methods[] = obj.getClass().getDeclaredMethods();
        LogUtils.d(TAG,"permissionGranted clz:"+clz.getSimpleName());
        for (Method method : methods) {
            LogUtils.d(TAG,"permissionGranted method:"+method.getName());
            Annotation a = method.getAnnotation(clz);
            if (a != null){
                LogUtils.d(TAG,"annotation:"+a.getClass().getSimpleName());
            }
            if(a != null && isSameCode(method,request_code,clz)){
                if (method.isAccessible()) {
                    method.setAccessible(true);
                }
                method.invoke(obj);
            }
//            Annotation annotations[] = method.getAnnotations();
//            if(annotations == null){
//                return;
//            }
//            for (Annotation a : annotations) {
//                LogUtils.d(TAG,"annotation:"+a.getClass().getSimpleName());
//                if (a.getClass().getSimpleName().equals(clz.getSimpleName())) {
//                    if (method.isAccessible()) {
//                        method.setAccessible(true);
//                    }
//                    method.invoke(obj);
//                }
//            }
        }
    }
    static boolean isSameCode(Method method, int request_code, Class clz){
        if(clz.equals(OnPermissionGranted.class)){
            return request_code == method.getAnnotation(OnPermissionGranted.class).value();
        }
        return false;
    }
}
