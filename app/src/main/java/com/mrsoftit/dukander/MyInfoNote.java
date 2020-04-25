package com.mrsoftit.dukander;

public class MyInfoNote {

    private String myid;
    private String dukanName;
    private String dukanphone;
    private String dukanaddress;
    private String dukanaddpicurl;
    private boolean firsttime;
    private String picName;
    private Double invesment;
    private Double activeBalance;

    public MyInfoNote(){}

    public MyInfoNote(String myid, String dukanName, String dukanphone, String dukanaddress, String dukanaddpicurl,boolean firsttime,String picName,Double activeBalance,Double invesment) {
        this.myid = myid;
        this.dukanName = dukanName;
        this.dukanphone = dukanphone;
        this.dukanaddress = dukanaddress;
        this.dukanaddpicurl = dukanaddpicurl;
        this.firsttime = firsttime;
        this.picName = picName;
        this.invesment = invesment;
        this.activeBalance = activeBalance;
    }

    public String getMyid() {
        return myid;
    }

    public String getDukanName() {
        return dukanName;
    }

    public String getDukanphone() {
        return dukanphone;
    }

    public String getDukanaddress() {
        return dukanaddress;
    }

    public String getDukanaddpicurl() {
        return dukanaddpicurl;
    }

    public boolean isFirsttime() {
        return firsttime;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public Double getActiveBalance() {
        return activeBalance;
    }

    public void setActiveBalance(Double activeBalance) {
        this.activeBalance = activeBalance;
    }

    public Double getInvesment() {
        return invesment;
    }

    public void setInvesment(Double invesment) {
        this.invesment = invesment;
    }
}
