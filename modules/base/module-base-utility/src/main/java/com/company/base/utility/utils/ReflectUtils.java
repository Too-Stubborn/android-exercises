package com.company.base.utility.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 反射工具类
 *
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/27
 */
public class ReflectUtils {

    /**
     * Map转Object
     *
     * @param map   map
     * @param clazz clazz
     * @return Object
     * @throws Exception
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> clazz) throws Exception {
        if (map == null) {
            return null;
        }
        Object obj = clazz.newInstance();

        // 获取所有声明属性字段，包含父类中的属性字段
        List<Field> declareFields = new ArrayList<>();
        do {
            declareFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class);

        // 循环所有属性字段，将map中的属性设置进去
        for (Field field : declareFields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }

    /**
     * Object转Map，包含父类中的属性字段
     *
     * @param obj obj
     * @return Map<String, Object>
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();

        // 遍历所有声明的属性字段，包含父类中的属性字段
        Class<?> clazz = obj.getClass();
        do {
            Field[] declareFields = clazz.getDeclaredFields();
            for (Field field : declareFields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class);
        return map;
    }
}
