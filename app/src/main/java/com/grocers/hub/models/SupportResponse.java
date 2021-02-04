package com.grocers.hub.models;

import java.util.ArrayList;

public class SupportResponse {

    private String City;
    private String email;
    private String phone;
    private String addrress;
    private ArrayList<SupportResponse> kamreddy;
    private ArrayList<SupportResponse> Hyderabad;
    private ArrayList<SupportResponse> Nizambad;
    private SupportResponse location;

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddrress() {
        return addrress;
    }

    public void setAddrress(String addrress) {
        this.addrress = addrress;
    }

    public ArrayList<SupportResponse> getKamreddy() {
        return kamreddy;
    }

    public void setKamreddy(ArrayList<SupportResponse> kamreddy) {
        this.kamreddy = kamreddy;
    }

    public ArrayList<SupportResponse> getHyderabad() {
        return Hyderabad;
    }

    public void setHyderabad(ArrayList<SupportResponse> hyderabad) {
        Hyderabad = hyderabad;
    }

    public ArrayList<SupportResponse> getNizambad() {
        return Nizambad;
    }

    public void setNizambad(ArrayList<SupportResponse> nizambad) {
        Nizambad = nizambad;
    }

    public SupportResponse getLocation() {
        return location;
    }

    public void setLocation(SupportResponse location) {
        this.location = location;
    }
}
