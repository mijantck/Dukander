package com.mrsoftit.dukander;

public class CustomerNote {
    private String customerIdDucunt;
    private String nameCUstomer;
    private String phone;
    private double taka;
    private String addres;
    private String imageUrl;
    private double lastTotal;

    public  CustomerNote(){}

    public CustomerNote(String customerIdDucunt, String nameCUstomer, String phone, double taka, String addres, String imageUrl, double lastTotal) {
        this.customerIdDucunt = customerIdDucunt;
        this.nameCUstomer = nameCUstomer;
        this.phone = phone;
        this.taka = taka;
        this.addres = addres;
        this.imageUrl = imageUrl;
        this.lastTotal = lastTotal;
    }

    public CustomerNote(String customerIdDucunt, String nameCUstomer, String phone, double taka, String addres, double lastTotal) {
        this.customerIdDucunt = customerIdDucunt;
        this.nameCUstomer = nameCUstomer;
        this.phone = phone;
        this.taka = taka;
        this.addres = addres;
        this.lastTotal = lastTotal;
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
}
