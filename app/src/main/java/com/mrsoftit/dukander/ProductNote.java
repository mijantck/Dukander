package com.mrsoftit.dukander;

public class ProductNote {
    private String proId;
    private String proName;
    private double proPrice;
    private String userID;
    private int proQua;
    private int proMin;
    private String proImgeUrl;
    private String invoiseid;
    private  int invoice;
    private String unkid;

    public ProductNote(){}

    public ProductNote(String proId, String proName, double proPrice, int proQua, int proMin, String proImgeUrl) {
        this.proId = proId;
        this.proName = proName;
        this.proPrice = proPrice;
        this.proQua = proQua;
        this.proMin = proMin;
        this.proImgeUrl = proImgeUrl;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public String getProId() {
        return proId;
    }

    public String getProName() {
        return proName;
    }

    public double getProPrice() {
        return proPrice;
    }

    public int getProQua() {
        return proQua;
    }

    public int getProMin() {
        return proMin;
    }

    public String getProImgeUrl() {
        return proImgeUrl;
    }

    public String getInvoiseid() {
        return invoiseid;
    }

    public void setInvoiseid(String invoiseid) {
        this.invoiseid = invoiseid;
    }

    public int getInvoice() {
        return invoice;
    }

    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }

    public String getUnkid() {
        return unkid;
    }

    public void setUnkid(String unkid) {
        this.unkid = unkid;
    }
}
