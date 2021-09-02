package bean;

import constants.NumRange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

//订单表
public class OrderInfo {
    /**
     * id
     */
    private Integer id;
    /**
     * 会员vip卡id
     */
    private Integer vipId;
    /**
     * 订单总金额
     */
    private Float price;
    /**
     * 下单时间
     */
    private Date orderDate;
    /**
     * 支付类型1 现金 2 会员卡
     */
    @NumRange(Min = 1,Max = 2,Message = "只能1-2")
    private Integer payType;
    /**
     * 付款现金
     */
    private Float money;
}
