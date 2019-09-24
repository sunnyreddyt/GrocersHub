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
    private ArrayList<ProductOptions> options;
    private int price;
    private int finalPrice;
    private String id;
    private String sku;
    private String image;
    private int cartQuantity;
    private String product_type;

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public ArrayList<ProductOptions> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<ProductOptions> options) {
        this.options = options;
    }

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

    public class ProductOptions {
        private String sku, price, option_title, default_title, super_attribute_label, value_index, attribute_code, product_id;
        private int qty, finalPrice,cartQuantity;

        public int getCartQuantity() {
            return cartQuantity;
        }

        public void setCartQuantity(int cartQuantity) {
            this.cartQuantity = cartQuantity;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOption_title() {
            return option_title;
        }

        public void setOption_title(String option_title) {
            this.option_title = option_title;
        }

        public String getDefault_title() {
            return default_title;
        }

        public void setDefault_title(String default_title) {
            this.default_title = default_title;
        }

        public String getSuper_attribute_label() {
            return super_attribute_label;
        }

        public void setSuper_attribute_label(String super_attribute_label) {
            this.super_attribute_label = super_attribute_label;
        }

        public String getValue_index() {
            return value_index;
        }

        public void setValue_index(String value_index) {
            this.value_index = value_index;
        }

        public String getAttribute_code() {
            return attribute_code;
        }

        public void setAttribute_code(String attribute_code) {
            this.attribute_code = attribute_code;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public int getFinalPrice() {
            return finalPrice;
        }

        public void setFinalPrice(int finalPrice) {
            this.finalPrice = finalPrice;
        }
    }
}
