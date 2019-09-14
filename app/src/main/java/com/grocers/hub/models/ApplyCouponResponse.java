package com.grocers.hub.models;

public class ApplyCouponResponse {

    private ApplyCouponResponse totals;
    private int grand_total, base_grand_total, subtotal, base_subtotal, discount_amount, base_discount_amount,
            subtotal_with_discount, base_subtotal_with_discount, shipping_amount, base_shipping_amount, shipping_discount_amount,
            tax_amount, base_tax_amount, base_shipping_tax_amount, subtotal_incl_tax, shipping_incl_tax;
    private String coupon_code;

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public ApplyCouponResponse getTotals() {
        return totals;
    }

    public void setTotals(ApplyCouponResponse totals) {
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


    /*"totals": {
        "grand_total": 1350,
                "base_grand_total": 1350,
                "subtotal": 1500,
                "base_subtotal": 1500,
                "discount_amount": -200,
                "base_discount_amount": -200,
                "subtotal_with_discount": 1300,
                "base_subtotal_with_discount": 1300,
                "shipping_amount": 50,
                "base_shipping_amount": 50,
                "shipping_discount_amount": 0,
                "base_shipping_discount_amount": 0,
                "tax_amount": 0,
                "base_tax_amount": 0,
                "weee_tax_applied_amount": null,
                "shipping_tax_amount": 0,
                "base_shipping_tax_amount": 0,
                "subtotal_incl_tax": 1500,
                "shipping_incl_tax": 50,
                "base_shipping_incl_tax": 50,
                "base_currency_code": "INR",
                "quote_currency_code": "INR",
                "coupon_code": "OFF200",
                "items_qty": 3,*/
}
