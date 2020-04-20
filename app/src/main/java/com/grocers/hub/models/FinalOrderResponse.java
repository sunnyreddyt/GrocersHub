package com.grocers.hub.models;

public class FinalOrderResponse {

    private int status;
    private String orderId;
    private String message;
    private String instamojo_order_id;

    public String getInstamojo_order_id() {
        return instamojo_order_id;
    }

    public void setInstamojo_order_id(String instamojo_order_id) {
        this.instamojo_order_id = instamojo_order_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
