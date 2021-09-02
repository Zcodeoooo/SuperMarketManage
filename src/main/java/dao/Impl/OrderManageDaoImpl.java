package dao.impl;

import bean.OrderDetails;
import bean.OrderInfo;
import bean.Top;
import bean.Vip;
import dao.OrderManageDao;
import util.JDBCDBUtils;

import java.util.List;

public class OrderManageDaoImpl implements OrderManageDao {
    @Override
    public Integer addOrderInfo(OrderInfo orderInfos) {
        String sql = "insert into orderinfo values(null,?,?,NOW(),?,?)";
        return JDBCDBUtils.updateGetId(sql, orderInfos.getVipId(), orderInfos.getPrice(), orderInfos.getPayType(), orderInfos.getMoney());
    }

    @Override
    public Integer addOrderDetails(OrderDetails orderDetails) {
        String sql = "insert into orderdetails values(null,?,?,?,?)";
        return JDBCDBUtils.update(sql,orderDetails.getOrderId(),orderDetails.getProductId(),orderDetails.getNum(),orderDetails.getMoney());
    }

    @Override
    public Vip vipLogin(String card,String pass) {
        String sql = "SELECT * FROM vip WHERE cardnumber =? AND vipPass = ?";
        return JDBCDBUtils.get(sql, Vip.class, card, pass);
    }



    @Override
    public Integer subStock(Integer id, Integer num){
        String sql = "UPDATE product SET num=(num-?) WHERE id = ?";
        return JDBCDBUtils.update(sql,num,id);
    }

    public Integer subMoney(String card,Float money){
        String sql = "UPDATE vip SET money=(money-?) WHERE id = ?";
        return JDBCDBUtils.update(sql,money,card);
    }

    public Integer addJiFen(String card,Integer jifen){
        String sql = "UPDATE vip SET jifen=(jifen-?) WHERE id = ?";
        return JDBCDBUtils.update(sql,jifen,card);
    }

    @Override
    public List<OrderDetails> findOrderDetailsLog(Integer id){
        String sql = "SELECT * FROM orderdetails d WHERE productid=?";
        return JDBCDBUtils.find(sql,OrderDetails.class,id);
    }

    @Override
    public List<OrderInfo> findOrderInfoLog(Integer id){
        String sql = "SELECT * FROM orderinfo i WHERE i.id=?";
        return JDBCDBUtils.find(sql,OrderInfo.class,id);
    }

    @Override
    public List<OrderInfo> findCardOrderDetailsLog(Integer id) {
        String sql = "SELECT * FROM orderinfo i WHERE i.vipid=?";
        return JDBCDBUtils.find(sql,OrderInfo.class,id);
    }

    @Override
    public List<OrderDetails> findCardOrderInfoLog(Integer id) {
        String sql = "SELECT d.* FROM orderinfo i,orderdetails d WHERE i.vipid=? AND d.orderid=i.id";
        return JDBCDBUtils.find(sql,OrderDetails.class,id);
    }

    @Override
    public Object findSumTop() {
        String sql = "SELECT sum(num) FROM orderdetails d";
        return JDBCDBUtils.getSum(sql);
    }

    @Override
    public List<Top> findMonthCountTop(String money) {
        //DATE_FORMAT(orderdate,'%m')
        //SELECT p.pname,sum(d.num) NB FROM orderinfo o,orderdetails d,product p WHERE DATE_FORMAT(orderdate,'%m') LIKE '8' AND o.id=d.orderid AND d.productid=p.id group by productid ORDER BY NB desc Limit 0,10;
        String sql = "SELECT p.pname,sum(d.num) NB FROM orderinfo o,orderdetails d,product p WHERE substring(o.orderdate,6,2) LIKE ? AND o.id=d.orderid AND d.productid=p.id group by productid ORDER BY NB desc Limit 0,10";
        return JDBCDBUtils.find(sql,Top.class,money);
    }

    @Override
    public List<Top> findTypeCountTop() {
        //SELECT p.pname,sum(d.num) NB FROM orderdetails d,product p WHERE p.id=d.productid group by productid ORDER BY NB desc Limit 0,10
        String sql = "SELECT t.typename pname,sum(d.num) NB FROM orderdetails d,product p,producttype t WHERE p.id=d.productid AND p.typeid=t.id group by typeid ORDER BY NB desc Limit 0,10";
        return JDBCDBUtils.find(sql,Top.class);
    }
}
