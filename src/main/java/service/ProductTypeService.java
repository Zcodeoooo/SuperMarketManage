package service;

import bean.ProductType;
import dao.ProductTypeDao;
import util.ProductTypeUtils;

import java.util.List;
import java.util.Scanner;

/**
 * ClassName:ProductService
 *
 * @version 0.1
 * @Description: <br/>
 * @date:ProductService16:30 <br/>
 * @authorAdministrator <br/>
 * @since JDK 1.8
 */
public class ProductTypeService {
    private ProductType productType;
    private ProductTypeDao productDao;
    private Scanner scanner;

    public ProductTypeService(ProductTypeDao productDao,Scanner scanner) {

        this.productDao = productDao;
        this.scanner = scanner;
    }

    public void addProductType() {
        System.out.println("请输入类型名称");
        String typeName = scanner.next();
        System.out.println("请添加一级分类请输入1,添加二级分类请输入2");
        int inputSort = scanner.nextInt();
        if (inputSort == 1) {
            productType = new ProductType(null, 0, typeName, 1, ProductTypeUtils.date(), ProductTypeUtils.date());
            productDao.addProductType(productType);
            System.out.println("添加成功");
        } else if (inputSort == 2) {
            System.out.println("请选择二级分类对应的一级分类");
            //显示一级目录,并选择
            List<ProductType> allId = productDao.findAllId();
            System.out.println("id\t名称");
            for (ProductType type : allId) {
                System.out.println(type.getId() + "\t" + type.getTypename());
            }
            System.out.println("请输入id");
            int inputId = scanner.nextInt();
            Boolean aBoolean = parentIdLimit(inputId);
            if (aBoolean) {
                productType = new ProductType(null, inputId, typeName, 0, ProductTypeUtils.date(), ProductTypeUtils.date());
                productDao.addProductType(productType);
                System.out.println("添加成功");
            } else {
                System.out.println("添加失败");
            }
        }
    }

    public void findAllId(){
        List<ProductType> allId = productDao.findAllId();
        System.out.println("编号\t 类型名称");
        for (ProductType type : allId) {
            System.out.println(type.getId()+"\t"+type.getTypename());
        }
    }



    public void upProductType() {
        showTwoProductType();
        //显示一级目录,并选择

        ProductType productType = findProductType();
        if (productType != null) {
            Scanner scanner999 = new Scanner(System.in);
            findAllId();
            int inputParentId=-1;
            System.out.println("请输入类型编号,一级目录请输入0,默认请回车");
            String s = scanner999.nextLine();
            if ("".equals(s)){

            }else {
                try {
                    inputParentId = Integer.parseInt(s);
                }catch (Exception e){
                    System.out.println("请输入数字");
                    upProductType();
                }
            }

            if (inputParentId == -1) {
                System.out.println("请输入商品类型名称,默认请回车");
                Scanner scanner2 = new Scanner(System.in);
                String inputName = scanner2.nextLine();
                if ("".equals(inputName)) {
                    productType.setTypename(inputName);
                    productDao.upProductType(productType);
                    System.out.println("修改成功");
                    showTwoProductType();
                } else {
                    productType.setTypename(inputName);
                    productDao.upProductType(productType);
                    System.out.println("修改成功");
                    showTwoProductType();
                }

            } else {
                Boolean aBoolean = parentIdLimit(inputParentId);
                if (aBoolean) {
                    System.out.println("请输入商品类型名称,默认请回车");
                    Scanner scanner2 = new Scanner(System.in);
                    String inputName = scanner2.nextLine();
                    if ("".equals(inputName)) {
                        productType.setFlagParent(0);
                        productType.setParentId(inputParentId);
                        productDao.upProductType(productType);
                        System.out.println("修改成功");
                        showTwoProductType();

                    } else {
                        productType.setFlagParent(0);
                        productType.setParentId(inputParentId);
                        productType.setTypename(inputName);
                        productDao.upProductType(productType);
                        System.out.println("修改成功");
                        showTwoProductType();
                    }
                //父类型为0时设置为父标志
                }else if (inputParentId==0){
                    productType.setParentId(inputParentId);
                    productType.setFlagParent(1);
                    productDao.upProductType(productType);
                    System.out.println("修改成功");
                    showTwoProductType();
                }
                else {
                    System.out.println("输入错误");
                }
            }
        }
    }

    public void findChooseProductType(){
        System.out.println("请选择 1.查询一级目录 2.查询二级目录 2.查询单个类型");
        int choose = scanner.nextInt();
        if (choose==1){
            showParentProductType();
        }
        if (choose==2){
            showTwoProductType();
        }
        if (choose==3){
            findProductType();
        }
    }





    public ProductType findProductType() {
        int id;
        for (; ; ) {
            System.out.println("请输入类型id");
            try {
                Scanner scanner1 = new Scanner(System.in);
                id = scanner1.nextInt();
                break;
            } catch (Exception e) {
                System.out.print("输入数据类型错误！你必须输入数值数据！\n");
            }
        }

        ProductType productType = productDao.findProductType(id);
        if (productType == null) {
            System.out.println("没有该id");
            return null;
        }
        System.out.println("编号\t父id\t类型名称\t是否为父类型\t创建时间\t\t\t\t修改时间");
        System.out.println(+productType.getId() + "\t" + productType.getParentId() + "\t\t" + productType.getTypename() + "\t\t\t" + ProductTypeUtils.textConversion(productType.getFlagParent()) + "\t\t" + ProductTypeUtils.dateConversion(productType.getCreateTime()) + "\t\t\t" + ProductTypeUtils.dateConversion(productType.getUpdateTime()));
        return productType;
    }

    public void deleteProductType() {
        System.out.println("商品类型表");
        showTwoProductType();
        ProductType productType = findProductType();
        if (productType != null) {
            Integer integer = productDao.deleteProductType(productType.getId());
            if (integer > 0) {
                System.out.println("删除成功");
            }
        } else {
            System.out.println("删除失败");
        }

    }

    public void showParentProductType(){
        List<ProductType> allProductType = productDao.findParentAll();
        System.out.println("编号\t一级名称\t是否为父类型\t创建时间\t\t\t\t修改时间");
        for (ProductType productType : allProductType) {
            System.out.println(+productType.getId() + "\t" + productType.getTypename() + "\t\t\t" + ProductTypeUtils.textConversion(productType.getFlagParent()) + "\t\t" + ProductTypeUtils.dateConversion(productType.getCreateTime()) + "\t\t\t" + ProductTypeUtils.dateConversion(productType.getUpdateTime()));
        }
    }

    public void showTwoProductType() {
        List<ProductType> allProductType = productDao.findAllProductType();
        System.out.println("编号\t二级名称\t一级名称\t是否为父类型\t创建时间\t\t\t\t修改时间");
        for (ProductType productType : allProductType) {
            System.out.println(+productType.getId() + "\t" + productType.getTypename() + "\t\t" + productType.getParentTypeName() + "\t\t\t" + ProductTypeUtils.textConversion(productType.getFlagParent()) + "\t\t" + ProductTypeUtils.dateConversion(productType.getCreateTime()) + "\t\t\t" + ProductTypeUtils.dateConversion(productType.getUpdateTime()));
        }
    }

    public Boolean parentIdLimit(Integer inputId) {
        List<ProductType> allId = productDao.findAllId();
        for (ProductType type : allId) {
            if (type.getId().equals(inputId)) {
                System.out.println(inputId);
                return true;
            }
        }
        return false;
    }


}