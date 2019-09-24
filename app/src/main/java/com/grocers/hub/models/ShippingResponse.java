package com.grocers.hub.models;

import java.util.ArrayList;

public class ShippingResponse {

    private ArrayList<ShippingResponse> payment_methods;
    private String code;
    private String title;

    private ShippingResponse totals;
    private double grand_total;
    private double base_grand_total;
    private double subtotal;
    private double base_subtotal;
    private double discount_amount;
    private double base_discount_amount;
    private double subtotal_with_discount;
    private double base_subtotal_with_discount;
    private double shipping_amount;
    private double base_shipping_amount;
    private double shipping_discount_amount;
    private double base_shipping_discount_amount;
    private double tax_amount;
    private double base_tax_amount;
    private double shipping_tax_amount;
    private double base_shipping_tax_amount;
    private double subtotal_incl_tax;
    private double shipping_incl_tax;
    private double base_shipping_incl_tax;
    private String base_currency_code;
    private String quote_currency_code;
    private double items_qty;
    private ArrayList<ShippingResponse> items;
    private double item_id;
    private double price;
    private double base_price;
    private double qty;
    private double row_total;
    private double base_row_total;
    private double row_total_with_discount;
    private double tax_percent;
    private double discount_percent;
    private double price_incl_tax;
    private double base_price_incl_tax;
    private double row_total_incl_tax;
    private double base_row_total_incl_tax;
    private String options;
    private String name;
    private ArrayList<ShippingResponse> total_segments;
    private int value;

    public ArrayList<ShippingResponse> getPayment_methods() {
        return payment_methods;
    }

