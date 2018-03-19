package hgwxr.kotlin.com.mylibrary;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by hgwxr on 2018/3/19.
 */

public class MyEventBus {

    private static final String TAG = MyEventBus.class.getSimpleName();

    private MyEventBus() {

        mHashMap = new HashMap<>();
    }

    private static MyEventBus instance;

    public static MyEventBus instance() {
        if (instance == null) {
            synchronized (MyEventBus.class) {
                if (instance == null) {
                    instance = new MyEventBus();
                }
            }
        }
        return instance;
    }

    /**
     * k 事件名称
     */
    private HashMap<Class, HashMap<Object, Method>> mHashMap;

    public void register(Object subscriber) {
        Class<?> myClass = subscriber.getClass();
        MySubscribe annotation = myClass.getAnnotation(MySubscribe.class);
        Method[] methods = myClass.getMethods();
        for (Method method : methods) {
            MySubscribe mySubscribe = method.getAnnotation(MySubscribe.class);
            if (mySubscribe != null) {
                Log.e(TAG, "register: MySubscribe Annotation: " + method.getName() + "  ");
                Class[] params = method.getParameterTypes();
                for (int j = 0; j < params.length; j++) {
                    try {
                        params[j] = Class.forName(params[j].getName());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (params.length == 1) {
                    HashMap<Object, Method> value = new HashMap<>();
                    value.put(subscriber, method);
                    mHashMap.put(params[0], value);
                    Log.e(TAG, "register: MySubscribe Annotation:end " + method.getName() + "  ");
                } else {
                    throw new IllegalArgumentException("show only one param");
                }
            }
            Log.e(TAG, "register: " + method.getName());
        }
    }

    public void unRegister(Object subscriber) {
        Class<?> aClass = subscriber.getClass();
        Method[] methods = aClass.getMethods();
        ArrayList<Class> classes = new ArrayList<>();
        for (Method method : methods) {
            MySubscribe mySubscribe = method.getAnnotation(MySubscribe.class);
            if (mySubscribe != null) {
                Class[] params = method.getParameterTypes();
                for (int j = 0; j < params.length; j++) {
                    try {
                        params[j] = Class.forName(params[j].getName());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (params.length == 1) {
                    classes.add(params[0]);
                } else {
                    throw new IllegalArgumentException("show only one param");
                }
            }
            Log.e(TAG, "regster: " + method.getName());
        }
        for (Class a : classes) {
            HashMap<Object, Method> map = mHashMap.get(a);
            if (map != null) {
                map.remove(subscriber);
                for (Method method : methods) {

                }
            }
            mHashMap.remove(a);
        }
        Log.e(TAG, "regster: " + mHashMap);
    }

    public void post(Object event) {

        HashMap<Object, Method> map = mHashMap.get(event.getClass());

        if (map != null) {
            Set<Map.Entry<Object, Method>> entries = map.entrySet();
            for (Map.Entry<Object, Method> entry : entries) {
                try {
                    entry.getValue().invoke(entry.getKey(), event);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
