package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;


/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    private Integer id;
    @NotNull(message = "名字不能为空")
    private String pname;
    @NotNull(message = "价钱不能为空")
    private Float price;
    @NotNull(message = "数量不能为空")
    private Integer num;
    /**
     * 1~10之间
     */
    @Range(min = 1, max = 10, message = "折扣必须在1-10之间")
    private Integer discount;
    @NotNull(message = "类型不能为空")
    private Integer typeId;
    /**
     * 1为上架，2为下架，3为删除
     */
    @Range(min = 1, max = 3, message = "商品状态必须在1-3之间")
    private int state;

}
