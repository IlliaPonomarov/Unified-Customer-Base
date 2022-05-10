package com.example.demo.service.Factory;

import com.example.demo.service.Product;


public abstract class Factory implements Product {


  public Factory(){}

  public abstract Product createActionProduct();




  @Override
  public Product getObject() {
    return this;
  }




}
