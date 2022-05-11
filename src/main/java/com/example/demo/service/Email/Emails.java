package com.example.demo.service.Email;

import com.example.demo.model.Auction.AuctionProduct;
import com.example.demo.model.Auction.Product;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;


public class Emails {

    private static EmailAccount emailAccount;

    private static class EmailAccount {
        private Properties properties = new Properties();
        private static final String email = "pythonprogramatorqq@gmail.com";
        private static final String password = "lololoshka2";
        private Session session;

        public EmailAccount() {
        }

        public static String getEmail() {
            return email;
        }

        public static String getPassword() {
            return password;
        }
    }


    public static Session properties(){

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailAccount.email, EmailAccount.getPassword());
            }
        });
        return session;
    }

    public static void sendEmails(String emails, Product auctionProduct) throws MessagingException {


        if (auctionProduct.getClass().getSimpleName().equals("AuctionProduct")) {
            sendInformationAboutAuction((AuctionProduct) auctionProduct);
        }

    }

    private static void sendInformationAboutAuction(AuctionProduct auctionProduct) throws MessagingException {
        Session session = properties();

        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress("pythonprogramatorqq@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("pythonprogramatorqq@gmail.com"));
        message.setContent("This is Content", "text/html");
        message.setSentDate(new Date());

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setHeader("The price in your auction has been changed." + " Product " + auctionProduct.getName(), "text/html");
        mimeBodyPart.setContent("The price of your item has been changed to " + auctionProduct.getPrice() +
                " If you want to continue participating in the auction, then bid higher", "text/html");
        mimeBodyPart.setHeader(auctionProduct.getName(), "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        MimeBodyPart attachPart = new MimeBodyPart();
        message.setContent(multipart);

        Transport.send(message);
    }

}
