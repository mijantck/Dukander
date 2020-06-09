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
    private String barCode;
    private String productCode;
    private String productPrivacy;
    private String productCategory;
    private int date;

    public ProductNote(){}

    public ProductNote(String proId, String proName, double proPrice,double proBuyPrice, double proQua, double proMin,
                       String proImgeUrl,String barCode,String productCode,String productPrivacy,String productCategory,int date) {
        this.proId = proId;
        this.proName = proName;
        this.proPrice = proPrice;
        this.proBuyPrice = proBuyPrice;
        this.proQua = proQua;
        this.proMin = proMin;
        this.proImgeUrl = proImgeUrl;
        this.barCode = barCode;
        this.productCode = productCode;
        this.productPrivacy = productPrivacy;
        this.productCategory = productCategory;
        this.date = date;
    }

    public ProductNote(String proId, String proName, double proPrice,double proBuyPrice, double proQua,
                       double proMin,String barCode,String productCode,String productPrivacy,String productCategory,int date) {
        this.proId = proId;
        this.proName = proName;
        this.proPrice = proPrice;
        this.proBuyPrice = proBuyPrice;
        this.proQua = proQua;
        this.proMin = proMin;
        this.barCode = barCode;
        this.productCode = productCode;
        this.productPrivacy = productPrivacy;
        this.productCategory = productCategory;
        this.date = date;
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


    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public void setProPrice(double proPrice) {
        this.proPrice = proPrice;
    }

    public void setProImgeUrl(String proImgeUrl) {
        this.proImgeUrl = proImgeUrl;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductPrivacy() {
        return productPrivacy;
    }

    public void setProductPrivacy(String productPrivacy) {
        this.productPrivacy = productPrivacy;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
