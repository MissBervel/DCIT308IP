package com.example.individualproject;

public class DBVendors {
    private int vendorID;
    private String vendorName;
    private String contact;

    public DBVendors(int vendorID, String vendorName, String contact) {
        this.vendorID = vendorID;
        this.vendorName = vendorName;
        this.contact = contact;
    }

    public int getVendorID(){
        return this.vendorID;
    }

    public String getVendorName(){
        return this.vendorName;
    }

    public String getContact() {
        return this.contact;
    }
}
