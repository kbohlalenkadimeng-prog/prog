package com.richfield.smartpantry.model;

public class PantryItem {
    private long id;
    private String name;
    private double quantity;
    private String unit;
    private String expiryDate;

    public PantryItem() {}
    public PantryItem(long id, String name, double quantity, String unit, String expiryDate) {
        this.id=id; this.name=name; this.quantity=quantity; this.unit=unit; this.expiryDate=expiryDate;
    }
    public long getId(){return id;} public void setId(long v){id=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public double getQuantity(){return quantity;} public void setQuantity(double v){quantity=v;}
    public String getUnit(){return unit;} public void setUnit(String v){unit=v;}
    public String getExpiryDate(){return expiryDate;} public void setExpiryDate(String v){expiryDate=v;}
}
