package com.example.ecom.models;

import java.io.Serializable;

public class AdminShowAllModel implements Serializable {
    String description;
    String name;
    String rating;
    int price;
    String Img_url;
    String type;
    int qty;

    public AdminShowAllModel(String description, String name, String rating, int price, String Img_url, String type, int qty) {
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.Img_url = Img_url;
        this.type = type;
        this.qty = qty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg_url() {
        return Img_url;
    }

    public void setImg_url(String img_url) {
        Img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public AdminShowAllModel() {
    }
}
