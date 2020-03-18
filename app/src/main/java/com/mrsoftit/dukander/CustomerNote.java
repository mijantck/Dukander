package com.mrsoftit.dukander;

public class CustomerNote {
    private String CustomerIdDucunt;
    private String nameCUstomer;
    private String phone;
    private String taka;
    private String addres;
    private String imageUrl;

    public  CustomerNote(){}

    public CustomerNote(String customerIdDucunt, String nameCUstomer, String phone, String taka, String addres, String imageUrl) {
        CustomerIdDucunt = customerIdDucunt;
        this.nameCUstomer = nameCUstomer;
        this.phone = phone;
        this.taka = taka;
        this.addres = addres;
        this.imageUrl = imageUrl;
    }

    public CustomerNote(String customerIdDucunt, String nameCUstomer, String phone, String taka, String addres) {
        CustomerIdDucunt = customerIdDucunt;
        this.nameCUstomer = nameCUstomer;
        this.phone = phone;
        this.taka = taka;
        this.addres = addres;
    }

    public String getCustomerIdDucunt() {
        return CustomerIdDucunt;
    }

    public String getNameCUstomer() {
        return nameCUstomer;
    }

    public String getPhone() {
        return phone;
    }

    public String getTaka() {
        return taka;
    }

    public String getAddres() {
        return addres;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
