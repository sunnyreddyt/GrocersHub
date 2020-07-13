package com.grocers.hub.models;

public class MinimumOrderResponse {

    private int success;
    private MinimumOrderResponse data;
    private int MinimumOrder;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public MinimumOrderResponse getData() {
        return data;
    }

    public void setData(MinimumOrderResponse data) {
        this.data = data;
    }

    public int getMinimumOrder() {
        return MinimumOrder;
    }

    public void setMinimumOrder(int minimumOrder) {
        MinimumOrder = minimumOrder;
    }
}
