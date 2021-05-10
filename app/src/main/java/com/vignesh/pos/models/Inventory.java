package com.vignesh.pos.models;

public class Inventory {
    private String pName;
    private String pQty;
    private Boolean pAvailability;

    public Inventory(String pName, String pQty, Boolean pAvailability) {
        this.pName = pName;
        this.pQty = pQty;
        this.pAvailability = pAvailability;
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

    public Boolean getpAvailability() {
        return pAvailability;
    }

    public void setpAvailability(Boolean pAvailability) {
        this.pAvailability = pAvailability;
    }
}
