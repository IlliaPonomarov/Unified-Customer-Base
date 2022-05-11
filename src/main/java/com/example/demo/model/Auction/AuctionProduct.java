package com.example.demo.model.Auction;


import com.example.demo.model.Login;
import com.example.demo.service.EventManager.NotifyUpdate;
import com.example.demo.service.EventManager.Observerable;
import com.example.demo.service.Product;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "auction_product")
@Component
public class AuctionProduct
        extends com.example.demo.model.Auction.Product
        implements Product, Observerable, NotifyUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "finish_price")
    private Double finishPrice;


    @Column(name = "finish_date")
    private String finishDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "persons_auctions",
            joinColumns = @JoinColumn(name = "auction_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @MapKeyColumn(name = "bitten")
    private Map<Double, Login> participantsAuction = new LinkedHashMap<>();


    public AuctionProduct() {
    }

    public AuctionProduct(Double finishPrice, String finishDate, Map<Double, Login> participantsAuction) {
        this.finishPrice = finishPrice;
        this.finishDate = finishDate;
        this.participantsAuction = participantsAuction;
    }


    public AuctionProduct(Double finishPrice, String finishDate) {
        this.finishPrice = finishPrice;
        this.finishDate = finishDate;
    }


    /**
     *  The method iterates over all elements with participantsAuction
     */
    public void iterate(){
        for (Map.Entry<Double, Login> part: this.participantsAuction.entrySet())
            System.out.println(part.getKey() + " : " + part.getValue());
    }

    /**
     *
     * Metro picks up the last element from participantsAuction
     */
    public Map.Entry<Double, Login> lastElement(){

        final  long count = participantsAuction.entrySet().stream().count();

        for (Map.Entry<Double, Login> part: this.participantsAuction.entrySet()) {
            System.out.println(part.getKey() + " : " + part.getValue());
        }

        if (participantsAuction.size() == 0)
            return null;

        return participantsAuction.entrySet().stream().skip(count - 1).findFirst().get();
    }


    // Add user to auction

    /**
     *The method adds a new user to the Auction,
     * and sends emails to all participants about the price change.
     * @param bitten
     * @param login
     */
    @Override
    public void subscribeToAuction(Double bitten, Login login) {


            System.out.println("sub:"+login.getUsername());
            participantsAuction.put(bitten, login);

        try {
            notifyAuction();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


    /**
     *The method removes a new user from the Auction,
     * and sends all participants Emails about the price change.
     * @param login
     */
    @Override
    public void unsubscribeToAuction(Login login) {
        participantsAuction.remove(login);
        try {
            notifyAuction();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    /**
     * Sends mail to all users
     * @throws MessagingException
     */

    @Override
    public void notifyAuction() throws MessagingException {
        update(this, participantsAuction);
    }



    // Getters / Setters


    public Long getId() {
        return id;
    }

    public Double getFinishPrice() {
        return finishPrice;
    }

    public void setFinishPrice(Double finishPrice) {
        this.finishPrice = finishPrice;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Map<Double, Login> getParticipantsAuction() {
        return participantsAuction;
    }

    public void setParticipantsAuction(Map<Double, Login> participantsAuction) {
        this.participantsAuction = participantsAuction;
    }

    @Override
    public void setPrice(Double money) {
         this.finishPrice = money;
    }

    @Override
    public void buy() {

    }

    @Override
    public Product getObject() {
        return null;
    }
}
