package com.mrsoftit.dukander;

public class ProductNote {
    private String proId;
    private String proName;
    private double proPrice;
    private double proBuyPrice;
    private String userID;
    private double proQua;
    private double proMin;
    private String proImgeUrl;
    private String invoiseid;
    private  int invoice;
    private String unkid;

    public ProductNote(){}

    public ProductNote(String proId, String proName, double proPrice,double proBuyPrice, double proQua, double proMin, String proImgeUrl) {
        this.proId = proId;
        this.proName = proName;
        this.proPrice = proPrice;
        this.proBuyPrice = proBuyPrice;
        this.proQua = proQua;
        this.proMin = proMin;
        this.proImgeUrl = proImgeUrl;
    }

    public ProductNote(String proId, String proName, double proPrice,double proBuyPrice, double proQua, double proMin) {
        this.proId = proId;
        this.proName = proName;
        this.proPrice = proPrice;
        this.proBuyPrice = proBuyPrice;
        this.proQua = proQua;
        this.proMin = proMin;
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

    public double getProBuyPrice() {
        return proBuyPrice;
    }

    public void setProBuyPrice(double proBuyPrice) {
        this.proBuyPrice = proBuyPrice;
    }

    public double getProQua() {
        return proQua;
    }

    public void setProQua(double proQua) {
        this.proQua = proQua;
    }

    public double getProMin() {
        return proMin;
    }

    public void setProMin(double proMin) {
        this.proMin = proMin;
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
