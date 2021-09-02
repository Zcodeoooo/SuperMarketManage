package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Cart implements Serializable {
    private static final long serialVersionUID = -1778383147510199946L;
    private String name;
    private Integer id;
    private Integer num;
    private Float price;
    private Integer discount;
}
