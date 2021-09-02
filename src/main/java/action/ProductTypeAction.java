package action;

import service.ProductTypeService;

import java.util.Scanner;

/**
 * @Author：zzt
 * @Version：1.0
 * @Date：2021/9/2-15:25
 * @Since:jdk1.8
 * @Description:
 */
public class ProductTypeAction {

    /**
     * 商品类型管理菜单
     */


    public static void productTypeManage(Scanner scanner,ProductTypeService productTypeService) {
        boolean temp = true;
        while (temp) {
            System.out.println("商品类型管理菜单");
            System.out.println("1.添加商品类型");
            System.out.println("2.修改商品类型");
            System.out.println("3.查询商品类型");
            System.out.println("4.删除商品类型");
            System.out.println("5.退出");
            System.out.print("请选择:");
            int choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    productTypeService.addProductType();
                    break;
                case 2:
                    productTypeService.upProductType();
                    break;
                case 3:
                    productTypeService.findChooseProductType();
                    break;
                case 4:
                    productTypeService.deleteProductType();
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
