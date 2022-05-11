package com.example.demo.service.EventManager;

import com.example.demo.model.Auction.AuctionProduct;
import com.example.demo.model.Login;
import com.example.demo.service.Email.SendEmail;

import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface NotifyUpdate {

    default void update(AuctionProduct auctionProduct, Map<Double, Login> participantsAuction){
        Set<String> emails = new HashSet<>();

        if (participantsAuction.size() != 0) {
            for (Map.Entry<Double, Login> email : participantsAuction.entrySet())
                if (email.getValue().getEmail() != null)
                    emails.add(email.getValue().getEmail());

            emails.forEach(email -> {
                try {
                    SendEmail.sendEmails(email, auctionProduct);
                    System.out.println(email);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
