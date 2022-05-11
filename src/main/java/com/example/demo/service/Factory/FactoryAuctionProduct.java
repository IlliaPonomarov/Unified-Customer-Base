package com.example.demo.service.Factory;

import com.example.demo.model.Auction.AuctionProduct;
import com.example.demo.model.Login;
import com.example.demo.service.Product;

public class FactoryAuctionProduct extends Factory {

    private AuctionProduct auctionProduct;

    @Override
    public AuctionProduct createActionProduct() {

        auctionProduct = new AuctionProduct();

        return auctionProduct;
    }

    @Override
    public void buy() {

    }

    @Override
    public Product getObject() {
        return auctionProduct;
    }



}
