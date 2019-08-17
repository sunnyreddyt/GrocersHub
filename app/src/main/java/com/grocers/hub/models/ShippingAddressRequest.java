package com.grocers.hub.models;

import org.json.JSONArray;

import java.util.ArrayList;

public class ShippingAddressRequest {

    private ShippingAddressRequest addressInformation;
    private String shipping_carrier_code;
    private String shipping_method_code;
    private ShippingAddressRequest shipping_address;
    private String region;
    private int region_id;
    private String region_code;
    private String country_id;
    private String postcode;
    private String city;
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
    private int same_as_billing;
    JSONArray street;

    public ShippingAddressRequest getAddressInformation() {
        return addressInformation;
    }

    public void setAddressInformation(ShippingAddressRequest addressInformation) {
        this.addressInformation = addressInformation;
    }

    public String getShipping_carrier_code() {
        return shipping_carrier_code;
    }

    public void setShipping_carrier_code(String shipping_carrier_code) {
        this.shipping_carrier_code = shipping_carrier_code;
    }

    public String getShipping_method_code() {
        return shipping_method_code;
    }

    public void setShipping_method_code(String shipping_method_code) {
        this.shipping_method_code = shipping_method_code;
    }

    public ShippingAddressRequest getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(ShippingAddressRequest shipping_address) {
        this.shipping_address = shipping_address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getSame_as_billing() {
        return same_as_billing;
    }

    public void setSame_as_billing(int same_as_billing) {
        this.same_as_billing = same_as_billing;
    }

    public JSONArray getStreet() {
        return street;
    }

    public void setStreet(JSONArray street) {
        this.street = street;
    }
}
