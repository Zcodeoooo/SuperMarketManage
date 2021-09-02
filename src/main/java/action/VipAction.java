package action;

import service.VipManageService;

import java.util.Scanner;

/**
 * @Author：zzt
 * @Version：1.0
 * @Date：2021/9/2-15:24
 * @Since:jdk1.8
 * @Description:
 */
public class VipAction {


    /**
     * 会员管理菜单
     */

    public static void vipManage(Scanner scanner,VipManageService vipManageService){
        boolean temp = true;
        while (temp) {
            System.out.println("会员管理菜单");
            System.out.println("1.添加会员信息");
            System.out.println("2.修改会员信息");
            System.out.println("3.查询会员信息");
            System.out.println("4.删除会员信息");
            System.out.println("5.会员充值");
            System.out.println("6.退出");
            System.out.print("请选择:");
            int choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    vipManageService.addVip();
                    break;
                case 2:
                    vipManageService.upVip();
                    break;
                case 3:
                    vipManageService.findVip();
                    break;
                case 4:
                    vipManageService.deleteVip();
                    break;
                case 5:
                    vipManageService.payVip();
                    break;
                case 6:
                    temp = false;
                    break;
                default:
                    break;
            }
        }
    }
}
