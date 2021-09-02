package util;

import constants.IsNull;
import constants.NumRange;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class MyValidationUtil {
    private static <T> List<String> validate(T obj,T ...pare){
        ArrayList<String> warning = new ArrayList<>();
        Class clazz = obj.getClass();
        //获取类的所有属性
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            //获取属性 declaredField.getName() 属性名
            Field field = null;
            try {
                field = clazz.getDeclaredField(declaredField.getName());
                field.setAccessible(true);
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    //数字为空判断
                    if (annotation instanceof IsNull) {
                        Object o = field.get(obj);
                        IsNull isNull = (IsNull) annotation;
                        if (o == null) {
                            warning.add(isNull.Message());
                        }
                    }
                    //数组大小判断
                    if (annotation instanceof NumRange) {
                        Object o = field.get(obj);
//                        if (o instanceof Integer) {
                            int value = (Integer) o;
                            NumRange numRange = (NumRange) annotation;
                            //获取注解的属性值
                            if (!(value < numRange.Max() && value > numRange.Min())) {
                                warning.add("错误" + numRange.Message());
                            }
//                        }
                    }

                }
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }

        }
        return warning;
    }
}
