package com.grocers.hub.models;

public class UpdateOrderStatusRequest {

    private Payment payment;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
