package com.grocers.hub.models;

import java.util.ArrayList;

public class CategoryModel {

    private int id;
    private int parent_id;
    private boolean is_active;
    private String name;
    private int position;
    private int level;
    private String image;
    private int product_count;
    private boolean categoryBackground;
    private ArrayList<CategoryModel> children_data;

    public ArrayList<CategoryModel> getChildren_data() {
        return children_data;
    }

    public void setChildren_data(ArrayList<CategoryModel> children_data) {
        this.children_data = children_data;
    }

    public boolean isCategoryBackground() {
        return categoryBackground;
    }

    public void setCategoryBackground(boolean categoryBackground) {
        this.categoryBackground = categoryBackground;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getProduct_count() {
        return product_count;
    }

    public void setProduct_count(int product_count) {
        this.product_count = product_count;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
