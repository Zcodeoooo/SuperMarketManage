package dao.impl;

import bean.OrderDetails;
import bean.ProductInfo;
import dao.ProductInfoDao;
import util.JDBCDBUtils;

import java.util.List;

public class ProductInfoDaoImpl implements ProductInfoDao {
    @Override
    public Integer addProductInfo(ProductInfo productInfo) {
        String sql = "insert into product values(null,?,?,?,?,?,?)";

        return JDBCDBUtils.update(sql,productInfo.getPname(),productInfo.getPrice(),productInfo.getNum(),productInfo.getDiscount(),productInfo.getTypeId(),productInfo.getState());
    }




    @Override
    public Integer upProductInfo(ProductInfo productInfo) {
        String sql="UPDATE product SET pname=?,price=?,num=?,discount=?,typeid=?,state=? WHERE id=?";
        return JDBCDBUtils.update(sql,productInfo.getPname(),productInfo.getPrice(),productInfo.getNum(),productInfo.getDiscount(),productInfo.getTypeId(),productInfo.getState(),productInfo.getId());
    }

    @Override
    public ProductInfo findProductInfo(Integer id) {
        String sql = "SELECT p.id,p.pname,p.price,p.num,p.discount,p.typeId,p.state from product p,producttype t WHERE t.id=p.typeid AND p.id=?";
        return JDBCDBUtils.get(sql, ProductInfo.class, id);
    }

    @Override
    public List<ProductInfo> findProductInfo(String name) {
        String sql = "SELECT p.id,p.pname,p.price,p.num,p.discount,p.typeid,p.state from product p,producttype t WHERE t.id=p.typeid AND p.pname LIKE \"%\"?\"%\"";
        return JDBCDBUtils.find(sql, ProductInfo.class, name);
    }

    @Override
    public List<ProductInfo> findProductTypeNameInfo(String name) {
        String sql = "SELECT p.id,p.pname,p.price,p.num,p.discount,p.typeid,p.state from product p,producttype t WHERE t.id=p.typeid AND t.typename=?";
        return JDBCDBUtils.find(sql, ProductInfo.class, name);
    }


    @Override
    public List<ProductInfo> findAllProductInfo(){
        String sql = "SELECT * from product";
        return JDBCDBUtils.find(sql, ProductInfo.class);
    }

    @Override
    public OrderDetails findOrderDetails(Integer id) {
        String sql = "SELECT * FROM orderdetails d WHERE productid=?";
        return JDBCDBUtils.get(sql, OrderDetails.class,id);
    }

    @Override
    public Integer deleteProductInfo(Integer id) {
        String sql = "DELETE FROM product WHERE id = ?";
        return JDBCDBUtils.update(sql,id);
    }
}
