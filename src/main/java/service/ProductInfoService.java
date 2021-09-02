package service;

import bean.OrderDetails;
import bean.ProductInfo;
import dao.ProductTypeDao;
import dao.impl.ProductInfoDaoImpl;
import util.ProductInfoUtils;
import util.ValidationUtil;

import java.util.List;
import java.util.Scanner;

public class ProductInfoService {
    private ProductInfoDaoImpl productInfoDao;
    private ProductTypeService productTypeService;
    private ProductTypeDao productTypeDao;
    private Scanner scanner;

    public ProductInfoService(ProductInfoDaoImpl productInfoDao, ProductTypeService productTypeService, ProductTypeDao productTypeDao, Scanner scanner) {
        this.productInfoDao = productInfoDao;
        this.productTypeService = productTypeService;
        this.productTypeDao = productTypeDao;
        this.scanner = scanner;
    }

    public void addProductInfo() {
        System.out.println("*******添加商品信息*******");
        System.out.println("请输入商品名");
        String inputName = scanner.next();
        System.out.println("请输入商品单价");
        Float inputPrice = scanner.nextFloat();
        System.out.println("请输入商品数量");
        Integer inputNum = scanner.nextInt();
        System.out.println("请输入商品折扣");
        Integer inputDiscount = scanner.nextInt();
        productTypeService.findAllId();
        System.out.println("请输入商品所属类型");
        int inputTypeId = scanner.nextInt();
        while (productTypeDao.findId(inputTypeId)){
            inputTypeId = scanner.nextInt();
        }
        System.out.println("请输入商品状态 1-正常，2-下架，3-删除");
        int inputState = scanner.nextInt();
        ProductInfo productInfo = new ProductInfo(null, inputName, inputPrice, inputNum, inputDiscount, inputTypeId, inputState);
        List<String> valid = ValidationUtil.valid(productInfo);
        if (valid!=null){
            productInfoDao.addProductInfo(productInfo);
        }



    }


    public void upProductInfo() {
        List<ProductInfo> allProductInfo = productInfoDao.findAllProductInfo();
        findAllProductInfo();
        System.out.println("请选择编号");
        try {
            int inputId = scanner.nextInt();
            ProductInfo productInfo = productInfoDao.findProductInfo(inputId);
            if (productInfo != null) {
                try {
                    Scanner scanner3 = new Scanner(System.in);
                    System.out.println("请输入商品名称,默认请回车" + "(" + productInfo.getPname() + ")");
                    String pname = scanner3.nextLine();
                    System.out.println("请输入商品单价,默认请回车" + "(" + productInfo.getPrice() + ")");
                    String price = scanner3.nextLine();
                    System.out.println("请输入商品数量,默认请回车" + "(" + productInfo.getNum() + ")");
                    String num = scanner3.nextLine();
                    System.out.println("请输入商品折扣,默认请回车" + "(" + productInfo.getDiscount() + ")");
                    String discount = scanner3.nextLine();
                    System.out.println("请输入商品所属类型,默认请回车" + "(" + productInfo.getTypeId() + ")");
                    String typeId = scanner3.nextLine();
                    System.out.println("请输入商品状态(1.正常 2.下架 3.删除),默认请回车" + "(" + productInfo.getState() + ")");
                    String state = scanner3.nextLine();
                    if (!"".equals(pname)){
                        productInfo.setPname(pname);
                    }
                    if (!"".equals(price)){
                        productInfo.setPrice(Float.parseFloat(price));
                    }
                    if (!"".equals(num)){
                        productInfo.setNum(Integer.parseInt(num));
                    }
                    if (!"".equals(discount)){
                        productInfo.setDiscount(Integer.parseInt(discount));
                    }
                    if (!"".equals(typeId)){
                        productInfo.setTypeId(Integer.parseInt(typeId));
                    }
                    if (!"".equals(state)){
                        productInfo.setState(Integer.parseInt(state));
                    }
                    System.out.println(productInfo);

                    Integer integer = productInfoDao.upProductInfo(productInfo);
                    if (integer>0){
                        System.out.println("商品编号\t\t商品名\t\t单价\t\t商品数量\t\t折扣\t\t商品所属类型\t\t商品状态");
                        System.out.println(productInfo.getId() + "\t\t\t" + productInfo.getPname() + "\t" + productInfo.getPrice() + "\t\t" + productInfo.getNum() + "\t\t" + productInfo.getDiscount() + "\t\t" + ProductInfoUtils.typeNameConversion(productInfo.getTypeId()) + "\t\t\t" + ProductInfoUtils.stateConversion(productInfo.getState()));

                        System.out.println("修改成功");
                    }
                } catch (Exception e) {
                    System.out.println("请输入数字");
                }
            } else {
                System.out.println("没有该id");
            }
        } catch (Exception e) {
            System.out.println("输入错误");
        }

    }

