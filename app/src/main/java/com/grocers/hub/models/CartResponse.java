package com.grocers.hub.models;

import java.util.ArrayList;

public class CartResponse {

    private ArrayList<CartResponse> items;
    private int item_id;
    private String sku;
    private int qty;
    private String name;
    private double price;
    private double finalPrice;
    private String image;
    private String product_type;
    private String quote_id;
    private String default_title;
    private String parentSkuID;
    private int maxQtyAllowed;

    public int getMaxQtyAllowed() {
        return maxQtyAllowed;
    }

    public void setMaxQtyAllowed(int maxQtyAllowed) {
        this.maxQtyAllowed = maxQtyAllowed;
    }

    public String getParentSkuID() {
        return parentSkuID;
    }

    public void setParentSkuID(String parentSkuID) {
        this.parentSkuID = parentSkuID;
    }

    public String getDefault_title() {
        return default_title;
    }

    public void setDefault_title(String default_title) {
        this.default_title = default_title;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public ArrayList<CartResponse> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartResponse> items) {
        this.items = items;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(String quote_id) {
        this.quote_id = quote_id;
    }
}
