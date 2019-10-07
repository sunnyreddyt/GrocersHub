package com.grocers.hub.models;

import java.util.ArrayList;

public class EditProfileResponse {

    private int id;
    private String email,firstname,lastname;
    ArrayList<EditProfileResponse> custom_attributes;
    private String attribute_code;
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public ArrayList<EditProfileResponse> getCustom_attributes() {
        return custom_attributes;
    }

    public void setCustom_attributes(ArrayList<EditProfileResponse> custom_attributes) {
        this.custom_attributes = custom_attributes;
    }

    public String getAttribute_code() {
        return attribute_code;
    }

    public void setAttribute_code(String attribute_code) {
        this.attribute_code = attribute_code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
