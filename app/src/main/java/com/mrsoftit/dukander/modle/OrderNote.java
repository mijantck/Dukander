package com.mrsoftit.dukander.modle;

public class OrderNote {
    private String cutomerName;
    private String cutomerPhone;
    private String cutomerAddress;
    private String cutomerID;
    private String shopName;
    private String shopPhone;
    private String shopAddress;
    private String shopID;
    private String orderID;
    private String productID;
    private String productURL;
    private String productCode;
    private String productPrice;
    private String productQuantity;
    private String offerForShop;
    private String offerForcoupon;
    private String orderStatus;
    private String deliveryBoyName;
    private String deliveryBoyPhone;


    public void OrderNote(){}

    public OrderNote(String cutomerName, String cutomerPhone, String cutomerAddress, String cutomerID, String shopName, String shopPhone, String shopAddress, String shopID, String orderID, String productID, String productURL, String productCode, String productPrice, String productQuantity, String offerForShop, String offerForcoupon, String orderStatus) {
        this.cutomerName = cutomerName;
        this.cutomerPhone = cutomerPhone;
        this.cutomerAddress = cutomerAddress;
        this.cutomerID = cutomerID;
        this.shopName = shopName;
        this.shopPhone = shopPhone;
        this.shopAddress = shopAddress;
        this.shopID = shopID;
        this.orderID = orderID;
        this.productID = productID;
        this.productURL = productURL;
        this.productCode = productCode;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.offerForShop = offerForShop;
        this.offerForcoupon = offerForcoupon;
        this.orderStatus = orderStatus;
    }


    public String getCutomerName() {
        return cutomerName;
    }

    public void setCutomerName(String cutomerName) {
        this.cutomerName = cutomerName;
    }

    public String getCutomerPhone() {
        return cutomerPhone;
    }

    public void setCutomerPhone(String cutomerPhone) {
        this.cutomerPhone = cutomerPhone;
    }

    public String getCutomerAddress() {
        return cutomerAddress;
    }

    public void setCutomerAddress(String cutomerAddress) {
        this.cutomerAddress = cutomerAddress;
    }

    public String getCutomerID() {
        return cutomerID;
    }

    public void setCutomerID(String cutomerID) {
        this.cutomerID = cutomerID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductURL() {
        return productURL;
    }

    public void setProductURL(String productURL) {
        this.productURL = productURL;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getOfferForShop() {
        return offerForShop;
    }

    public void setOfferForShop(String offerForShop) {
        this.offerForShop = offerForShop;
    }

    public String getOfferForcoupon() {
        return offerForcoupon;
    }

    public void setOfferForcoupon(String offerForcoupon) {
        this.offerForcoupon = offerForcoupon;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryBoyName() {
        return deliveryBoyName;
    }

    public void setDeliveryBoyName(String deliveryBoyName) {
        this.deliveryBoyName = deliveryBoyName;
    }

    public String getDeliveryBoyPhone() {
        return deliveryBoyPhone;
    }

    public void setDeliveryBoyPhone(String deliveryBoyPhone) {
        this.deliveryBoyPhone = deliveryBoyPhone;
    }
}
