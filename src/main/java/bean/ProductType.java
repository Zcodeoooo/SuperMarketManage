package bean;

import lombok.Data;

import java.util.Date;



/**
 * @author Administrator
 */
@Data


public class ProductType {
    private Integer id;
    private Integer parentId;
    //父类的类型名称
    private String parentTypeName;
    private String typename;
    private Integer flagParent;
    private Date createTime;
    private Date updateTime;

    public ProductType() {
    }

    public ProductType(Integer id, Integer parentId, String typename, Integer flagParent, Date createTime, Date updateTime) {
        this.id = id;
        this.parentId = parentId;
        this.typename = typename;
        this.flagParent = flagParent;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ProductType(Integer id, Integer parentId, String parentTypeName, String typename, Integer flagParent, Date createTime, Date updateTime) {
        this.id = id;
        this.parentId = parentId;
        this.parentTypeName = parentTypeName;
        this.typename = typename;
        this.flagParent = flagParent;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


}
