package com.grocers.hub.models;

import java.util.ArrayList;

public class HomeResponse {

    private ArrayList<HomeResponse> banners;
    private String title;
    private int banner_id;
    private String bannerimage;
    private String link;
    private String target;
    private String sort_order;
    private String status;

    private ArrayList<HomeResponse> categoryProducts;
    private String name;
    private ArrayList<HomeResponse> products;

    private int price;
    private String id;
    private String sku;
    private String image;


    public ArrayList<HomeResponse> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<HomeResponse> banners) {
        this.banners = banners;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(int banner_id) {
        this.banner_id = banner_id;
    }

    public String getBannerimage() {
        return bannerimage;
    }

    public void setBannerimage(String bannerimage) {
        this.bannerimage = bannerimage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<HomeResponse> getCategoryProducts() {
        return categoryProducts;
    }

    public void setCategoryProducts(ArrayList<HomeResponse> categoryProducts) {
        this.categoryProducts = categoryProducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<HomeResponse> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<HomeResponse> products) {
        this.products = products;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
