package service;

import bean.*;
import dao.impl.OrderManageDaoImpl;
import dao.impl.ProductInfoDaoImpl;
import org.apache.commons.dbutils.DbUtils;
import util.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Administrator
 */
public class OrderManageService {
    private ProductInfoService productInfoDao;
    private Scanner scanner;

    public OrderManageService(ProductInfoService productInfoDao, Scanner scanner) {
        this.productInfoDao = productInfoDao;
        this.scanner = scanner;
        List<Cart> cartsBuck = OrderUtil.reverseSerialCart();
        //判断历史购物车是否为空
        if (cartsBuck == null) {
            carts = new ArrayList<>();
        } else {
            carts = cartsBuck;
        }

    }

    //按月份统计销量前10的商品

    public void monthCountTop() {
        System.out.println("请输入月份");
        String inputMoney = scanner.next();
        try {
            int i = Integer.parseInt(inputMoney);
            if (i < 10) {
                inputMoney = 0 + inputMoney;
            }
        } catch (Exception e) {
            System.out.println("请输入数字");
        }
        for (Top top : orderManageDao.findMonthCountTop(inputMoney)) {
            System.out.println(top.getPName() + "\t" + top.getNB());
        }


    }

    /**
     * 按商品类别统计销量前10
     */
    public void typeCountTop() {
        for (Top top : orderManageDao.findTypeCountTop()) {
            System.out.println(top.getPName() + "\t" + top.getNB());
        }

    }

    /**
     * 总销量
     */
    public void sumTop() {
        Object sumTop = orderManageDao.findSumTop();
        System.out.println("总销量" + sumTop);
    }


    private List<Cart> carts;
    private ProductInfoDaoImpl productInfoService = new ProductInfoDaoImpl();
    private OrderManageDaoImpl orderManageDao = new OrderManageDaoImpl();
    List<ProductInfo> allProductInfo = productInfoService.findAllProductInfo();

    /**
     * 查询订单记录
     */
    public void orderLog() {
        System.out.println("1.商品编号查询 2.会员编号查询");
        int inputChoose = scanner.nextInt();

        if (inputChoose == 1) {
            int inputId = scanner.nextInt();
            List<OrderInfo> orderInfoLog = orderManageDao.findOrderInfoLog(inputId);
            List<OrderDetails> orderDetailsLog = orderManageDao.findOrderDetailsLog(inputId);
            if (!orderInfoLog.isEmpty()) {
                System.out.println("简要订单记录");
                System.out.println("编号\t\t会员编号\t\t总计金额\t\t订单生成时间\t\t支付方式\t\t付款金额");
                for (OrderInfo orderInfo : orderInfoLog) {
                    System.out.println(orderInfo.getId() + "\t\t" + OrderUtil.vipIdConversion(orderInfo.getVipId()) + "\t\t" + orderInfo.getPrice() + "\t\t" + OrderUtil.dateConversion(orderInfo.getOrderDate()) + "\t\t" + OrderUtil.payTypeConversion(orderInfo.getPayType()) + "\t\t" + orderInfo.getMoney());
                }
            }
            if (!orderDetailsLog.isEmpty()) {
                System.out.println("详细订单记录");
                System.out.println("编号\t\t订单编号\t\t商品编号\t\t商品数量\t\t总金额");
                for (OrderDetails orderDetails : orderDetailsLog) {
                    System.out.println(orderDetails.getId() + "\t\t" + orderDetails.getOrderId() + "\t\t" + orderDetails.getProductId() + "\t\t" + orderDetails.getNum() + "\t\t" + orderDetails.getMoney());
                }
            }

        }

        if (inputChoose == 2) {
            int inputId = scanner.nextInt();
            List<OrderInfo> vipIdOrderDetailsLog = orderManageDao.findCardOrderDetailsLog(inputId);
            List<OrderDetails> vipIdOrderInfoLog = orderManageDao.findCardOrderInfoLog(inputId);
            if (!vipIdOrderInfoLog.isEmpty()) {
                System.out.println("简要订单记录");
                System.out.println("编号\t\t会员编号\t\t总计金额\t\t订单生成时间\t\t支付方式\t\t付款金额");
                for (OrderInfo orderInfo : vipIdOrderDetailsLog) {
                    System.out.println(orderInfo.getId() + "\t\t" + OrderUtil.vipIdConversion(orderInfo.getVipId()) + "\t\t" + orderInfo.getPrice() + "\t\t" + OrderUtil.dateConversion(orderInfo.getOrderDate()) + "\t\t" + OrderUtil.payTypeConversion(orderInfo.getPayType()) + "\t\t" + orderInfo.getMoney());
                }
            }

            if (!vipIdOrderDetailsLog.isEmpty()) {
                System.out.println("简要订单记录");
                System.out.println("编号\t\t订单编号\t\t商品编号\t\t商品数量\t\t总金额");
                for (OrderInfo orderInfo : vipIdOrderDetailsLog) {
                    System.out.println(orderInfo);
                }
            }
        }
    }


