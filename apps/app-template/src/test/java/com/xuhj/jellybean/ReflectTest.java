package com.xuhj.jellybean;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/3/15
 */
public class ReflectTest {

    @Test
    public void dynamicTest() throws Exception {
        CopyOnWriteArrayList
        IUser user = (IUser) Proxy.newProxyInstance(this.getClass().getClassLoader()
                , new Class[]{IUser.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("getUserName")) {
                            System.out.println("proxy: " + args[0]);
                        }
                        return method.invoke(new Student1(), args);
                    }
                });
        user.getUserName("xuhaojie123");

    }

    public class UserProxy implements InvocationHandler {

        private Object object;

        public UserProxy(Object object) {
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object obj = method.invoke(object, args);
//            if (method.getName().equals("getUserName")) {
//                System.out.println(args[0]);
//            }
            return obj;
        }
    }

    public interface IUser {
        void getUserName(String name);
    }

    public class Student1 implements IUser {
        @Override
        public void getUserName(String name) {
        }
    }

}
