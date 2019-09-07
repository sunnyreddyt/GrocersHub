package com.grocers.hub.models;

public class AddAddressRequest {

    private AddAddressRequest address, region;
    private int customer_id, region_id;
    private String region_code, country_id, street, telephone, postcode, city, firstname, lastname;
    private boolean default_shipping, default_billing;

    public AddAddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddAddressRequest address) {
        this.address = address;
    }

    public AddAddressRequest getRegion() {
        return region;
    }

    public void setRegion(AddAddressRequest region) {
        this.region = region;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public boolean isDefault_shipping() {
        return default_shipping;
    }

    public void setDefault_shipping(boolean default_shipping) {
        this.default_shipping = default_shipping;
    }

    public boolean isDefault_billing() {
        return default_billing;
    }

    public void setDefault_billing(boolean default_billing) {
        this.default_billing = default_billing;
    }
}
