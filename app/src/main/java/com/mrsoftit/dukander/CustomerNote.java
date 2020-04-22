package com.mrsoftit.dukander;

public class CustomerNote {
    private String customerIdDucunt;
    private String nameCUstomer;
    private String phone;
    private double taka;
    private String addres;
    private String imageUrl;
    private double lastTotal;
    private double pdfTotal;

    public  CustomerNote(){}

    public CustomerNote(String customerIdDucunt, String nameCUstomer, String phone, double taka, String addres, String imageUrl, double lastTotal,double pdfTotal) {
        this.customerIdDucunt = customerIdDucunt;
        this.nameCUstomer = nameCUstomer;
        this.phone = phone;
        this.taka = taka;
        this.addres = addres;
        this.imageUrl = imageUrl;
        this.lastTotal = lastTotal;
        this.pdfTotal = pdfTotal;
    }

    public CustomerNote(String customerIdDucunt, String nameCUstomer, String phone, double taka, String addres, double lastTotal,double pdfTotal) {
        this.customerIdDucunt = customerIdDucunt;
        this.nameCUstomer = nameCUstomer;
        this.phone = phone;
        this.taka = taka;
        this.addres = addres;
        this.lastTotal = lastTotal;
        this.pdfTotal = pdfTotal;

    }


    public String getCustomerIdDucunt() {
        return customerIdDucunt;
    }

    public String getNameCUstomer() {
        return nameCUstomer;
    }

    public String getPhone() {
        return phone;
    }

    public double getTaka() {
        return taka;
    }

    public String getAddres() {
        return addres;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getLastTotal() {
        return lastTotal;
    }

    public double getPdfTotal() {
        return pdfTotal;
    }

    public void setPdfTotal(double pdfTotal) {
        this.pdfTotal = pdfTotal;
    }
}
