package com.example.demo.service.detailsService;


import com.example.demo.model.Auction.SimpleProduct;
import com.example.demo.repo.action.SimpleProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleProductDetailService {

    private SimpleProductRepository simpleProductRepository;

    @Autowired
    public SimpleProductDetailService(SimpleProductRepository simpleProductRepository) {
        this.simpleProductRepository = simpleProductRepository;
    }

    /**
     * Saving a regular product to the database
     * @param simpleProduct
     */
    public void save(SimpleProduct simpleProduct){
        simpleProductRepository.save(simpleProduct);
    }


}
