package com.grocers.hub.models;

import java.util.ArrayList;

public class UpdateProfileRequest {

    private UpdateCustomer customer;

    public UpdateCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(UpdateCustomer customer) {
        this.customer = customer;
    }

    public static class UpdateCustomer {
        private int id;
        private String dob;
        private String email;
        private String firstname;
        private String lastname;
        private String middlename;
        private int website_id;
        private String mobile;
        private ArrayList<CustomAttributes> custom_attributes;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getMiddlename() {
            return middlename;
        }

        public void setMiddlename(String middlename) {
            this.middlename = middlename;
        }

        public int getWebsite_id() {
            return website_id;
        }

        public void setWebsite_id(int website_id) {
            this.website_id = website_id;
        }

        public ArrayList<CustomAttributes> getCustom_attributes() {
            return custom_attributes;
        }

        public void setCustom_attributes(ArrayList<CustomAttributes> custom_attributes) {
            this.custom_attributes = custom_attributes;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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
}
