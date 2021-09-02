package dao.impl;

import bean.Vip;
import dao.VipManageDao;
import util.JDBCDBUtils;

import java.util.List;

public class VipManageDaoImpl implements VipManageDao {


    @Override
    public Integer addVip(Vip vip) {
        String sql = "insert into vip values(null,?,?,?,?,?,?,?,?)";
        return JDBCDBUtils.update(sql,vip.getCardNumber(),vip.getVipName(),vip.getVipPass(),vip.getIphone(),vip.getJiFen(),vip.getMoney(),vip.getCreateTime(),vip.getUpdateTime());
    }

    @Override
    public Integer upVip(Vip vip) {
        String sql = "UPDATE vip SET cardnumber=?,vipname=?,vipPass=?,iphone=?,jifen=?,money=? WHERE id=?";
        return JDBCDBUtils.update(sql,vip.getCardNumber(),vip.getVipName(),vip.getVipPass(),vip.getIphone(),vip.getJiFen(),vip.getMoney(),vip.getId());
    }

    @Override
    public Vip findVip(Integer id) {
        String sql = "SELECT * FROM vip WHERE id = ?";
        return JDBCDBUtils.get(sql,Vip.class,id);
    }
    //"%"?"%"
    @Override
    public List<Vip> findVip(String name) {
        String sql = "SELECT * FROM vip WHERE vipname LIKE \"%\"?\"%\"";
        return JDBCDBUtils.find(sql,Vip.class,name);
    }


    @Override
    public Vip findVipCard(String cardnumber) {
        String sql = "SELECT * FROM vip WHERE cardnumber=?";
        return JDBCDBUtils.get(sql,Vip.class,cardnumber);
    }



    @Override
    public List<Vip> findAllVip() {
        String sql = "SELECT * FROM vip";
        return JDBCDBUtils.find(sql, Vip.class);
    }

    @Override
    public Integer deleteVip(Integer id) {
        String sql = "DELETE FROM vip WHERE id = ?";
        return JDBCDBUtils.update(sql,id);
    }

    @Override
    public Integer payVip(Integer id,Float money) {
        String sql = "UPDATE vip SET money=(?+money) WHERE id = ?";
        return JDBCDBUtils.update(sql,money,id);
    }




}
