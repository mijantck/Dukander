package com.mrsoftit.dukander;

public class MyInfoNote {

    private String myid;
    private String dukanName;
    private String dukanphone;
    private String dukanaddress;
    private String dukanaddpicurl;
    private boolean firsttime;
    public MyInfoNote(){}

    public MyInfoNote(String myid, String dukanName, String dukanphone, String dukanaddress, String dukanaddpicurl,boolean firsttime) {
        this.myid = myid;
        this.dukanName = dukanName;
        this.dukanphone = dukanphone;
        this.dukanaddress = dukanaddress;
        this.dukanaddpicurl = dukanaddpicurl;
        this.firsttime = firsttime;
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
}
