package com.grocers.hub.models;

import java.util.ArrayList;

public class ShippingResponse {

    private ArrayList<ShippingResponse> payment_methods;
    private String code;
    private String title;

    private ShippingResponse totals;
    private int grand_total;
    private int base_grand_total;
    private int subtotal;
    private int base_subtotal;
    private int discount_amount;
    private int base_discount_amount;
    private int subtotal_with_discount;
    private int base_subtotal_with_discount;
    private int shipping_amount;
    private int base_shipping_amount;
    private int shipping_discount_amount;
    private int base_shipping_discount_amount;
    private int tax_amount;
    private int base_tax_amount;
    private int shipping_tax_amount;
    private int base_shipping_tax_amount;
    private int subtotal_incl_tax;
    private int shipping_incl_tax;
    private int base_shipping_incl_tax;
    private String base_currency_code;
    private String quote_currency_code;
    private int items_qty;
    private ArrayList<ShippingResponse> items;
    private int item_id;
    private int price;
    private int base_price;
    private int qty;
    private int row_total;
    private int base_row_total;
    private int row_total_with_discount;
    private int tax_percent;
    private int discount_percent;
    private int price_incl_tax;
    private int base_price_incl_tax;
    private int row_total_incl_tax;
    private int base_row_total_incl_tax;
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

    public int getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(int grand_total) {
        this.grand_total = grand_total;
    }

    public int getBase_grand_total() {
        return base_grand_total;
    }

    public void setBase_grand_total(int base_grand_total) {
        this.base_grand_total = base_grand_total;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getBase_subtotal() {
        return base_subtotal;
    }

    public void setBase_subtotal(int base_subtotal) {
        this.base_subtotal = base_subtotal;
    }

    public int getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(int discount_amount) {
        this.discount_amount = discount_amount;
    }

    public int getBase_discount_amount() {
        return base_discount_amount;
    }

    public void setBase_discount_amount(int base_discount_amount) {
        this.base_discount_amount = base_discount_amount;
    }

    public int getSubtotal_with_discount() {
        return subtotal_with_discount;
    }

    public void setSubtotal_with_discount(int subtotal_with_discount) {
        this.subtotal_with_discount = subtotal_with_discount;
    }

    public int getBase_subtotal_with_discount() {
        return base_subtotal_with_discount;
    }

    public void setBase_subtotal_with_discount(int base_subtotal_with_discount) {
        this.base_subtotal_with_discount = base_subtotal_with_discount;
    }

    public int getShipping_amount() {
        return shipping_amount;
    }

    public void setShipping_amount(int shipping_amount) {
        this.shipping_amount = shipping_amount;
    }

    public int getBase_shipping_amount() {
        return base_shipping_amount;
    }

    public void setBase_shipping_amount(int base_shipping_amount) {
        this.base_shipping_amount = base_shipping_amount;
    }

    public int getShipping_discount_amount() {
        return shipping_discount_amount;
    }

    public void setShipping_discount_amount(int shipping_discount_amount) {
        this.shipping_discount_amount = shipping_discount_amount;
    }

    public int getBase_shipping_discount_amount() {
        return base_shipping_discount_amount;
    }

    public void setBase_shipping_discount_amount(int base_shipping_discount_amount) {
        this.base_shipping_discount_amount = base_shipping_discount_amount;
    }

    public int getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(int tax_amount) {
        this.tax_amount = tax_amount;
    }

    public int getBase_tax_amount() {
        return base_tax_amount;
    }

    public void setBase_tax_amount(int base_tax_amount) {
        this.base_tax_amount = base_tax_amount;
    }

    public int getShipping_tax_amount() {
        return shipping_tax_amount;
    }

    public void setShipping_tax_amount(int shipping_tax_amount) {
        this.shipping_tax_amount = shipping_tax_amount;
    }

    public int getBase_shipping_tax_amount() {
        return base_shipping_tax_amount;
    }

    public void setBase_shipping_tax_amount(int base_shipping_tax_amount) {
        this.base_shipping_tax_amount = base_shipping_tax_amount;
    }

    public int getSubtotal_incl_tax() {
        return subtotal_incl_tax;
    }

    public void setSubtotal_incl_tax(int subtotal_incl_tax) {
        this.subtotal_incl_tax = subtotal_incl_tax;
    }

    public int getShipping_incl_tax() {
        return shipping_incl_tax;
    }

    public void setShipping_incl_tax(int shipping_incl_tax) {
        this.shipping_incl_tax = shipping_incl_tax;
    }

    public int getBase_shipping_incl_tax() {
        return base_shipping_incl_tax;
    }

    public void setBase_shipping_incl_tax(int base_shipping_incl_tax) {
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

    public int getItems_qty() {
        return items_qty;
    }

    public void setItems_qty(int items_qty) {
        this.items_qty = items_qty;
    }

    public ArrayList<ShippingResponse> getItems() {
        return items;
    }

    public void setItems(ArrayList<ShippingResponse> items) {
        this.items = items;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBase_price() {
        return base_price;
    }

    public void setBase_price(int base_price) {
        this.base_price = base_price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getRow_total() {
        return row_total;
    }

    public void setRow_total(int row_total) {
        this.row_total = row_total;
    }

    public int getBase_row_total() {
        return base_row_total;
    }

    public void setBase_row_total(int base_row_total) {
        this.base_row_total = base_row_total;
    }

    public int getRow_total_with_discount() {
        return row_total_with_discount;
    }

    public void setRow_total_with_discount(int row_total_with_discount) {
        this.row_total_with_discount = row_total_with_discount;
    }

    public int getTax_percent() {
        return tax_percent;
    }

    public void setTax_percent(int tax_percent) {
        this.tax_percent = tax_percent;
    }

    public int getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(int discount_percent) {
        this.discount_percent = discount_percent;
    }

    public int getPrice_incl_tax() {
        return price_incl_tax;
    }

    public void setPrice_incl_tax(int price_incl_tax) {
        this.price_incl_tax = price_incl_tax;
    }

    public int getBase_price_incl_tax() {
        return base_price_incl_tax;
    }

    public void setBase_price_incl_tax(int base_price_incl_tax) {
        this.base_price_incl_tax = base_price_incl_tax;
    }

    public int getRow_total_incl_tax() {
        return row_total_incl_tax;
    }

    public void setRow_total_incl_tax(int row_total_incl_tax) {
        this.row_total_incl_tax = row_total_incl_tax;
    }

    public int getBase_row_total_incl_tax() {
        return base_row_total_incl_tax;
    }

    public void setBase_row_total_incl_tax(int base_row_total_incl_tax) {
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
