package util;

import bean.ProductType;
import cn.hutool.crypto.SecureUtil;

public class ProductInfoUtils {
    public static String stateConversion(Integer state){
        switch (state){
            case 1:return "正常";
            case 2:return "下架";
            case 3:return "删除";
            default: break;
        }
        return null;
    }

    public static String typeNameConversion(Integer typeId){
        String sql = "SELECT typename from producttype WHERE id=?";
        ProductType o = JDBCDBUtils.get(sql, ProductType.class, typeId);
        return o.getTypename();
    }

    public static String passConversion(String pass){
        return SecureUtil.md5(pass);
    }


}
