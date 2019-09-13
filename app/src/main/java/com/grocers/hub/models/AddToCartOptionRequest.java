package com.grocers.hub.models;

import java.util.ArrayList;

public class AddToCartOptionRequest {

    private CartItem cartItem;

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public static class ConfigurableItemOptions {
        int option_value;
        String option_id;

        public int getOption_value() {
            return option_value;
        }

        public void setOption_value(int option_value) {
            this.option_value = option_value;
        }

        public String getOption_id() {
            return option_id;
        }

        public void setOption_id(String option_id) {
            this.option_id = option_id;
        }
    }

    public static class ExtensionAttributes {
        ArrayList<ConfigurableItemOptions> configurable_item_options;

        public ArrayList<ConfigurableItemOptions> getConfigurable_item_options() {
            return configurable_item_options;
        }

        public void setConfigurable_item_options(ArrayList<ConfigurableItemOptions> configurable_item_options) {
            this.configurable_item_options = configurable_item_options;
        }
    }

    public static class ProductOption {
        ExtensionAttributes extension_attributes;

        public ExtensionAttributes getExtension_attributes() {
            return extension_attributes;
        }

        public void setExtension_attributes(ExtensionAttributes extension_attributes) {
            this.extension_attributes = extension_attributes;
        }
    }

    public static class CartItem {
        ProductOption product_option;
        private int qty, quote_id;
        private String sku, product_type;

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public int getQuote_id() {
            return quote_id;
        }

        public void setQuote_id(int quote_id) {
            this.quote_id = quote_id;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }

        public ProductOption getProduct_option() {
            return product_option;
        }

        public void setProduct_option(ProductOption product_option) {
            this.product_option = product_option;
        }
    }

}
