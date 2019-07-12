package com.ipaylinks.test.mock.server.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {
    public static Map<String, String> beanToMap(Object bean) throws IllegalAccessException {
        if (bean == null) {
            return new HashMap<>();
        } else {
            Map<String, String> map = new HashMap<>();
            Class<?> clazz = bean.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                field.setAccessible(true);
                if (field.get(bean) instanceof String){
                    map.put(field.getName(), (String) field.get(bean));
                }
            }
            return map;
        }
    }

    public static <T> T mapToBean(Map<String, String> map, Class<T> beanClass) throws IllegalAccessException, InstantiationException{
        if (map == null || map.isEmpty()) {
            return null;
        } else {
            T t = beanClass.newInstance();

            Field[] fields = beanClass.getDeclaredFields();
            for(Field field : fields){
                field.setAccessible(true);
                field.set(t, map.get(field.getName()));
            }

//            BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());
//            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//            for (PropertyDescriptor property : propertyDescriptors) {
//                Method setter = property.getWriteMethod();
//                if (setter != null) {
//                    setter.invoke(t, map.get(property.getName()));
//                }
//            }
            return t;
        }
    }
}
