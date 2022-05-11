package com.example.demo.service.Factory;

import com.example.demo.model.Auction.SimpleProduct;

public class FactorySimpleProduct extends Factory {

    private SimpleProduct simpleProduct;

    @Override
    public SimpleProduct createActionProduct() {
        simpleProduct = new SimpleProduct();
        return simpleProduct;
    }


    public String getPhoto() {
        return simpleProduct.getPhoto();
    }

    public Long getId() {
        return simpleProduct.getId();
    }

    @Override
    public void buy() {

    }

    @Override
    public SimpleProduct getObject() {
        return simpleProduct;
    }

    public void setPhoto(String photo) {
        simpleProduct.setPhoto(photo);
    }
}
