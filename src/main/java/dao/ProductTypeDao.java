package dao;

import bean.ProductType;

import java.util.List;

/**
 * ClassName:GoodsDao
 *
 * @version 0.1
 * @Description: <br/>
 * @date:GoodsDao16:12 <br/>
 * @authorAdministrator <br/>
 * @since JDK 1.8
 */
public interface ProductTypeDao {
    /**
     * 添加商品类型
     * @param productType
     * @return
     */
    Integer addProductType(ProductType productType);

    /**
     * 更新商品类型
     * @param productType
     * @return
     */
    Integer upProductType(ProductType productType);

    /**
     * 查找单个商品id类型
     * @param id
     * @return
     */
    ProductType findProductType(Integer id);

    /**
     * 删除商品类型
     * @param id
     * @return
     */
    Integer deleteProductType(Integer id);

    /**
     * 查找所有商品类型
     * @return
     */
    List<ProductType> findAllProductType();

    /**
     * 查找所有二级商品类型
     * @return
     */
    List<ProductType> findAllId();

    /**
     * 查询二级目录商品id是否有
     * @return
     */
    Boolean findId(Integer id);

    /**
     * 查询一级目录
     */
    List<ProductType> findParentAll();

}
