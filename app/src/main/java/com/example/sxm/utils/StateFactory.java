package com.example.sxm.utils;

/**
 * 工厂模式，泛型
 * */

public class StateFactory {
    private static StateFactory mInstance = null;
    public StateFactory getInstance(){
        if(mInstance == null){
            mInstance = new StateFactory();
        }
        return mInstance;
    }
    public <T extends State> T getState(Class<T> cls){
        T state = null;
        try{
            state = cls.newInstance();
        }catch(Exception e){
            e.printStackTrace();
        }
        return state;
    }
    public static <E> E getGenericity(){
        return null;
    }
}
