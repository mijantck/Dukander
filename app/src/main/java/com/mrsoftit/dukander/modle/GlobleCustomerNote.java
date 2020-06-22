package com.mrsoftit.dukander.modle;

public class GlobleCustomerNote {
    private String glovleCustomerID;
    private String customerType;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    public GlobleCustomerNote(){}

    public GlobleCustomerNote(String glovleCustomerID, String customerType, String name, String email, String phoneNumber, String address) {
        this.glovleCustomerID = glovleCustomerID;
        this.customerType = customerType;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getGlovleCustomerID() {
        return glovleCustomerID;
    }

    public void setGlovleCustomerID(String glovleCustomerID) {
        this.glovleCustomerID = glovleCustomerID;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
