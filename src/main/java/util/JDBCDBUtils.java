package util;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author：liulei
 * @Version：1.0
 * @Date：2021/8/3-14:43
 * @Since:jdk1.8
 * @Description:
 */
public class JDBCDBUtils {
    /**
     * 执行增删改
     *
     * @param sql
     * @param params
     * @return
     */
    public static int update(String sql, Object... params) {
        int i = 0;
        //获取连接
        Connection connection = JDBCUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        try {
            i = queryRunner.update(connection, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return i;
    }

    public static int update(Connection connection,String sql, Object... params) {
        int i = 0;
        //获取连接
        QueryRunner queryRunner = new QueryRunner();
        try {
            i = queryRunner.update(connection, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }


    public static int updateGetId(String sql, Object... params) {
        //获取连接
        Connection connection = JDBCUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        try {
            String sql2 = "SELECT LAST_INSERT_ID()";
            queryRunner.update(connection, sql, params);
            BigInteger returnId = (BigInteger) queryRunner.query(connection,sql2,new ScalarHandler());
            return returnId.intValue();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return 0;
    }


    public static Object getSum(String sql) {
        //获取连接
        Connection connection = JDBCUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        try {
            Map<String, Object> query = queryRunner.query(connection, sql, new MapHandler());
            return query.values();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return 0;
    }



    /**
     * 新增，返回自增id
     * @param sql
     * @return
     */
    public static Integer insert(String sql,Object... params){
        Integer id = null;
        //获取连接
        Connection connection = JDBCUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        try {
            id = queryRunner.insert(connection,sql,new ScalarHandler<Long>(),params).intValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(connection);
        }
        return id;
    }

    /**
     * 查询所有
     *
     * @param sql
     * @param clazz
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> find(String sql, Class clazz, Object... params) {
        List<T> list = new ArrayList<>(10);
        //获取连接
        Connection connection = JDBCUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        try {
            list = queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return list;
    }

    /**
     * 获取单个实体
     *
     * @param sql
     * @param clazz
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T get(String sql, Class clazz, Object... params) {
        T t = null;
        //获取连接
        Connection connection = JDBCUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        try {
            t = queryRunner.query(connection, sql, new BeanHandler<T>(clazz), params);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(connection);
        }
        return t;
    }




    /**
     * 统计条数
     * @param sql
     * @param params
     * @return
     */
    public static Long count(String sql, Object... params) {
        Long count = 0L;
        //获取连接
        Connection connection = JDBCUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        try {
            count = queryRunner.query(connection, sql, new ScalarHandler<>(), params);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(connection);
        }
        return count;
    }
}
