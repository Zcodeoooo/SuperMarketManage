package util;

import constants.JDBCConstants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JDBCUtils {
    /**
     * 获取数据库连接
     * @return 连接
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(JDBCConstants.DRIVER);
            connection = DriverManager.getConnection(JDBCConstants.URL, JDBCConstants.USER, JDBCConstants.PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 回收资源
     *
     * @param stm
     * @param connection
     */
    public static void close(PreparedStatement stm, Connection connection) {
        try {
            if (stm != null) {
                stm.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("回收资源出现异常！");
        }
    }

    /**
     * 回收资源
     *
     * @param rs
     * @param stm
     * @param connection
     */
    public static void close(ResultSet rs, PreparedStatement stm, Connection connection) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("回收资源出现异常！");
        }
    }

    /**
     * 执行增删改操作
     *
     * @param sql
     * @param params
     * @return 影响行数（判断是否执行成功？）
     */
    public static int executeUpdate(String sql, Object... params) {
        int n = 0;
        //获取连接
        Connection connection = getConnection();
        PreparedStatement stm = null;
        if (connection != null) {
            try {
                stm = connection.prepareStatement(sql);
                //解决参数
                if (params != null && params.length > 0) {
                    for (int i = 0; i < params.length; i++) {
                        stm.setObject((i + 1), params[i]);
                    }
                }
                n = stm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close(stm, connection);
            }
        }
        return n;
    }

    /**
     * 执行查询操作
     * @param sql sql语句
     * @param clazz 对象的类
     * @param params 可变参数
     * @param <T>  泛型
     * @return
     */
    public static <T> List<T> executeQuery(String sql, Class clazz, Object... params) {
        List<T> list = new ArrayList<>(10);
        //获取数据库连接
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        if (connection != null) {
            try {
                statement = connection.prepareStatement(sql);
                //解决参数
                if (params != null && params.length > 0) {
                    for (int i = 0; i < params.length; i++) {
                        statement.setObject((i + 1), params[i]);
                    }
                }
                //执行
                rs = statement.executeQuery();
                //获取元数据（结果集：数据库）
                ResultSetMetaData metaData = rs.getMetaData();
                while (rs.next()) {
                    //创建对象
                    T object = (T) clazz.newInstance();
                    //获取每一列中每一个格子中的值
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        //获取列名
                        String labelName = metaData.getColumnLabel(i);
                        //获取列对应的数据类型
                        String className = metaData.getColumnClassName(i);
                        //根据列名获取对应的值
                        Object value = rs.getObject(labelName);
                        //赋值
                        invoke(object, value, labelName, className, clazz);
                    }
                    //存入到集合
                    list.add(object);
                }
            } catch (SQLException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            } finally {
                close(rs, statement, connection);
            }
        }
        return list;
    }

    public static <T> T get(String sql, Class clazz, Object... params){
        T t = null;
        List<T> list = executeQuery(sql, clazz, params);
        if(list != null && list.size() > 0){
            t = list.get(0);
        }
        return t;
    }






    /**
     * 赋值
     *
     * @param object
     * @param value
     * @param labelName
     * @param className
     * @param clazz
     */
    private static void invoke(Object object, Object value, String labelName, String className, Class clazz) {
        //获取方法名
        String methodName = ClassUtils.getMethod(labelName);
        //获取方法对应的参数类型
        Class aClass = ClassUtils.getClass(className);
        try {
            //通过反射来赋值
            Method method = clazz.getMethod(methodName, aClass);
            method.invoke(object, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
