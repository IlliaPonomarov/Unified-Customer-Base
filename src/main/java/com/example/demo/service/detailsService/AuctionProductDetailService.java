package com.example.demo.service.detailsService;


import com.example.demo.model.Auction.AuctionProduct;
import com.example.demo.repo.action.AuctionProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AuctionProductDetailService {

    private AuctionProductRepository auctionProductRepository;



    @Autowired
    public AuctionProductDetailService(AuctionProductRepository auctionProductRepository) {
        this.auctionProductRepository = auctionProductRepository;
    }


    public void save(AuctionProduct auctionProduct){
        auctionProductRepository.save(auctionProduct);
    }

    public void delete(AuctionProduct auctionProduct){
        auctionProductRepository.delete(auctionProduct);
    }

    public void update(AuctionProduct newAuction){
        auctionProductRepository.save(newAuction);
    }

    public List<AuctionProduct> findAll(){
        List<AuctionProduct>  list = new ArrayList<>();

        auctionProductRepository.findAll().forEach(index -> {
            list.add(index);
        });

        return list;
    }


    public AuctionProduct findById(Long id){

        if (auctionProductRepository.findById(id).isPresent())
            return auctionProductRepository.findById(id).get();

       return null;
    }


}
