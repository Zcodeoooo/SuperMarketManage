package util;

import java.util.Date;

public class ProductTypeUtils {
    public static String textConversion(Integer text){
        if (text==1){
            return "是";
        }else {
            return "否";
        }
    }

    public static String dateConversion(Date date){
        String s = date.toString();
        return s.substring(0,19);
    }


    public static Date date(){
        return new Date();
    }
}
