package util;

import bean.Cart;

import java.io.*;
import java.util.Date;
import java.util.List;

public class OrderUtil {

    //序列化购物车
    public static void serialCart(List<Cart> carts) {
        ObjectOutputStream os = null;
        File file = new File("record.txt");
        try {
            os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(carts);
            os.flush();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //反序列化购物车
    public static List<Cart> reverseSerialCart() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("record.txt"));
            List<Cart> o = (List<Cart>) ois.readObject();
            ois.close();
            if (o!=null){
                return o;
            }else {
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    //清空序列化购物车文件
    public static void clearInfoForFile(String fileName) {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static float orderCalc(List<Cart> carts) {
        float price;
        float sumPrice = 0f;
        for (Cart cart : carts) {
            price=cart.getNum() * cart.getPrice()*(100 - cart.getDiscount()) / 100f;
            sumPrice+=price;
        }
        return sumPrice;
    }

    public static String dateConversion(Date date){
        String s = date.toString();
        return s.substring(0,19);
    }

    public static String payTypeConversion(Integer payType){
        if (payType==0){
            return "现金支付";
        }
        if (payType==1){
            return "会员卡支付";
        }
        else {
            return "";
        }
    }

    public static String vipIdConversion(Integer id){
        if (id == null){
            return "非会员用户";
        }
        return String.valueOf(id);
    }

}