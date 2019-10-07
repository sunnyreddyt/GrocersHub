package com.grocers.hub.models;

import java.util.ArrayList;

public class OrderDetailsResponse {

    private String orderId, grandTotal, total_qty_ordered, entity_id,
            state, status, discount_amount, base_shipping_amount, base_tax_amount, tax_amount,
            price, sku, name, qty, image,subtotal,created_at;
    private OrderDetailsResponse data;
    private ArrayList<OrderDetailsResponse> items;
    private ArrayList<OrderDetailsResponse> orders;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public ArrayList<OrderDetailsResponse> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrderDetailsResponse> orders) {
        this.orders = orders;
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

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }

    public String getBase_shipping_amount() {
        return base_shipping_amount;
    }

    public void setBase_shipping_amount(String base_shipping_amount) {
        this.base_shipping_amount = base_shipping_amount;
    }

    public String getBase_tax_amount() {
        return base_tax_amount;
    }

    public void setBase_tax_amount(String base_tax_amount) {
        this.base_tax_amount = base_tax_amount;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(String tax_amount) {
        this.tax_amount = tax_amount;
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

    public OrderDetailsResponse getData() {
        return data;
    }

    public void setData(OrderDetailsResponse data) {
        this.data = data;
    }

    public ArrayList<OrderDetailsResponse> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderDetailsResponse> items) {
        this.items = items;
    }
}
