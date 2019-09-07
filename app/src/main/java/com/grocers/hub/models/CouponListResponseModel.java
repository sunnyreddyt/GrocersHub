package com.grocers.hub.models;

import java.util.ArrayList;

public class CouponListResponseModel {

    private ArrayList<CouponListResponseModel> items;
    private int coupon_id, rule_id, times_used, type;
    private String code;
    private boolean is_primary;
    private boolean categoryBackground;

    public boolean isCategoryBackground() {
        return categoryBackground;
    }

    public void setCategoryBackground(boolean categoryBackground) {
        this.categoryBackground = categoryBackground;
    }

    public ArrayList<CouponListResponseModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<CouponListResponseModel> items) {
        this.items = items;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public int getRule_id() {
        return rule_id;
    }

    public void setRule_id(int rule_id) {
        this.rule_id = rule_id;
    }

    public int getTimes_used() {
        return times_used;
    }

    public void setTimes_used(int times_used) {
        this.times_used = times_used;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isIs_primary() {
        return is_primary;
    }

    public void setIs_primary(boolean is_primary) {
        this.is_primary = is_primary;
    }
}
