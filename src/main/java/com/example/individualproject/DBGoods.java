package com.example.individualproject;

public class DBGoods {
    private int productID;
    private String productName;
    private int categoryID;
    private int unitPrice;
    private int quantity;

    public DBGoods(int productID, String productName, int categoryID, int unitPrice, int quantity) {
        this.productID = productID;
        this.productName = productName;
        this.categoryID = categoryID;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getProductID() {
        return this.productID;
    }

    public String getProductName() {
        return this.productName;
    }

    public int getCategoryID() {
        return this.categoryID;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getUnitPrice() {
        return this.unitPrice;
    }
}
