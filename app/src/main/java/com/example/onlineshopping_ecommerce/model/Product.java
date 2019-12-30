package com.example.onlineshopping_ecommerce.model;

public class Product {
    private String Id;
    private String Name;
    private String Code;
    private String Description;
    private String CategoryId;
    private String Quantity;
    private String Color;
    private String Size;
    private String Image;
    private String Price;
    private String OQuantity;

    public String getOQuantity() {
        return OQuantity;
    }

    public void setOQuantity(String oquantity) {
        OQuantity = oquantity;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public Product(String id, String name, String code, String description, String categoryId, String quantity, String color, String size, String image, String price, String oquantity) {
        Id = id;
        Name = name;
        Code = code;
        Description = description;
        CategoryId = categoryId;
        Quantity = quantity;
        Color = color;
        Size = size;
        Image = image;
        Price = price;
        OQuantity = oquantity;
    }
}
