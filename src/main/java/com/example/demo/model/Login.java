package com.example.demo.model;


import com.example.demo.model.Auction.AuctionProduct;
import com.example.demo.service.ValidationUser;
import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("CASE WHEN id IS NOT NULL THEN 'admin' ELSE 'worker' END")
public class Login implements Serializable, ValidationUser {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "money")
    private Double money;

    @Column(name = "role")
    private String role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "auction_ta",
    joinColumns = @JoinColumn(name = "person_id"),
    inverseJoinColumns = @JoinColumn(name = "auction_id"))
    Set<AuctionProduct> auctionProducts = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_id")
    List<AuctionProduct> soldProducts = new ArrayList<>();


    public Set<AuctionProduct> getAuctionProducts() {
        return auctionProducts;
    }

    public void setAuctionProducts(Set<AuctionProduct> auctionProducts) {
        this.auctionProducts = auctionProducts;
    }

    public Login(Set<AuctionProduct> auctionProducts) {
        this.auctionProducts = auctionProducts;
    }

    private String confrimPassword;

    public Login(){}

    public Login(String username, String password, String phoneNumber, String email, String role) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }


    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfrimPassword() {
        return confrimPassword;
    }

    public void setConfrimPassword(String confrimPassword) {
        if (confrimPassword.equals(this.password))
            this.confrimPassword = confrimPassword;
    }

    public void addAuction(AuctionProduct auctionProduct){
        auctionProducts.add(auctionProduct);
    }

    public void removeAuction(AuctionProduct auctionProduct){
        auctionProducts.remove(auctionProduct);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
