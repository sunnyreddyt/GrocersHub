package com.grocers.hub.models;

import java.util.ArrayList;

public class ProductsResponse {

    private String status;
    private ArrayList<ProductsResponse> products;
    private String name;
    private String id;
    private int price;
    private String image;
    private int totalCount;
    private String sku,product_id,attribute_code,value_index,super_attribute_label,default_title,finalPrice,qty;
    ArrayList<ProductsResponse> options;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getAttribute_code() {
        return attribute_code;
    }

    public void setAttribute_code(String attribute_code) {
        this.attribute_code = attribute_code;
    }

    public String getValue_index() {
        return value_index;
    }

    public void setValue_index(String value_index) {
        this.value_index = value_index;
    }

    public String getSuper_attribute_label() {
        return super_attribute_label;
    }

    public void setSuper_attribute_label(String super_attribute_label) {
        this.super_attribute_label = super_attribute_label;
    }

    public String getDefault_title() {
        return default_title;
    }

    public void setDefault_title(String default_title) {
        this.default_title = default_title;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public ArrayList<ProductsResponse> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<ProductsResponse> options) {
        this.options = options;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ProductsResponse> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductsResponse> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
