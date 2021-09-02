package dao;

import bean.OrderDetails;
import bean.ProductInfo;

import java.util.List;

public interface ProductInfoDao {
    /**
     * 添加商品信息
     * @param productType
     * @return
     */
    Integer addProductInfo(ProductInfo productType);

    /**
     * 更新商品信息
     * @param productType
     * @return
     */
    Integer upProductInfo(ProductInfo productType);

    /**
     * 搜索单个商品信息
     * @param id
     * @return
     */
    ProductInfo findProductInfo(Integer id);

    /**
     * 模糊匹配的商品
     * @param name
     * @return
     */
    List<ProductInfo> findProductInfo(String name);

    /**
     * 删除商品信息
     * @param id
     * @return
     */
    Integer deleteProductInfo(Integer id);

    /**
     * 显示商品类型信息
     * @param name
     * @return
     */
    List<ProductInfo> findProductTypeNameInfo(String name);

    /**
     * 显示所有商品信息
     * @return
     */
    List<ProductInfo> findAllProductInfo();

    /**
     * 查询是否有订单?
     */
    OrderDetails findOrderDetails(Integer id);

}
