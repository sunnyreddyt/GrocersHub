package com.grocers.hub.models;

import java.util.ArrayList;

public class ProductsResponse {

    private String status;
    private ArrayList<ProductsResponse> products;
    private int qty, id, cartQuantity,maxQtyAllowed;
    private double price, finalPrice;
    private String name, sku, image, product_type;
    ArrayList<ProductOptions> options;

    public int getMaxQtyAllowed() {
        return maxQtyAllowed;
    }

    public void setMaxQtyAllowed(int maxQtyAllowed) {
        this.maxQtyAllowed = maxQtyAllowed;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ProductsResponse> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductsResponse> products) {
        this.products = products;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public ArrayList<ProductOptions> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<ProductOptions> options) {
        this.options = options;
    }

    public class ProductOptions {
        private String sku, price, option_title, default_title, super_attribute_label, value_index, attribute_code, product_id;
        private int qty, cartQuantity,maxQtyAllowed;
        private double finalPrice;

        public int getMaxQtyAllowed() {
            return maxQtyAllowed;
        }

        public void setMaxQtyAllowed(int maxQtyAllowed) {
            this.maxQtyAllowed = maxQtyAllowed;
        }

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

        public void setFinalPrice(double finalPrice) {
            this.finalPrice = finalPrice;
        }

        public double getFinalPrice() {
            return finalPrice;
        }
    }

}
