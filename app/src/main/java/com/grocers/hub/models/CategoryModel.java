package com.grocers.hub.models;

public class CategoryModel {

    private String categoryName;
    private int categoryIcon;
    private boolean categoryBackground;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(Integer categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public void setCategoryIcon(int categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public boolean isCategoryBackground() {
        return categoryBackground;
    }

    public void setCategoryBackground(boolean categoryBackground) {
        this.categoryBackground = categoryBackground;
    }
}
