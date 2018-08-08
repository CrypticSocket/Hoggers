package com.crypticsocket.hoggers;

/**
 * Created by saket on 22-Dec-17.
 */

public class Food {

    private String name, price, image;

    public Food(){

    }

    public Food(String name, String price, String image){
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
