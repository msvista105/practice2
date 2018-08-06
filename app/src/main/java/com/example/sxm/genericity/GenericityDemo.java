package com.example.sxm.genericity;

import android.widget.LinearLayout;

import com.example.sxm.utils.LogUtils;

import java.util.ArrayList;
import java.util.Collection;


/**
 * 泛型 ： 泛型类，泛型方法;泛型向上、向下类型固定；多个泛型
 * */
public class GenericityDemo {
    private static final String TAG = "GenericityDemo";
    public void test (){
        //泛型类
        new Request<String>().setElement("haha");
        //泛型方法
        Request1 request1 = new Request1();
        request1.<String>setElement("haha_1");
        request1.<Integer>getElement(222);
        //向下限制
        Request2<ArrayList,String> request2 = new Request2<>();
        request2.setElement(new ArrayList());
        request2.getElement();
    }
    class Request<T>{
        private T t;
        public T getElement(){
            LogUtils.d(TAG,"Request:getElement:element.type:"+t.getClass().getSimpleName()+" body:"+t);
            return t;
        }
        public void setElement(T element){
            this.t = element;
        }
    }
    class Request1{
        public <T> void setElement(T element){
            LogUtils.d(TAG,"Request1:setElement:element.type:"+element.getClass().getSimpleName());
        }
        public <T> T getElement(T element){
            T t = element;
            LogUtils.d(TAG,"Request1:getElement:element.type:"+element.getClass().getSimpleName());
            return t;
        }
    }
    class Request2<T extends Collection,R>{
        private T t;
        public T getElement(){
            LogUtils.d(TAG,"Request2:getElement:element.type:"+t.getClass().getSimpleName()+" body:"+t);
            return t;
        }
        public void setElement(T element){
            this.t = element;
        }
    }

}
