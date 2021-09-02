package dao;

import bean.Vip;

import java.util.List;

public interface VipManageDao{
    /**
     * 添加用户
     * @param vip
     * @return
     */
    Integer addVip(Vip vip);

    /**
     * 修改用户
     * @param vip
     * @return
     */
    Integer upVip(Vip vip);

    /**
     * 查找用户
     * @param id
     * @return
     */
    Vip findVip(Integer id);

    /**
     * 模糊搜索用户
     * @param name
     * @return
     */
    List<Vip> findVip(String name);

    /**
     * 查找所有用户
     * @return
     */
    List<Vip> findAllVip();

    /**
     * 删除用户
     * @param id
     * @return
     */
    Integer deleteVip(Integer id);

    /**
     * 充值账号
     * @param id
     * @param money
     * @return
     */
    Integer payVip(Integer id,Float money);

    /**
     * 查询卡号是否重复
     * @param cardnumber
     * @return
     */
    Vip findVipCard(String cardnumber);

}
