package mortal.learn.java.annotation.introduce;

import mortal.learn.java.annotation.introduce.ActionListenerFor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.awt.event.*;
import java.lang.reflect.*;

public class ActionListenerInstaller {
    public static void processAnnotations(Object obj){
        try{
            Class<?> cl = obj.getClass();
            for(Method m : cl.getDeclaredMethods()){
                ActionListenerFor a = m.getAnnotation(ActionListenerFor.class);
                if(a != null){
                    Field f = cl.getDeclaredField(a.source());
                    f.setAccessible(true);
                    addListener(f.get(obj), obj, m);
                }
            }
        } catch(ReflectiveOperationException e){
            e.printStackTrace();
        }
    }

    public static void addListener(Object source, final Object param, final Method m)throws ReflectiveOperationException{
        //使用代理对象来调用监视器方法
        InvocationHandler handler = new InvocationHandler(){
             public Object invoke(Object proxy,Method mm, Object[] args)throws Throwable{
                 return m.invoke(param);
             }
        };
        Object listener = Proxy.newProxyInstance(null,new Class[]{java.awt.event.ActionListener.class}, handler);
        //获取添加监视器方法的方法。
        Method adder = source.getClass().getMethod("addActionListener",ActionListener.class);
        //添加监视器。
        adder.invoke(source, listener);
    }
}
