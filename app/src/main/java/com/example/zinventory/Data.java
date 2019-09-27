package com.example.zinventory;

public class Data {
    public String title;
    public String description;
    public String quantity;

    public Data(String title, String description, String quantity) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
