package com.grocers.hub.models;

import java.util.ArrayList;

public class GeneralResponse {

    private String status;
    private String token;
    private int id;
    private String email;
    private String firstname;
    private String lastname;
    private String message;
    private String name;
    ArrayList<GeneralResponse> children_data;
    ArrayList<GeneralResponse> custom_attributes;
    private String attribute_code;
    private String value;
    private GeneralResponse result;
    private ArrayList<UserAddressListModel> addresses;
    private String updated_version;

    public String getUpdated_version() {
        return updated_version;
    }

    public void setUpdated_version(String updated_version) {
        this.updated_version = updated_version;
    }

    public ArrayList<UserAddressListModel> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<UserAddressListModel> addresses) {
        this.addresses = addresses;
    }

    public GeneralResponse getResult() {
        return result;
    }

    public void setResult(GeneralResponse result) {
        this.result = result;
    }

    public ArrayList<GeneralResponse> getCustom_attributes() {
        return custom_attributes;
    }

    public void setCustom_attributes(ArrayList<GeneralResponse> custom_attributes) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<GeneralResponse> getChildren_data() {
        return children_data;
    }

    public void setChildren_data(ArrayList<GeneralResponse> children_data) {
        this.children_data = children_data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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
}
