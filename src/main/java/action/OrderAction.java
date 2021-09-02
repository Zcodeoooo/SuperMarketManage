package action;

import service.OrderManageService;

import java.util.Scanner;

/**
 * @Author：zzt
 * @Version：1.0
 * @Date：2021/9/2-15:24
 * @Since:jdk1.8
 * @Description:
 */
public class OrderAction {
    /**
     * 商品类型管理菜单
     */
    public static void orderManage(Scanner scanner,OrderManageService orderManageService) {
        boolean temp = true;
        while (temp) {
            System.out.println("购买管理菜单");
            System.out.println("1.购买商品");
            System.out.println("2.修改商品数量");
            System.out.println("3.删除购物车中的商品");
            System.out.println("4.展示购物车商品列表");
            System.out.println("5.退出");
            System.out.print("请选择:");
            int choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    orderManageService.addProduct();
                    break;
                case 2:
                    orderManageService.upProductNum();
                    break;
                case 3:
                    orderManageService.deleteCartProduct();
                    break;
                case 4:
                    orderManageService.showCartProduct();
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
