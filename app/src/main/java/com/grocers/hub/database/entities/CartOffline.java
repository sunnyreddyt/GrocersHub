package com.grocers.hub.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity
public class CartOffline implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "qty")
    private int qty;

    @ColumnInfo(name = "skuID")
    private String skuID;

    @ColumnInfo(name = "product_id")
    private int product_id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "product_type")
    private String product_type;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "value_index")
    private String value_index;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "finalPrice")
    private int finalPrice;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSkuID() {
        return skuID;
    }

    public void setSkuID(String skuID) {
        this.skuID = skuID;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getValue_index() {
        return value_index;
    }

    public void setValue_index(String value_index) {
        this.value_index = value_index;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }
}