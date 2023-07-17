package com.example.individualproject;

public class Category {
    private static int cat_id;
    private String name;
    private int count = 1;

    public Category(String name) {
        this.name = name;
        cat_id = count++;
    }
}
