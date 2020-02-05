package com.grocers.hub.models;

import java.util.ArrayList;

public class OrdersResponse {

    private int staus;
    private ArrayList<OrdersResponse> orders, items;
    private String orderId, grandTotal, total_qty_ordered, price, sku, name, qty, image;

    public int getStaus() {
        return staus;
    }

    public void setStaus(int staus) {
        this.staus = staus;
    }

    public ArrayList<OrdersResponse> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrdersResponse> orders) {
        this.orders = orders;
    }

    public ArrayList<OrdersResponse> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrdersResponse> items) {
        this.items = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getTotal_qty_ordered() {
        return total_qty_ordered;
    }

    public void setTotal_qty_ordered(String total_qty_ordered) {
        this.total_qty_ordered = total_qty_ordered;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
