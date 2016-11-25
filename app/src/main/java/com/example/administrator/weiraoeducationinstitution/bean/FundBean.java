package com.example.administrator.weiraoeducationinstitution.bean;

/**
 * Created by Administrator on 2016/4/29.
 */
public class FundBean {
    //    "id": "16",
//            "money": "10.00",
//            "type": "2",
//            "time": "2016-05-11 14:01:31",
//            "status": "0",
//            "agency": "1",
//            "cash_account": "1",
//            "balance": "590.00"
    private String id;
    private String type;//1收入 2提现
    private String time;
    private String money;//金额
    private String balance;//余额
    private String status;//提款状态0为提款失败
    private String agency;
    private String cash_account;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getCash_account() {
        return cash_account;
    }

    public void setCash_account(String cash_account) {
        this.cash_account = cash_account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
