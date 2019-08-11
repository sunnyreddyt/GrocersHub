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