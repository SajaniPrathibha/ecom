package com.example.ecom.models;

public class AddItem {
    private String name;
    private String type;
    private String description;
    private String img_url;
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public AddItem(String name, String type, String description, String img_url, int price) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.img_url = img_url;
        this.price = price;
    }

    public AddItem() {

    }
}