    public ProductInfo showProductInfo(Integer id) {
        try {
            for (ProductInfo productInfo : allProductInfo) {
                if (productInfo.getId().equals(id)) {
                    System.out.println("商品编号\t\t商品名\t\t单价\t\t商品数量\t\t折扣\t\t商品所属类型\t\t商品状态");
                    System.out.println(productInfo.getId() + "\t\t\t" + productInfo.getPname() + "\t" + productInfo.getPrice() + "\t\t" + productInfo.getNum() + "\t\t" + productInfo.getDiscount() + "\t\t" + ProductInfoUtils.typeNameConversion(productInfo.getTypeId()) + "\t\t\t" + ProductInfoUtils.stateConversion(productInfo.getState()));
                    return productInfo;
                }
            }
        } catch (Exception e) {
            System.out.println("输入错误");
        }
        return null;
    }


    public void addProduct() {
        ProductInfo productInfo = null;
        productInfoDao.findAllProductInfo();
        boolean choose = true;
        while (choose) {
            System.out.println("请输入商品id,输入exit退出添加");
            int inputId = 0;
            Scanner scanner2 = new Scanner(System.in);
            try {
                inputId = scanner2.nextInt();
                productInfo = showProductInfo(inputId);
                while (productInfo == null) {
                    System.out.println("请重新输入没有该id");
                    inputId = scanner2.nextInt();
                    productInfo = showProductInfo(inputId);
                }
            } catch (Exception e) {
                String input = scanner2.next();
                if ("exit".equals(input)) {
                    amount();
                    break;
                }
            }

            System.out.println("请输入要购买的商品数量");
            Scanner scanner3 = new Scanner(System.in);
            int inputNum = 0;
            try {
                inputNum = scanner3.nextInt();
            } catch (Exception e) {
                try {
                    throw new Exception("输入错误");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            while (inputNum < 0 || productInfo.getNum() < inputNum) {
                System.out.println("请重新输入商品数量");
                inputNum = scanner.nextInt();
            }

            //去本地库存
            for (ProductInfo info : allProductInfo) {
                if (info.getId().equals(inputId)) {
                    info.setNum((productInfo.getNum() - inputNum));
                    break;
                }
            }

            //购物车去重
            if (carts != null) {
                boolean flag = true;
                for (Cart cart : carts) {
                    if (cart.getId().equals(inputId)) {
                        cart.setNum(cart.getNum() + inputNum);
                        flag = false;
                    }
                }
                if (flag) {
                    carts.add(new Cart(productInfo.getPname(), productInfo.getId(), inputNum, productInfo.getPrice(), productInfo.getDiscount()));
                }
            }
        }
        System.out.println(carts);
    }

    public void amount() {
        //结算阶段
        System.out.println("购物车");
        showCartProduct();
        System.out.println();
        float sumPrice = OrderUtil.orderCalc(carts);
        System.out.println("一共" + sumPrice + "元");
        System.out.println("1.现金 2.会员卡 (任意键退出)");
        int moneyPay = 1, cardPay = 2;
        int inputChoose = scanner.nextInt();
        if (inputChoose == moneyPay) {
            moneyPay();
        } else if (inputChoose == cardPay) {
            cardPay();
        }
    }


    public void moneyPay() {
        System.out.println("请输入现金");
        float inputMoney = scanner.nextFloat();
        float sumPrice = OrderUtil.orderCalc(carts);
        if (inputMoney - sumPrice >= 0) {
            //返回的id
            Integer returnId = orderManageDao.addOrderInfo(new OrderInfo(null, null, sumPrice, ProductTypeUtils.date(), 0, inputMoney));
            if (returnId > 0) {
                for (Cart cart : carts) {
                    OrderDetails orderDetails = new OrderDetails(null, returnId, cart.getId(), cart.getNum(), cart.getPrice() * cart.getNum());
                    //添加到历史订单
                    orderManageDao.addOrderDetails(orderDetails);
                    //修改库存
                    orderManageDao.subStock(cart.getId(), cart.getNum());
                }
            }
            System.out.println("支付成功,刷新购物车");
            //清空数组
            carts.clear();
            //清空序列化购物车文件
            OrderUtil.clearInfoForFile("record.txt");


        } else {
            System.out.println("金额不足");
            OrderUtil.serialCart(carts);
        }
    }

    /**
     * 待改
     */
    public void cardPay() {
        //减库存,减卡余额,加积分 事务
        System.out.println("会员卡支付");
        System.out.println("请输入会员卡号");
        String card = scanner.next();
        System.out.println("请输入会员密码");
        String pass = scanner.next();
        //pass = SecureUtil.md5(pass);
        //System.out.println(pass);
        Vip vip = orderManageDao.vipLogin(card, pass);
        if (vip != null) {
            System.out.println("验证成功");
            float sumPrice = OrderUtil.orderCalc(carts);
            if (vip.getMoney() >= sumPrice) {
                String sql1 = "UPDATE vip SET money=(money-?),jifen=(jifen+?) WHERE id = ?";
                //获取连接
                Connection connection = JDBCUtils.getConnection();
                try {
                    //设置事务的提交方式为手动
                    connection.setAutoCommit(false);
                    int ii = JDBCDBUtils.update(connection, sql1, sumPrice, jiFenCalc(sumPrice), vip.getId());
                    if (ii > 0) {
                        //添加订单
                        Integer returnId = orderManageDao.addOrderInfo(new OrderInfo(null, vip.getId(), sumPrice, ProductTypeUtils.date(), 1, vip.getMoney()));
                        for (Cart cart : carts) {
                            //减库存
                            Integer statusCode = orderManageDao.subStock(cart.getId(), cart.getNum());
                            OrderDetails orderDetails = new OrderDetails(null, returnId, cart.getId(), cart.getNum(), cart.getPrice() * cart.getNum());
                            //添加到历史订单
                            orderManageDao.addOrderDetails(orderDetails);
                            if (statusCode < 0) {
                                System.out.println("扣款失败！");
                                DbUtils.rollbackAndClose(connection);
                                JDBCUtils.close(null, connection);
                                break;
                            }
                        }
                        System.out.println("购买成功！");
                        DbUtils.commitAndClose(connection);
                        //清空数组
                        carts.clear();
                        //清空序列化购物车文件
                        OrderUtil.clearInfoForFile("record.txt");
                    } else {
                        System.out.println("扣款失败！");
                        DbUtils.rollbackAndClose(connection);
                        JDBCUtils.close(null, connection);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("扣款失败！");
                    JDBCUtils.close(null, connection);
                    try {
                        DbUtils.rollbackAndClose(connection);
                        JDBCUtils.close(null, connection);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                System.out.println("账户金额不足");
            }
        } else {
            System.out.println("验证失败");
        }
        OrderUtil.serialCart(carts);
    }




    public Integer jiFenCalc(Float money) {
        return Math.round(money);
    }


    public void upProductNum() {
        showCartProduct();
        System.out.println("请要修改的商品编号");
        int inputId = scanner.nextInt();
        System.out.println("请输入商品数量");
        int inputNum = scanner.nextInt();
        ProductInfo productInfo2 = allProductInfo.get(inputId);
        if (productInfo2.getNum() >= inputNum) {
            for (Cart cart : carts) {
                if (cart.getId().equals(inputId)) {
                    cart.setNum(inputNum);
                    System.out.println("修改成功");
                    break;
                }
            }
        } else {
            System.out.println("修改失败");
        }
    }


    public void deleteCartProduct() {
        showCartProduct();
        System.out.println("请输入要删除的商品id");
        int inputId = scanner.nextInt();
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getId() == inputId) {
                carts.remove(i);
                break;
            }
        }
        OrderUtil.clearInfoForFile("record.txt");
        OrderUtil.reverseSerialCart();


    }


    public void showCartProduct() {
        if (!carts.isEmpty()) {
            System.out.println("商品id\t商品名称\t购买数量\t商品单价\t商品折扣");
            for (Cart cart : carts) {
                System.out.println(cart.getId() + "\t" + cart.getName() + "\t" + cart.getNum() + "\t" + cart.getPrice() + "\t" + cart.getDiscount());
            }
        } else {
            System.out.println("******请添加商品******");
        }

    }

}
