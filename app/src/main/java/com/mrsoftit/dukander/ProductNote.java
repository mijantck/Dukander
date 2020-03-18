package com.mrsoftit.dukander;

public class ProductNote {
    private String proId;
    private String proName;
    private double proPrice;
    private int proQua;
    private int proMin;
    private String proImgeUrl;

    public ProductNote(){}

    public ProductNote(String proId, String proName, double proPrice, int proQua, int proMin, String proImgeUrl) {
        this.proId = proId;
        this.proName = proName;
        this.proPrice = proPrice;
        this.proQua = proQua;
        this.proMin = proMin;
        this.proImgeUrl = proImgeUrl;
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
}
