package bean;

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
public class Vip {
    private Integer id;
    private String cardNumber;
    private String vipName;
    private String vipPass;
    private String iphone;
    private Integer jiFen;
    private Float money;
    private Date createTime;
    private Date updateTime;
}
