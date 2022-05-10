package com.example.demo.service.details;

import com.example.demo.model.Auction.AuctionProduct;

import java.util.*;
import java.time.Instant;

public class TimeAuction {

    // Here I am comparing the current time with the product time
    public static int compareCurrentProductTime(String auctionDate){

        String current = String.valueOf(Instant.now()).substring(0, 16);

        return current.compareTo(auctionDate);

    }

    //Search for items that have run out of time
    public static List<Long> compareTimeOfAllProducts(List<AuctionProduct> auctionProductList){

        List<Long> end = new ArrayList<>();

        for (AuctionProduct product: auctionProductList)
            if (compareCurrentProductTime(product.getFinishDate()) == 0)
                end.add(product.getId());



        return end;

    }


}
