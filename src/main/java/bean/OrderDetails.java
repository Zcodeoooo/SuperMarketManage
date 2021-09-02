package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

//订单明细
public class OrderDetails {
    /**
     * id
     */
    private Integer id;
    /**
     * 订单id int （与订单信息表订单ID有关系） orderInfo订单信息表返回的自增id
     */
    @NotNull(message = "订单id不能为空")
    private Integer orderId;
    /**
     * 购买商品id
     */
    @NotNull(message="商品id不能为空")
    private Integer productId;
    /**
     * 购买商品数量
     */
    @NotNull(message="商品数量不能为空")
    private Integer num;
    /**  购买商品价格 */
    @NotNull(message="商品价格不能为空")

    private Float money;
}
