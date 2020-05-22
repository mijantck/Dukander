package com.mrsoftit.dukander;

public class MyInfoNote {

    private String myid;
    private String dukanName;
    private String dukanphone;
    private String dukanaddress;
    private String dukanaddpicurl;
    private boolean firsttime;
    private String picName;
    private double invesment;
    private double withdrow;
    private double activeBalance;
    private double totalpaybil;
    private int date;
    private String investmentDeleils;
    private String withdrowDeleils;
    private String productname;
    private double product;

    public MyInfoNote(){}

    public MyInfoNote(String myid, String dukanName, String dukanphone, String dukanaddress, String dukanaddpicurl, boolean firsttime, String picName, double invesment, double activeBalance, double totalpaybil,int date) {
        this.myid = myid;
        this.dukanName = dukanName;
        this.dukanphone = dukanphone;
        this.dukanaddress = dukanaddress;
        this.dukanaddpicurl = dukanaddpicurl;
        this.firsttime = firsttime;
        this.picName = picName;
        this.invesment = invesment;
        this.activeBalance = activeBalance;
        this.totalpaybil = totalpaybil;
        this.date = date;
    }

    public MyInfoNote(String myid, String dukanName, String dukanphone, String dukanaddress,  boolean firsttime, String picName, double invesment, double activeBalance, double totalpaybil,int date) {
        this.myid = myid;
        this.dukanName = dukanName;
        this.dukanphone = dukanphone;
        this.dukanaddress = dukanaddress;
        this.firsttime = firsttime;
        this.picName = picName;
        this.invesment = invesment;
        this.activeBalance = activeBalance;
        this.totalpaybil = totalpaybil;
        this.date = date;
    }

    public MyInfoNote(String productname,double product,String myid, Double invesment, int date, String investmentDeleils) {
       this.productname = productname;
       this.product = product;
        this.myid = myid;
        this.invesment = invesment;
        this.date = date;
        this.investmentDeleils = investmentDeleils;
    }

    public MyInfoNote(String myid, boolean firsttime, Double withdrow, int date, String withdrowDeleils) {
        this.myid = myid;
        this.firsttime = firsttime;
        this.withdrow = withdrow;
        this.date = date;
        this.withdrowDeleils = withdrowDeleils;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getMyid() {
        return myid;
    }

    public void setMyid(String myid) {
        this.myid = myid;
    }

    public String getDukanName() {
        return dukanName;
    }

    public void setDukanName(String dukanName) {
        this.dukanName = dukanName;
    }

    public String getDukanphone() {
        return dukanphone;
    }

    public void setDukanphone(String dukanphone) {
        this.dukanphone = dukanphone;
    }

    public String getDukanaddress() {
        return dukanaddress;
    }

    public void setDukanaddress(String dukanaddress) {
        this.dukanaddress = dukanaddress;
    }

    public String getDukanaddpicurl() {
        return dukanaddpicurl;
    }

    public void setDukanaddpicurl(String dukanaddpicurl) {
        this.dukanaddpicurl = dukanaddpicurl;
    }

    public boolean isFirsttime() {
        return firsttime;
    }

    public void setFirsttime(boolean firsttime) {
        this.firsttime = firsttime;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public Double getInvesment() {
        return invesment;
    }

    public void setInvesment(Double invesment) {
        this.invesment = invesment;
    }

    public Double getActiveBalance() {
        return activeBalance;
    }

    public void setActiveBalance(Double activeBalance) {
        this.activeBalance = activeBalance;
    }

    public Double getTotalpaybil() {
        return totalpaybil;
    }

    public void setTotalpaybil(Double totalpaybil) {
        this.totalpaybil = totalpaybil;
    }

    public Double getWithdrow() {
        return withdrow;
    }

    public void setWithdrow(Double withdrow) {
        this.withdrow = withdrow;
    }

    public String getInvestmentDeleils() {
        return investmentDeleils;
    }

    public void setInvestmentDeleils(String investmentDeleils) {
        this.investmentDeleils = investmentDeleils;
    }

    public String getWithdrowDeleils() {
        return withdrowDeleils;
    }

    public void setWithdrowDeleils(String withdrowDeleils) {
        this.withdrowDeleils = withdrowDeleils;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }


    public double getProduct() {
        return product;
    }

    public void setProduct(double product) {
        this.product = product;
    }
}
