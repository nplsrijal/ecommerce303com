package com.example.onlineshopping_ecommerce.model;

public class Cart {
    private String Id;
    private String Productid;
    private String Quantity;
    private String Price;
    private int TotalPrice;

    public Cart(String id, String productId, String quantity, String price, int totalPrice) {
        Id = id;
        Productid = productId;
        Quantity = quantity;
        Price = price;
        TotalPrice = totalPrice;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProductId() {
        return Productid;
    }

    public void setProductId(String productId) {
        Productid = productId;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
