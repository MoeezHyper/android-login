package com.example.myapplication;

public class Product {

    private int id;
    private String title;
    private String description;
    private int price;

    public Product(int id, String title, String description, int price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getPrice() { return price; }
}
