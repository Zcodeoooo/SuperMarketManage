package action;

import constants.AdminConstants;
import constants.CashierConstants;
import dao.impl.ProductInfoDaoImpl;
import dao.impl.ProductTypeDaoImpl;
import dao.impl.VipManageDaoImpl;
import service.OrderManageService;
import service.ProductInfoService;
import service.ProductTypeService;
import service.VipManageService;

import java.util.Scanner;

/**
 * ClassName:MenuAction
 *
 * @version 0.1
 * @Description: <br/>
 * @date:MenuAction15:47 <br/>
 * @authorAdministrator <br/>
 * @since JDK 1.8
 */
public class MenuAction {
    Boolean temp = true;
    private Scanner scanner = new Scanner(System.in);
    private VipManageDaoImpl vipManageDao = new VipManageDaoImpl();
    private ProductTypeDaoImpl productTypeDao = new ProductTypeDaoImpl();
    private ProductInfoDaoImpl productInfoDao = new ProductInfoDaoImpl();

    private ProductTypeService productTypeService = new ProductTypeService(productTypeDao,scanner);
    private ProductInfoService productInfoService = new ProductInfoService(productInfoDao,productTypeService,productTypeDao,scanner);
    private VipManageService vipManageService = new VipManageService(vipManageDao,scanner);
    private OrderManageService orderManageService = new OrderManageService(productInfoService,scanner);




    public void login(Class c) {
        String inputId,inputPass;
        if ("constants.AdminConstants".equals(c.getName())){
            System.out.println("请输入管理员账号");
             inputId = scanner.next();
            System.out.println("请输入管理员密码");
        }else {
            System.out.println("请输入收银员账号");
             inputId = scanner.next();
            System.out.println("请输入收银员账号");
        }
        inputPass = scanner.next();

        String id = null;
        String pass = null;
        try {
            id = (String) c.getField("id").get(null);
            pass = (String) c.getField("pass").get(null);
        } catch (IllegalAccessException e) {
            System.out.println("错误");
        } catch (NoSuchFieldException e) {
            System.out.println("请设置账号密码文件");
        }

        if (id.equals(inputId) && pass.equals(inputPass)) {
            System.out.println("登录成功");
        }else {
            System.out.println("登陆失败");
            System.out.println("1.退出 2.返回主菜单");
            Scanner scannerTemp = new Scanner(System.in);
            try {
                int next = scannerTemp.nextInt();
                if (next == 1) {
                    System.exit(0);
                }else if (next==2){
                    startMenu();
                }else {
                    System.out.println("输入错误已返回主菜单");
                    startMenu();
                }
            }catch (Exception e){
                System.out.println("输入错误已返回主菜单");
                startMenu();
            }
        }
        
    }

    public void salesChart(){
        System.out.println("1.按月份统计销量前10的商品   2.按商品类别统计销量前10的商品 3. 3.总销量");
        int inputChoose = scanner.nextInt();
        if (inputChoose==1){
            orderManageService.monthCountTop();
        }
        if (inputChoose==2){
            orderManageService.typeCountTop();
        }
        if (inputChoose==3){
            orderManageService.sumTop();
        }
    }



    /**
     * 启动菜单
     */
    public void startMenu() {
        while (temp) {
            System.out.println("***************欢迎使用XX公司超市管理系统***************** ");
            System.out.println("1、商品类型管理 ");
            System.out.println("2、商品管理");
            System.out.println("3.会员管理");
            System.out.println("4.购买管理");
            System.out.println("5.订单查询");
            System.out.println("6.排行统计");
            System.out.println("7.退出");
            int choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    login(AdminConstants.class);
                    ProductTypeAction.productTypeManage(scanner,productTypeService);
                    break;
                case 2:
                    login(AdminConstants.class);
                    ProductInfoAction.productInfoManage(scanner,productInfoService);
                    break;
                case 3:
                    login(AdminConstants.class);
                    VipAction.vipManage(scanner,vipManageService);
                    break;
                case 4:
                    //login(scanner, CashierConstants.class);
                    OrderAction.orderManage(scanner,orderManageService);
                    break;
                case 5:
                    login(CashierConstants.class);
                    orderManageService.orderLog();
                    break;
                case 6:
                    login(CashierConstants.class);
                    salesChart();
                    break;
                case 7:
                    temp = false;
                    break;
                default:
                    break;
            }
        }

    }

    public static void main(String[] args) {
        new MenuAction().startMenu();
    }
}
