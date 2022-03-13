package com.mohai.one.app.core.utils;

import com.github.pagehelper.Page;
import org.springframework.cglib.beans.BeanCopier;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/18 01:55
 */
public class BeanUtil {

    // 做缓存
    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    /**
     * 使用BeanCopier工具拷贝
     * @param source
     * @param target
     */
    public static void copyBean(Object source, Object target){
        String baseKey = generateKey(source.getClass(), target.getClass());
        BeanCopier beanCopier;
        if (!BEAN_COPIER_CACHE.containsKey(baseKey)) {
            beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
            BEAN_COPIER_CACHE.put(baseKey, beanCopier);
        } else {
            beanCopier = BEAN_COPIER_CACHE.get(baseKey);
        }
        beanCopier.copy(source, target, null);
    }

    private static String generateKey(Class<?> sourceClass, Class<?> targetClass) {
        return sourceClass.toString() + targetClass.toString();
    }

    /**
     * 直接拷贝返回传入对像
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if(source == null){
            return null;
        }
        T t = null;
        try {
            t = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(String.format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
        }
        copyBean(source, t);
        return t;
    }

    /**
     * 集合的拷贝
     * @param sourceList
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> copyPropertiesOfList(List<?> sourceList, Class<T> targetClass) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> resultList = new ArrayList<>(sourceList.size());
        for (Object o : sourceList) {
            T t = null;
            try {
              t = targetClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(String.format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
            }
            copyBean(o, t);
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * 分页集合的拷贝
     * @param sourcePage
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> Page<T> copyPropertiesOfPage(Page<?> sourcePage, Class<T> targetClass) {
        if (sourcePage == null || sourcePage.isEmpty()) {
            return new Page<>();
        }
        Page<T> resultList = (Page<T>) sourcePage.clone();
        resultList.clear();
        for (Object o : sourcePage) {
            T t = null;
            try {
                t = targetClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(String.format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
            }
            copyBean(o, t);
            resultList.add(t);
        }
        return resultList;
    }

//    /**
//     * Object转Map
//     * @param obj
//     * @return
//     * @throws IllegalAccessException
//     */
//    public static Map<String, Object> parseObjectToMap(Object obj) throws IllegalAccessException {
//        Map<String, Object> map = new LinkedHashMap<>();
//        Class<?> clazz = obj.getClass();
//        for (Field field : clazz.getDeclaredFields()) {
//            field.setAccessible(true);
//            String fieldName = field.getName();
//            Object value = field.get(obj);
//            if (value == null){
//                value = "";
//            }
//            map.put(fieldName, value);
//        }
//        return map;
//    }
//
//    /**
//     * Map转Object
//     * @param map
//     * @param beanClass
//     * @return
//     * @throws Exception
//     */
//    public static Object parseMapToObject(Map<Object, Object> map, Class<?> beanClass) throws Exception {
//        if (map == null)
//            return null;
//        Object obj = beanClass.newInstance();
//        Field[] fields = obj.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            int mod = field.getModifiers();
//            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
//                continue;
//            }
//            field.setAccessible(true);
//            if (map.containsKey(field.getName())) {
//                field.set(obj, map.get(field.getName()));
//            }
//        }
//        return obj;
//    }
//
//
//    /**
//     * 利用FastJson转换对象
//     * @param obj
//     * @param cls
//     * @param <T>
//     * @return
//     */
//    public static <T> T parseObject2(Object obj, Class<T> cls) {
//        return JSONObject.parseObject(JSONObject.toJSONString(obj), cls);
//    }

}