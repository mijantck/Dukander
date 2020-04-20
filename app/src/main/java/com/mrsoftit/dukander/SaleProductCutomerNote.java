package com.mrsoftit.dukander;

public class SaleProductCutomerNote {

   private String saleProductId;
    String itemName;
    Double price;
    Double quantedt;
    Double totalPrice;
    int date;
    int invoiceNumber;
    String invoiceId;
    Boolean update ;
    String TotalSaleid;

public SaleProductCutomerNote(){}

    public SaleProductCutomerNote(String saleProductId, String itemName, Double price, Double quantedt, Double totalPrice, int date, int invoiceNumber,Boolean update ) {
       this.saleProductId = saleProductId;
        this.itemName = itemName;
        this.price = price;
        this.quantedt = quantedt;
        this.totalPrice = totalPrice;
        this.date = date;
        this.invoiceNumber = invoiceNumber;
        this.update = update;
    }

  /*  public SaleProductCutomerNote(String saleProductId, String itemName, Double price, Double quantedt, Double totalPrice, int date, int invoiceNumber, String totalSaleid) {
        SaleProductId = saleProductId;
        this.itemName = itemName;
        this.price = price;
        this.quantedt = quantedt;
        this.totalPrice = totalPrice;
        this.date = date;
        this.invoiceNumber = invoiceNumber;
        TotalSaleid = totalSaleid;
    }*/

    public String getSaleProductId() {
        return saleProductId;
    }

    public void setSaleProductId(String saleProductId) {
        this.saleProductId = saleProductId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantedt() {
        return quantedt;
    }

    public void setQuantedt(Double quantedt) {
        this.quantedt = quantedt;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }
}
