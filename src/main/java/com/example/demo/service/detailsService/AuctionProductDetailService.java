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


    /**
     * Adding an auction to the database
     * @param auctionProduct
     */
    public void save(AuctionProduct auctionProduct){
        auctionProductRepository.save(auctionProduct);
    }

    /**
     * Deleting an auction to the database
     * @param auctionProduct
     */
    public void delete(AuctionProduct auctionProduct){
        auctionProductRepository.delete(auctionProduct);
    }

    /**
     * Updating the auction in the database
     * @param newAuction
     */
    public void update(AuctionProduct newAuction){
        auctionProductRepository.save(newAuction);
    }

    /**
     * Getting all auctions from the database
     * @return
     */

    public List<AuctionProduct> findAll(){
        List<AuctionProduct>  list = new ArrayList<>();

        auctionProductRepository.findAll().forEach(index -> {
            list.add(index);
        });

        return list;
    }

    /**
     * Getting auctions by id from the database
     * @param id
     * @return
     */
    public AuctionProduct findById(Long id){

        if (auctionProductRepository.findById(id).isPresent())
            return auctionProductRepository.findById(id).get();

       return null;
    }


}
