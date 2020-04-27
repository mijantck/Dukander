package com.mrsoftit.dukander;

public class TotalSaleNote {
    private int date;
    private String itemName;
    private boolean paid;
    private String saleProductId;
    private double quantedt;
    private double totalPrice;

    public TotalSaleNote(){}

    public TotalSaleNote(int date, String itemName, boolean paid, String saleProductId, double quantedt, double totalPrice) {
        this.date = date;
        this.itemName = itemName;
        this.paid = paid;
        this.saleProductId = saleProductId;
        this.quantedt = quantedt;
        this.totalPrice = totalPrice;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getSaleProductId() {
        return saleProductId;
    }

    public void setSaleProductId(String saleProductId) {
        this.saleProductId = saleProductId;
    }

    public double getQuantedt() {
        return quantedt;
    }

    public void setQuantedt(double quantedt) {
        this.quantedt = quantedt;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
