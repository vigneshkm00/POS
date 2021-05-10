package com.vignesh.pos.models;

public class Itembought {
    private String pName;
    private String pQty;
    private String pPrice;
    private String pDate;

    public Itembought(String pName, String pQty, String pPrice, String pDate) {
        this.pName = pName;
        this.pQty = pQty;
        this.pPrice = pPrice;
        this.pDate = pDate;
    }

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpQty() {
        return pQty;
    }

    public void setpQty(String pQty) {
        this.pQty = pQty;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }
}
