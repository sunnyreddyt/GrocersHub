package com.grocers.hub.models;

import java.util.ArrayList;

public class ProductDetailsResponse {

    private int qty, final_price,finalPrice;
    private String price, entity_id, attribute_set_id, type_id, sku, has_options, required_options, name,
            image, value_id, file, product_id, attribute_code, weight, value_index, default_title;
    private ProductDetailsResponse quantity_and_stock_status, data;
    private boolean is_in_stock;
    private ArrayList<ProductDetailsResponse> product_links;
    private ArrayList<ProductDetailsResponse> media_gallery_entries;
    private ArrayList<ProductDetailsResponse> options;


    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public ProductDetailsResponse getData() {
        return data;
    }

    public void setData(ProductDetailsResponse data) {
        this.data = data;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getFinal_price() {
        return final_price;
    }

    public void setFinal_price(int final_price) {
        this.final_price = final_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getAttribute_set_id() {
        return attribute_set_id;
    }

    public void setAttribute_set_id(String attribute_set_id) {
        this.attribute_set_id = attribute_set_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getHas_options() {
        return has_options;
    }

    public void setHas_options(String has_options) {
        this.has_options = has_options;
    }

    public String getRequired_options() {
        return required_options;
    }

    public void setRequired_options(String required_options) {
        this.required_options = required_options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getValue_id() {
        return value_id;
    }

    public void setValue_id(String value_id) {
        this.value_id = value_id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

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

    public String getDefault_title() {
        return default_title;
    }

    public void setDefault_title(String default_title) {
        this.default_title = default_title;
    }

    public ProductDetailsResponse getQuantity_and_stock_status() {
        return quantity_and_stock_status;
    }

    public void setQuantity_and_stock_status(ProductDetailsResponse quantity_and_stock_status) {
        this.quantity_and_stock_status = quantity_and_stock_status;
    }

    public boolean isIs_in_stock() {
        return is_in_stock;
    }

    public void setIs_in_stock(boolean is_in_stock) {
        this.is_in_stock = is_in_stock;
    }

    public ArrayList<ProductDetailsResponse> getProduct_links() {
        return product_links;
    }

    public void setProduct_links(ArrayList<ProductDetailsResponse> product_links) {
        this.product_links = product_links;
    }

    public ArrayList<ProductDetailsResponse> getMedia_gallery_entries() {
        return media_gallery_entries;
    }

    public void setMedia_gallery_entries(ArrayList<ProductDetailsResponse> media_gallery_entries) {
        this.media_gallery_entries = media_gallery_entries;
    }

    public ArrayList<ProductDetailsResponse> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<ProductDetailsResponse> options) {
        this.options = options;
    }
}
