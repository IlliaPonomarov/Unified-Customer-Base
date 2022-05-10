package com.example.demo.repo.action;

import com.example.demo.model.Auction.AuctionProduct;
import org.springframework.data.repository.CrudRepository;

public interface AuctionProductRepository extends CrudRepository<AuctionProduct, Long> {
}