    public void findProductInfo() {
        System.out.println("********查询商品信息********");
        System.out.println("请输入要查询的商品id,模糊查询商品名,商品类型,查询所有请输入ALL");
        Scanner scanner2 = new Scanner(System.in);
        try {
            int inputId = scanner2.nextInt();
            ProductInfo productInfo = productInfoDao.findProductInfo(inputId);
            if (productInfo != null) {
                System.out.println("商品编号\t\t商品名\t\t单价\t\t商品数量\t\t折扣\t\t商品所属类型\t\t商品状态");
                System.out.println(productInfo.getId() + "\t\t\t" + productInfo.getPname() + "\t" + productInfo.getPrice() + "\t\t" + productInfo.getNum() + "\t\t" + productInfo.getDiscount() + "\t\t" + ProductInfoUtils.typeNameConversion(productInfo.getTypeId()) + "\t\t\t" + ProductInfoUtils.stateConversion(productInfo.getState()));
            } else {
                System.out.println("没有该结果");
            }
        } catch (Exception e) {
            String inputName = scanner2.next();
            //查询商品名返回的集合
            List<ProductInfo> productInfo2 = productInfoDao.findProductInfo(inputName);
            //查询商品类型返回的集合
            List<ProductInfo> productInfo = productInfoDao.findProductTypeNameInfo(inputName);
            //所有
            List<ProductInfo> allProductInfo = null;
            if (!productInfo.isEmpty()) {
                System.out.println("商品编号\t\t商品名\t\t单价\t\t商品数量\t\t折扣\t\t商品所属类型\t\t商品状态");
                for (ProductInfo info : productInfo) {
                    if (ProductInfoUtils.typeNameConversion(info.getTypeId()).equals(inputName)) {
                        System.out.println(info.getId() + "\t\t\t" + info.getPname() + "\t" + info.getPrice() + "\t\t" + info.getNum() + "\t\t" + info.getDiscount() + "\t\t" + ProductInfoUtils.typeNameConversion(info.getTypeId()) + "\t\t\t" + ProductInfoUtils.stateConversion(info.getState()));
                    }
                }
            }

            if (!productInfo2.isEmpty()) {
                System.out.println("商品编号\t\t商品名\t\t单价\t\t商品数量\t\t折扣\t\t商品所属类型\t\t商品状态");
                for (ProductInfo info : productInfo2) {
                    System.out.println(info.getId() + "\t\t\t" + info.getPname() + "\t" + info.getPrice() + "\t\t" + info.getNum() + "\t\t" + info.getDiscount() + "\t\t" + ProductInfoUtils.typeNameConversion(info.getTypeId()) + "\t\t\t" + ProductInfoUtils.stateConversion(info.getState()));
                }
            }


            if (inputName.equalsIgnoreCase("all")) {
                //查询所有
                allProductInfo = productInfoDao.findAllProductInfo();
                findAllProductInfo();

            }

            if (productInfo2.isEmpty() && productInfo.isEmpty() && allProductInfo == null) {
                System.out.println("没有该结果");
            }
        }

    }

    public void findAllProductInfo() {
        System.out.println("商品编号\t\t商品名\t\t单价\t\t商品数量\t\t折扣\t\t商品所属类型\t\t商品状态");
        for (ProductInfo info : productInfoDao.findAllProductInfo()) {
            System.out.println(info.getId() + "\t\t\t" + info.getPname() + "\t" + info.getPrice() + "\t\t" + info.getNum() + "\t\t" + info.getDiscount() + "\t\t" + ProductInfoUtils.typeNameConversion(info.getTypeId()) + "\t\t\t" + ProductInfoUtils.stateConversion(info.getState()));
        }
    }

    public void deleteProductInfo() {
        List<ProductInfo> allProductInfo = productInfoDao.findAllProductInfo();
        findAllProductInfo();
        System.out.println("请输入要删除的ID");
        Scanner scanner2 = new Scanner(System.in);
        try {
            int id = scanner2.nextInt();
            OrderDetails orderDetails = productInfoDao.findOrderDetails(id);
            if (orderDetails==null){
                Integer integer = productInfoDao.deleteProductInfo(id);
                if (integer > 0) {
                    System.out.println("删除成功");
                }
            }else {
                System.out.println("改商品不能被删除,已有订单");
            }


        } catch (Exception e) {
            System.out.println("请重新输入");
        }

    }
}
