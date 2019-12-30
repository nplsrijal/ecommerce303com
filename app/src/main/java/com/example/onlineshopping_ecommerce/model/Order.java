package com.example.onlineshopping_ecommerce.model;

public class Order {
    private String Id;
    private String Totalprice;
    private String Orderdate;
    private String Deliverdate;
    private String Deliverstatus;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTotalprice() {
        return Totalprice;
    }

    public void setTotalprice(String totalprice) {
        Totalprice = totalprice;
    }

    public String getOrderdate() {
        return Orderdate;
    }

    public void setOrderdate(String orderdate) {
        Orderdate = orderdate;
    }

    public String getDeliverdate() {
        return Deliverdate;
    }

    public void setDeliverdate(String deliverdate) {
        Deliverdate = deliverdate;
    }

    public String getDeliverstatus() {
        return Deliverstatus;
    }

    public void setDeliverstatus(String deliverstatus) {
        Deliverstatus = deliverstatus;
    }

    public Order(String id, String totalprice, String orderdate, String deliverdate, String deliverstatus) {
        Id = id;
        Totalprice = totalprice;
        Orderdate = orderdate;
        Deliverdate = deliverdate;
        Deliverstatus = deliverstatus;
    }
}