    public void setPayment_methods(ArrayList<ShippingResponse> payment_methods) {
        this.payment_methods = payment_methods;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ShippingResponse getTotals() {
        return totals;
    }

    public void setTotals(ShippingResponse totals) {
        this.totals = totals;
    }



    public ArrayList<ShippingResponse> getItems() {
        return items;
    }

    public void setItems(ArrayList<ShippingResponse> items) {
        this.items = items;
    }

    public double getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(double grand_total) {
        this.grand_total = grand_total;
    }

    public double getBase_grand_total() {
        return base_grand_total;
    }

    public void setBase_grand_total(double base_grand_total) {
        this.base_grand_total = base_grand_total;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getBase_subtotal() {
        return base_subtotal;
    }

    public void setBase_subtotal(double base_subtotal) {
        this.base_subtotal = base_subtotal;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(double discount_amount) {
        this.discount_amount = discount_amount;
    }

    public double getBase_discount_amount() {
        return base_discount_amount;
    }

    public void setBase_discount_amount(double base_discount_amount) {
        this.base_discount_amount = base_discount_amount;
    }

    public double getSubtotal_with_discount() {
        return subtotal_with_discount;
    }

    public void setSubtotal_with_discount(double subtotal_with_discount) {
        this.subtotal_with_discount = subtotal_with_discount;
    }

    public double getBase_subtotal_with_discount() {
        return base_subtotal_with_discount;
    }

    public void setBase_subtotal_with_discount(double base_subtotal_with_discount) {
        this.base_subtotal_with_discount = base_subtotal_with_discount;
    }

    public double getShipping_amount() {
        return shipping_amount;
    }

    public void setShipping_amount(double shipping_amount) {
        this.shipping_amount = shipping_amount;
    }

    public double getBase_shipping_amount() {
        return base_shipping_amount;
    }

    public void setBase_shipping_amount(double base_shipping_amount) {
        this.base_shipping_amount = base_shipping_amount;
    }

    public double getShipping_discount_amount() {
        return shipping_discount_amount;
    }

    public void setShipping_discount_amount(double shipping_discount_amount) {
        this.shipping_discount_amount = shipping_discount_amount;
    }

    public double getBase_shipping_discount_amount() {
        return base_shipping_discount_amount;
    }

    public void setBase_shipping_discount_amount(double base_shipping_discount_amount) {
        this.base_shipping_discount_amount = base_shipping_discount_amount;
    }

    public double getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(double tax_amount) {
        this.tax_amount = tax_amount;
    }

    public double getBase_tax_amount() {
        return base_tax_amount;
    }

    public void setBase_tax_amount(double base_tax_amount) {
        this.base_tax_amount = base_tax_amount;
    }

    public double getShipping_tax_amount() {
        return shipping_tax_amount;
    }

    public void setShipping_tax_amount(double shipping_tax_amount) {
        this.shipping_tax_amount = shipping_tax_amount;
    }

    public double getBase_shipping_tax_amount() {
        return base_shipping_tax_amount;
    }

    public void setBase_shipping_tax_amount(double base_shipping_tax_amount) {
        this.base_shipping_tax_amount = base_shipping_tax_amount;
    }

    public double getSubtotal_incl_tax() {
        return subtotal_incl_tax;
    }

    public void setSubtotal_incl_tax(double subtotal_incl_tax) {
        this.subtotal_incl_tax = subtotal_incl_tax;
    }

    public double getShipping_incl_tax() {
        return shipping_incl_tax;
    }

    public void setShipping_incl_tax(double shipping_incl_tax) {
        this.shipping_incl_tax = shipping_incl_tax;
    }

    public double getBase_shipping_incl_tax() {
        return base_shipping_incl_tax;
    }

    public void setBase_shipping_incl_tax(double base_shipping_incl_tax) {
        this.base_shipping_incl_tax = base_shipping_incl_tax;
    }

    public String getBase_currency_code() {
        return base_currency_code;
    }

    public void setBase_currency_code(String base_currency_code) {
        this.base_currency_code = base_currency_code;
    }

    public String getQuote_currency_code() {
        return quote_currency_code;
    }

    public void setQuote_currency_code(String quote_currency_code) {
        this.quote_currency_code = quote_currency_code;
    }

    public double getItems_qty() {
        return items_qty;
    }

    public void setItems_qty(double items_qty) {
        this.items_qty = items_qty;
    }

    public double getItem_id() {
        return item_id;
    }

    public void setItem_id(double item_id) {
        this.item_id = item_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getBase_price() {
        return base_price;
    }

    public void setBase_price(double base_price) {
        this.base_price = base_price;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getRow_total() {
        return row_total;
    }

    public void setRow_total(double row_total) {
        this.row_total = row_total;
    }

    public double getBase_row_total() {
        return base_row_total;
    }

    public void setBase_row_total(double base_row_total) {
        this.base_row_total = base_row_total;
    }

    public double getRow_total_with_discount() {
        return row_total_with_discount;
    }

    public void setRow_total_with_discount(double row_total_with_discount) {
        this.row_total_with_discount = row_total_with_discount;
    }

    public double getTax_percent() {
        return tax_percent;
    }

    public void setTax_percent(double tax_percent) {
        this.tax_percent = tax_percent;
    }

    public double getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(double discount_percent) {
        this.discount_percent = discount_percent;
    }

    public double getPrice_incl_tax() {
        return price_incl_tax;
    }

    public void setPrice_incl_tax(double price_incl_tax) {
        this.price_incl_tax = price_incl_tax;
    }

    public double getBase_price_incl_tax() {
        return base_price_incl_tax;
    }

    public void setBase_price_incl_tax(double base_price_incl_tax) {
        this.base_price_incl_tax = base_price_incl_tax;
    }

    public double getRow_total_incl_tax() {
        return row_total_incl_tax;
    }

    public void setRow_total_incl_tax(double row_total_incl_tax) {
        this.row_total_incl_tax = row_total_incl_tax;
    }

    public double getBase_row_total_incl_tax() {
        return base_row_total_incl_tax;
    }

    public void setBase_row_total_incl_tax(double base_row_total_incl_tax) {
        this.base_row_total_incl_tax = base_row_total_incl_tax;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ShippingResponse> getTotal_segments() {
        return total_segments;
    }

    public void setTotal_segments(ArrayList<ShippingResponse> total_segments) {
        this.total_segments = total_segments;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
