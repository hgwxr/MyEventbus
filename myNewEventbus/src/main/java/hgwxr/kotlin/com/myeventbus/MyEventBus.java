package hgwxr.kotlin.com.myeventbus;

import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by hgwxr on 2018/3/19.
 */

public class MyEventBus {

    private static  final String TAG=MyEventBus.class.getSimpleName();

    private MyEventBus(){

        mHashMap=new HashMap<>();
    }
    private static MyEventBus instance;
    public static  MyEventBus instance(){
        if (instance==null){
            synchronized (MyEventBus.class){
                if (instance==null){
                    instance=new MyEventBus();
                }
            }
        }
        return instance;
    }

    /**
     * k 事件名称
     */
    private HashMap<Class,HashMap<Object,Method>> mHashMap;
    public void register(Object subscriber){
        Class<?> myClass = subscriber.getClass();
        MySubscribe annotation = myClass.getAnnotation(MySubscribe.class);
        Method[] methods = myClass.getMethods();
        for (Method method : methods) {
            MySubscribe mySubscribe = method.getAnnotation(MySubscribe.class);
            if (mySubscribe!=null){
                Log.e(TAG, "register: MySubscribe Annotation: "+method.getName()+"  ");
                Class[] params = method.getParameterTypes();
                params = new Class[ params.length] ;
                for (int j = 0; j < params.length; j++) {
                    try {
                        params[j] = Class.forName(params[j].getName());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (params.length==1){
                    HashMap<Object, Method> value = new HashMap<>();
                    value.put(subscriber,method);
                    mHashMap.put(params[0], value);
                    Log.e(TAG, "register: MySubscribe Annotation:end "+method.getName()+"  ");
                }else {
                    throw new IllegalArgumentException("show only one param");
                }
            }
            Log.e(TAG, "register: "+method.getName());
        }
    }
}
