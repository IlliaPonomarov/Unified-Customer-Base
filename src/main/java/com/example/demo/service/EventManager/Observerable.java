package com.example.demo.service.EventManager;

import com.example.demo.model.Login;

import javax.mail.MessagingException;

/**
 * Contains methods for adding, removing, and notifying changes to an object
 */
public interface Observerable {
    void subscribeToAuction(Double bitten, Login login);
    void unsubscribeToAuction(Login login);
    void notifyAuction() throws MessagingException;
}
