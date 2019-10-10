package com.grocers.hub.models;

public class ApplyCouponResponse {

    private ApplyCouponResponse cartDetails;
    private int status, entity_id;
    private double base_subtotal_with_discount, base_shipping_amount;
    private String coupon_code, message;

    public ApplyCouponResponse getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(ApplyCouponResponse cartDetails) {
        this.cartDetails = cartDetails;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(int entity_id) {
        this.entity_id = entity_id;
    }

    public double getBase_subtotal_with_discount() {
        return base_subtotal_with_discount;
    }

    public void setBase_subtotal_with_discount(double base_subtotal_with_discount) {
        this.base_subtotal_with_discount = base_subtotal_with_discount;
    }

    public double getBase_shipping_amount() {
        return base_shipping_amount;
    }

    public void setBase_shipping_amount(double base_shipping_amount) {
        this.base_shipping_amount = base_shipping_amount;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
