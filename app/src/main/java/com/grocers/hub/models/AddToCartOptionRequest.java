package com.grocers.hub.models;

import java.util.ArrayList;

public class AddToCartOptionRequest {

    private int qty, quote_id, option_value;
    private AddToCartOptionRequest cartItem;
    private String sku, option_id;
    private AddToCartOptionRequest product_option;
    private AddToCartOptionRequest extension_attributes;
    private ArrayList<AddToCartOptionRequest> configurable_item_options;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(int quote_id) {
        this.quote_id = quote_id;
    }

    public int getOption_value() {
        return option_value;
    }

    public void setOption_value(int option_value) {
        this.option_value = option_value;
    }

    public AddToCartOptionRequest getCartItem() {
        return cartItem;
    }

    public void setCartItem(AddToCartOptionRequest cartItem) {
        this.cartItem = cartItem;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public AddToCartOptionRequest getProduct_option() {
        return product_option;
    }

    public void setProduct_option(AddToCartOptionRequest product_option) {
        this.product_option = product_option;
    }

    public AddToCartOptionRequest getExtension_attributes() {
        return extension_attributes;
    }

    public void setExtension_attributes(AddToCartOptionRequest extension_attributes) {
        this.extension_attributes = extension_attributes;
    }

    public ArrayList<AddToCartOptionRequest> getConfigurable_item_options() {
        return configurable_item_options;
    }

    public void setConfigurable_item_options(ArrayList<AddToCartOptionRequest> configurable_item_options) {
        this.configurable_item_options = configurable_item_options;
    }
}
