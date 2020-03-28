package com.mrsoftit.dukander;

public class SaleProductCutomerNote {

   private String SaleProductId;
   private String itemName;
   private Double price;
   private Double quantedt;
   private Double totalPrice;
   private int date;
   private  int invoiceNumber;
   private  String invoiceId;

public SaleProductCutomerNote(){}

    public SaleProductCutomerNote(String saleProductId, String itemName, Double price, Double quantedt, Double totalPrice, int date, int invoiceNumber) {
        SaleProductId = saleProductId;
        this.itemName = itemName;
        this.price = price;
        this.quantedt = quantedt;
        this.totalPrice = totalPrice;
        this.date = date;
        this.invoiceNumber = invoiceNumber;
        this.invoiceId = invoiceId;
    }


    public String getSaleProductId() {
        return SaleProductId;
    }

    public String getItemName() {
        return itemName;
    }

    public Double getPrice() {
        return price;
    }

    public Double getQuantedt() {
        return quantedt;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public int getDate() {
        return date;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getInvoiceId() {
        return invoiceId;
    }
}
