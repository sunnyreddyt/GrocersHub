package com.grocers.hub.models;

public class PaymentRequest {

    private PaymentRequest paymentMethod;
    private String method;
    private PaymentRequest billing_address;
    private String email;
    private String region;
    private int region_id;
    private String region_code;
    private String country_id;
    private String street;
    private String postcode;
    private String city;
    private String telephone;
    private String firstname;
    private String lastname;

    public PaymentRequest getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentRequest paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public PaymentRequest getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(PaymentRequest billing_address) {
        this.billing_address = billing_address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
