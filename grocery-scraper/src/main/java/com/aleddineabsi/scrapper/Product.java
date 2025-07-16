package com.aleddineabsi.scrapper;

public class Product {
    private int id = 0;
    private String name = "null";
    private double price = 0;
    private double price2 = 0;
    private String category = "null";
    private String date = "null";
    private String store = "null";

    public Product(int id,String name,double price,double price2,String category,String date,String store){
        this.id = id;
        this.name = name;
        this.price = price;
        this.price2 = price2;
        this.category = category;
        this.date = date;
        this.store = store;
    }

    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public Double getPrice(){
        return this.price;
    }
    public Double getPrice2(){
        return this.price2;
    }
    public String getCategory(){
        return this.category;
    }
    public String getDate(){
        return this.date;
    }
    public String getStore(){
        return this.store;
    }






}
