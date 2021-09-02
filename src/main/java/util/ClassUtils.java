package util;

/**
 * @Author：liulei
 * @Version：1.0
 * @Date：2021/8/3-11:05
 * @Since:jdk1.8
 * @Description:
 */
public class ClassUtils {

    /**
     * 获取方法名
     * @param label
     * @return
     */
    public static String getMethod(String label){
        //userId  --> setUserId
        StringBuilder stringBuilder = new StringBuilder("set");
        stringBuilder.append(label.substring(0,1).toUpperCase());
        stringBuilder.append(label.substring(1,label.length()));
        return stringBuilder.toString();
    }

    /**
     * 根据列的数据类型返回对应的类
     * @param className
     * @return
     */
    public static Class getClass(String className){
        Class<?> aClass = null;
        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return aClass;
    }
}
