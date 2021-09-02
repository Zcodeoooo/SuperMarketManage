package service;

import bean.Vip;
import dao.impl.VipManageDaoImpl;
import util.ProductInfoUtils;
import util.ProductTypeUtils;

import java.util.List;
import java.util.Scanner;


/**
 * @author Administrator
 */
public class VipManageService {
    private VipManageDaoImpl vipManageDao;
    private Scanner scanner;

    public VipManageService(VipManageDaoImpl vipManageDao,Scanner scanner) {
        this.vipManageDao = vipManageDao;
        this.scanner = scanner;
    }


    public void findAll() {
        System.out.println("会员编号\t\t会员名\t\t密码\t\t\t联系方式\t\t\t积分\t\t余额\t\t\t\t\t创建时间\t\t\t\t修改时间");
        for (Vip vip : vipManageDao.findAllVip()) {
            System.out.println(vip.getId() + "\t\t\t" + vip.getVipName() + "\t\t\t" + vip.getVipPass() + "\t\t\t" + vip.getIphone() + "\t\t\t" + vip.getJiFen() + "\t" + vip.getMoney() + "\t\t\t" + vip.getCreateTime() + "\t\t\t" + vip.getUpdateTime());
        }
    }

    public void showOneVip(Vip vip) {
        System.out.println("会员编号\t\t会员名\t\t密码\t\t\t联系方式\t\t\t积分\t\t余额\t\t\t\t\t创建时间\t\t\t\t修改时间");
        System.out.println(vip.getId() + "\t\t\t" + vip.getVipName() + "\t\t\t" + vip.getVipPass() + "\t\t\t" + vip.getIphone() + "\t\t\t" + vip.getJiFen() + "\t" + vip.getMoney() + "\t\t\t" + vip.getCreateTime() + "\t\t\t" + vip.getUpdateTime());

    }

    public void showFuzzyVip(List<Vip> vipList) {
        System.out.println("会员编号\t\t会员名\t\t密码\t\t\t联系方式\t\t\t积分\t\t余额\t\t\t\t\t创建时间\t\t\t\t修改时间");
        for (Vip vip : vipList) {
            System.out.println(vip.getId() + "\t\t\t" + vip.getVipName() + "\t\t\t" + vip.getVipPass() + "\t\t\t" + vip.getIphone() + "\t\t\t" + vip.getJiFen() + "\t" + vip.getMoney() + "\t\t\t" + vip.getCreateTime() + "\t\t\t" + vip.getUpdateTime());

        }

    }

    public void findVip() {
        System.out.println("请选择 1.查询所有 2.查询单个会员");
        try {
            int choose = scanner.nextInt();
            if (choose == 1) {
                findAll();
            }
            if (choose == 2) {
                System.out.println("请输入会员id,或姓名(姓名可模糊查询)");
                String i = scanner.next();
                try {
                    int inputId = Integer.parseInt(i);
                    Vip vip = vipManageDao.findVip(inputId);
                    showOneVip(vip);
                } catch (Exception e) {
                    List<Vip> vip = vipManageDao.findVip(i);
                    showFuzzyVip(vip);
                }

            }
        } catch (Exception e) {
            System.out.println("输入错误");
        }


    }

    public void addVip() {
        Scanner scanner2 = new Scanner(System.in);
        try {
            System.out.println("请输入卡号");
            String inputCard = scanner2.next();
            Vip vipCard = vipManageDao.findVipCard(inputCard);
            while (vipCard!=null){
                System.out.println("请重新输入");
                inputCard = scanner2.next();
                vipCard = vipManageDao.findVipCard(inputCard);
            }
            System.out.println("请输入会员姓名,未办理会员请填入'未办理会员'");
            String inputName = scanner2.next();
            if (!"未办理会员".equals(inputName)) {
                System.out.println("请输入密码");
                String inputPass = scanner2.next();
                inputPass = ProductInfoUtils.passConversion(inputPass);
                System.out.println("请输入联系方式");
                String inputIphone = scanner2.next();
                System.out.println("请输入积分");
                int inputJiFen = scanner2.nextInt();
                System.out.println("请输入余额");
                float inputMoney = scanner2.nextFloat();
                if (inputJiFen > 0 && inputMoney > 0) {
                    Vip vip = new Vip(null, inputCard, inputName, inputPass, inputIphone, inputJiFen, inputMoney, ProductTypeUtils.date(), null);
                    vipManageDao.addVip(vip);
                } else {
                    System.out.println("输入错误");
                }
            }
        } catch (Exception e) {
            System.out.println("输入错误");
        }
    }

    public void deleteVip() {
        try {
            System.out.println("请先输入姓名查询编号后删除");
            String inputName = scanner.next();
            List<Vip> vip = vipManageDao.findVip(inputName);
            showFuzzyVip(vip);
            System.out.println("请输入编号");
            int inputId = scanner.nextInt();
            Integer integer = vipManageDao.deleteVip(inputId);
            if (integer > 0) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        } catch (Exception e) {

            System.out.println("输入错误");
        }

    }

    public void upVip() {
        Scanner scanner2 = new Scanner(System.in);
//        try {
            System.out.println("请输入要修改的会员编号");
            int inputId = scanner2.nextInt();
            Vip vip = vipManageDao.findVip(inputId);
            if (vip!=null){
                System.out.println("请输入要修改的卡号,默认请回车("+vip.getCardNumber()+")");
                String inputCard = scanner2.nextLine();
                System.out.println("请输入要修改的会员姓名,默认请回车"+vip.getVipName()+")");
                String inputName = scanner2.nextLine();
                System.out.println("请输入要修改的密码,默认请回车"+vip.getVipPass()+")");
                String inputPass = scanner2.nextLine();
                inputPass = ProductInfoUtils.passConversion(inputPass);
                System.out.println("请输入要修改的联系方式,默认请回车"+vip.getIphone()+")");
                String inputIphone = scanner2.nextLine();
                System.out.println("请输入要修改的积分,默认请回车"+vip.getJiFen()+")");
                String inputJiFen = scanner2.nextLine();
                System.out.println("请输入要修改的余额,默认请回车"+vip.getMoney()+")");
                String inputMoney = scanner2.nextLine();


                if (!"".equals(inputCard)){
                    vip.setCardNumber(inputCard);
                }
                if (!"".equals(inputName)){
                    vip.setVipName(inputName);
                }
                if (!"".equals(inputPass)){
                    vip.setVipPass(inputPass);
                }
                if (!"".equals(inputIphone)){
                    vip.setIphone(inputIphone);
                }
                if (!"".equals(inputJiFen)){
                    vip.setJiFen(Integer.parseInt(inputJiFen));
                }
                if (!"".equals(inputMoney)){
                    vip.setMoney(Float.parseFloat(inputMoney));
                }

                Integer integer = vipManageDao.upVip(vip);
                if (integer>0){
                    System.out.println("修改成功");
                }else {
                    System.out.println("修改失败");
                }
            }
    }

    public void payVip() {
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("请输入会员编号");
        try {
            int inputId = scanner2.nextInt();
            System.out.println("请输入要充值的金额");
            float inputMoney = scanner2.nextFloat();
            if (inputMoney > 0) {
                Integer integer = vipManageDao.payVip(inputId, inputMoney);
                if (integer > 0) {
                    System.out.println("充值成功");
                } else {
                    System.out.println("充值失败");
                }
            } else {
                System.out.println("输入错误");
            }
        } catch (Exception e) {
            System.out.println("编号错误");
        }

    }


}
