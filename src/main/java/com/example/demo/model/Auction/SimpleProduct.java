package com.example.demo.model.Auction;

import com.example.demo.service.Product;

import javax.persistence.*;

@Entity
@Table(name = "simple_product")
public class SimpleProduct extends com.example.demo.model.Auction.Product implements Product {


   private Integer count;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    public SimpleProduct(){}

    @Override
    public void buy() {

    }

    @Override
    public SimpleProduct getObject() {
        return this;
    }


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }



    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }
}
