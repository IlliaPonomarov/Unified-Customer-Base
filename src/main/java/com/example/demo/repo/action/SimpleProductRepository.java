package com.example.demo.repo.action;

import com.example.demo.model.Auction.SimpleProduct;
import org.springframework.data.repository.CrudRepository;

public interface SimpleProductRepository extends CrudRepository<SimpleProduct, Long> {
}
