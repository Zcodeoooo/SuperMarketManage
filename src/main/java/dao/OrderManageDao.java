package dao;

import bean.OrderDetails;
import bean.OrderInfo;
import bean.Top;
import bean.Vip;

import java.util.List;

public interface OrderManageDao {
    Integer addOrderInfo(OrderInfo orderInfos);
    Integer subStock(Integer id,Integer num);
    Integer addOrderDetails(OrderDetails orderDetails);
    Vip vipLogin(String card, String pass);

    List<OrderDetails> findOrderDetailsLog(Integer id);
    List<OrderInfo> findOrderInfoLog(Integer id);

    List<OrderInfo> findCardOrderDetailsLog(Integer id);
    List<OrderDetails> findCardOrderInfoLog(Integer id);

    /**
     * 总销量
     * @return
     */
    Object findSumTop();

    /**
     * 按月份统计销量前10的商品
     * @param money
     * @return
     */
    List<Top> findMonthCountTop(String money);

    /**
     * 按商品类别统计销量前10
     * @return
     */
    List<Top> findTypeCountTop();

}
