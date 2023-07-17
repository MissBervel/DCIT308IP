package com.example.individualproject;

import java.util.*;

class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Vendor {
    private String name;

    public Vendor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class InventoryManagementSystem {
    private Map<String, Stack<Item>> itemStacks;
    private Map<String, Queue<Item>> itemQueues;
    private Map<String, List<Item>> itemLists;
    private Map<String, Integer> stockBalance;
    private Map<String, Integer> productSales;
    private Map<String, Vendor> vendors;

    public InventoryManagementSystem() {
        itemStacks = new HashMap<>();
        itemQueues = new HashMap<>();
        itemLists = new HashMap<>();
        stockBalance = new HashMap<>();
        productSales = new HashMap<>();
        vendors = new HashMap<>();
    }

    public void addVendor(String vendorName) {
        Vendor vendor = new Vendor(vendorName);
        vendors.put(vendorName, vendor);
    }

    public void addGood(String category, String itemName) {
        Item item = new Item(itemName);
        switch (category) {
            case "Beverages":
            case "Bread/Bakery":
            case "Canned/Jarred Goods":
            case "Dairy Products":
                Stack<Item> stack = itemStacks.getOrDefault(category, new Stack<>());
                stack.push(item);
                itemStacks.put(category, stack);
                break;

            case "Dry/Baking Goods":
            case "Frozen Products":
            case "Meat":
                Queue<Item> queue = itemQueues.getOrDefault(category, new LinkedList<>());
                queue.add(item);
                itemQueues.put(category, queue);
                break;

            case "Farm Produce":
            case "Home Cleaners":
            case "Paper Goods":
            case "Home Care":
                List<Item> list = itemLists.getOrDefault(category, new ArrayList<>());
                list.add(item);
                itemLists.put(category, list);
                break;

            default:
                System.out.println("Invalid category.");
                break;
        }
        stockBalance.put(itemName, stockBalance.getOrDefault(itemName, 0) + 1);
    }

    public void viewVendors() {
        System.out.println("Vendors:");
        for (Vendor vendor : vendors.values()) {
            System.out.println(vendor.getName());
        }
    }

    public void viewGoods(String category) {
        System.out.println("Goods in category " + category + ":");
        switch (category) {
            case "Beverages":
            case "Bread/Bakery":
            case "Canned/Jarred Goods":
            case "Dairy Products":
                Stack<Item> stack = itemStacks.get(category);
                if (stack != null) {
                    for (Item item : stack) {
                        System.out.println(item.getName());
                    }
                }
                break;

            case "Dry/Baking Goods":
            case "Frozen Products":
            case "Meat":
                Queue<Item> queue = itemQueues.get(category);
                if (queue != null) {
                    for (Item item : queue) {
                        System.out.println(item.getName());
                    }
                }
                break;

            case "Farm Produce":
            case "Home Cleaners":
            case "Paper Goods":
            case "Home Care":
                List<Item> list = itemLists.get(category);
                if (list != null) {
                    for (Item item : list) {
                        System.out.println(item.getName());
                    }
                }
                break;

            default:
                System.out.println("Invalid category.");
                break;
        }
    }

    public void issueGoods(String itemName) {
        if (stockBalance.containsKey(itemName)) {
            int balance = stockBalance.get(itemName);
            if (balance > 0) {
                stockBalance.put(itemName, balance - 1);
                productSales.put(itemName, productSales.getOrDefault(itemName, 0) + 1);
                System.out.println("Item " + itemName + " issued successfully.");
            } else {
                System.out.println("Item " + itemName + " is out of stock.");
            }
        } else {
            System.out.println("Item " + itemName + " not found.");
        }
    }

    public static void main(String[] args) {
        // Create an instance of the inventory management system
        InventoryManagementSystem ims = new InventoryManagementSystem();

        // Add vendors
        ims.addVendor("Vendor1");
        ims.addVendor("Vendor2");

        // Add goods
        ims.addGood("Beverages", "Coffee");
        ims.addGood("Beverages", "Tea");

        ims.addGood("Bread/Bakery", "Sandwich Loaves");
        ims.addGood("Bread/Bakery", "Dinner Rolls");

        // View vendors
        ims.viewVendors();

        // View goods
        ims.viewGoods("Beverages");
        ims.viewGoods("Bread/Bakery");

        // Issue goods
        ims.issueGoods("Coffee");
        ims.issueGoods("Coffee");
        ims.issueGoods("Tea");

        // Perform other operations as needed
    }
}
