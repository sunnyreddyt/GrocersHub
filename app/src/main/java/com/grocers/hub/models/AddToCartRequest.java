package com.grocers.hub.models;

public class AddToCartRequest {

    private int qty;
    private AddToCartRequest cartItem;
    private String sku;
    private String quote_id;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public AddToCartRequest getCartItem() {
        return cartItem;
    }

    public void setCartItem(AddToCartRequest cartItem) {
        this.cartItem = cartItem;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(String quote_id) {
        this.quote_id = quote_id;
    }
}
