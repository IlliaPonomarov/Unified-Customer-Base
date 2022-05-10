package com.example.demo.model.Auction;


import com.example.demo.model.Login;
import com.example.demo.service.Email.SendEmail;
import com.example.demo.service.EventManager.Observerable;
import com.example.demo.service.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "auction_product")
@Component
public class AuctionProduct
        extends com.example.demo.model.Auction.Product
        implements Product, Observerable {

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


    public void iterate(){
        for (Map.Entry<Double, Login> part: this.participantsAuction.entrySet())
            System.out.println(part.getKey() + " : " + part.getValue());
    }

    public Map.Entry<Double, Login> lastElement(){

        final  long count = participantsAuction.entrySet().stream().count();

        for (Map.Entry<Double, Login> part: this.participantsAuction.entrySet()) {
            System.out.println(part.getKey() + " : " + part.getValue());
        }

        if (participantsAuction.size() == 0)
            return null;

        return participantsAuction.entrySet().stream().skip(count - 1).findFirst().get();
    }

    public AuctionProduct(Double finishPrice, String finishDate) {
        this.finishPrice = finishPrice;
        this.finishDate = finishDate;
    }

    public void errorMoney(Login login){
    }

    //Observer, add, remove, notify


    // Написать: логику для ставки нового пользователя
    // Add user to auction

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

    // Написать: проверить ставку пользователя, изменить состояние для всех пользователей
    @Override
    public void unsubscribeToAuction(Login login) {
        participantsAuction.remove(login);
        try {
            notifyAuction();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyAuction() throws MessagingException {

        Set<String> emails = new HashSet<>();

        if (participantsAuction.size() != 0) {
            for (Map.Entry<Double, Login> email : participantsAuction.entrySet())
                if (email.getValue().getEmail() != null)
                    emails.add(email.getValue().getEmail());

            emails.forEach(email -> {
                try {
                    SendEmail.sendEmails(email, this);
                    System.out.println(email);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });

        }
//        for (Map.Entry<Double, Login> part : participantsAuction.entrySet())
//            if (part.getValue().getEmail() != null) {
//                SendEmail.sendEmails(part.getValue().getEmail(), this);
//                System.out.println(part.getValue().getEmail());
//            }
    }

    ////

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
    public Product getObject() {
        return null;
    }
}
