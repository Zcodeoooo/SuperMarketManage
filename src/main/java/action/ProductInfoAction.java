package action;

import service.ProductInfoService;

import java.util.Scanner;

/**
 * @Author：zzt
 * @Version：1.0
 * @Date：2021/9/2-15:21
 * @Since:jdk1.8
 * @Description:
 */
public class ProductInfoAction {

    /**
     * 商品管理菜单
     */


    public static void productInfoManage(Scanner scanner,ProductInfoService productInfoService) {
        boolean temp = true;
        while (temp) {
            System.out.println("商品管理菜单");
            System.out.println("1.添加商品信息");
            System.out.println("2.修改商品信息");
            System.out.println("3.查询商品信息");
            System.out.println("4.删除商品信息");
            System.out.println("5.退出");
            System.out.print("请选择:");
            int choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    productInfoService.addProductInfo();
                    break;
                case 2:
                    productInfoService.upProductInfo();
                    break;
                case 3:
                    productInfoService.findProductInfo();
                    break;
                case 4:
                    productInfoService.deleteProductInfo();
                    break;
                case 5:
                    temp = false;
                    break;
                default:
                    break;
            }
        }
    }
}
