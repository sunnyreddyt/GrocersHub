package com.grocers.hub.models;

import java.util.ArrayList;

public class SimilarProductsResponse {

    private int status;
    private ArrayList<SimilarProductsResponse> data;
    private String name;
    private String sku;
    private String price;
    private int finalPrice;
    private String image;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<SimilarProductsResponse> getData() {
        return data;
    }

    public void setData(ArrayList<SimilarProductsResponse> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
