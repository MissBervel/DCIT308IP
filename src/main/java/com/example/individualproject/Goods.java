package com.example.individualproject;

public class Goods {
    private static int count = 1;
    private int goods_id;
    private String goods_name;
    private int quantity;
    private int unitPrice;

    public Goods(String product_name, int quantity, int unitPrice) {
        this.goods_id = count++;
        this.goods_name = product_name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }
}

