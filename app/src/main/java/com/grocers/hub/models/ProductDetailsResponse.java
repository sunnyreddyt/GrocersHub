package com.grocers.hub.models;

import java.util.ArrayList;

public class ProductDetailsResponse {

    private int id;
    private int attribute_set_id;
    private int price;
    private int status;
    private int weight;
    private int visibility;
    private String sku;
    private String name;
    private String type_id;
    private String file;
    private ArrayList<ProductDetailsResponse> product_links;
    private ArrayList<ProductDetailsResponse> media_gallery_entries;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public ArrayList<ProductDetailsResponse> getMedia_gallery_entries() {
        return media_gallery_entries;
    }

    public void setMedia_gallery_entries(ArrayList<ProductDetailsResponse> media_gallery_entries) {
        this.media_gallery_entries = media_gallery_entries;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttribute_set_id() {
        return attribute_set_id;
    }

    public void setAttribute_set_id(int attribute_set_id) {
        this.attribute_set_id = attribute_set_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
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

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public ArrayList<ProductDetailsResponse> getProduct_links() {
        return product_links;
    }

    public void setProduct_links(ArrayList<ProductDetailsResponse> product_links) {
        this.product_links = product_links;
    }
}
