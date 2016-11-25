package com.example.administrator.weiraoeducationinstitution.bean;

/**
 * Created by Administrator on 2016/5/6.
 */
public class BankCardBean {
    //    "id": "3",
//            "account_name": "",
//            "bank": "2",
//            "account_num": "6215584402001788223",
//            "agency": "1",
//            "bankName": "工商银行"
    private String id;
    private String account_name;
    private String bank;
    private String account_num;
    private String agency;
    private String bankName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount_num() {
        return account_num;
    }

    public void setAccount_num(String account_num) {
        this.account_num = account_num;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
