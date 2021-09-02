package dao.impl;

import bean.ProductType;
import dao.ProductTypeDao;
import util.JDBCDBUtils;

import java.util.Date;
import java.util.List;

/**
 * ClassName:ProductDaoImpl
 *
 * @version 0.1
 * @Description: <br/>
 * @date:ProductDaoImpl16:33 <br/>
 * @authorAdministrator <br/>
 * @since JDK 1.8
 */
public class ProductTypeDaoImpl implements ProductTypeDao {



    @Override
    public Integer addProductType(ProductType productType) {
        String sql = "INSERT INTO producttype VALUES (null,?,?,?,?,null)";
        return JDBCDBUtils.update(sql, productType.getParentId(), productType.getTypename(), productType.getFlagParent(), productType.getCreateTime());
    }



    @Override
    public Integer upProductType(ProductType productType) {
        String sql="UPDATE producttype SET typename=?,parentid=?,flagparent=?,updateTime=? WHERE id=?";
        return JDBCDBUtils.update(sql,productType.getTypename(),productType.getParentId(),productType.getFlagParent(),new Date(),productType.getId());
    }

    public Integer upProductTypeDefault(ProductType productType) {
        String sql="UPDATE producttype SET typename=?,createTime=? WHERE id=?";
        return JDBCDBUtils.update(sql,productType.getTypename(),productType.getCreateTime(),productType.getId());
    }


    @Override
    public ProductType findProductType(Integer id) {
        String sql = "SELECT * from producttype WHERE id =?";
        return JDBCDBUtils.get(sql, ProductType.class, id);
    }

    @Override
    public Integer deleteProductType(Integer id) {
        String sql = "DELETE FROM producttype WHERE id = ?";
        return JDBCDBUtils.update(sql,id);
    }

    @Override
    public List<ProductType> findAllProductType() {
        String sql = "SELECT p2.*,p1.typename parentTypeName FROM producttype p1,producttype p2 WHERE p1.id=p2.Parentid";
        return JDBCDBUtils.find(sql, ProductType.class);
    }

    @Override
    public List<ProductType> findAllId() {
        String sql = "SELECT id,typename from producttype WHERE flagparent=0";
        return JDBCDBUtils.find(sql,ProductType.class);
    }

    @Override
    public Boolean findId(Integer id) {
        String sql = "SELECT * from producttype WHERE flagparent=0 and id = ?";
        List<Object> objects = JDBCDBUtils.find(sql, ProductType.class, id);
        if (!objects.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public List<ProductType> findParentAll() {
        String sql = "SELECT * from producttype WHERE flagparent=1";
        return JDBCDBUtils.find(sql,ProductType.class);
    }
}
