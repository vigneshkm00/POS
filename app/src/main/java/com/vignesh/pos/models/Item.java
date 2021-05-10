package com.vignesh.pos.models;

public class Item {
    public String itemCode;
    public String itemName;
    public String itemCost;


    public Item() {
    }

    public Item(String itemName, String itemCost, String itemCode) {
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCost() {
        return itemCost;
    }

    public void setItemCost(String itemCost) {
        this.itemCost = itemCost;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
}
