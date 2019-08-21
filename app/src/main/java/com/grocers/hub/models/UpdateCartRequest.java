package com.grocers.hub.models;

public class UpdateCartRequest {

    private UpdateCartRequest cartItem;
    private int item_id;
    private int qty;
    private String quote_id;

    public UpdateCartRequest getCartItem() {
        return cartItem;
    }

    public void setCartItem(UpdateCartRequest cartItem) {
        this.cartItem = cartItem;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(String quote_id) {
        this.quote_id = quote_id;
    }
}
