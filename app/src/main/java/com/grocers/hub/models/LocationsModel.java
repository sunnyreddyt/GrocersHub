package com.grocers.hub.models;

import java.util.ArrayList;

public class LocationsModel {

    private int status;
    private String message, city, postcode;
    private ArrayList<LocationsModel> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public ArrayList<LocationsModel> getData() {
        return data;
    }

    public void setData(ArrayList<LocationsModel> data) {
        this.data = data;
    }
}
