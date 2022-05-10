package com.example.demo.controller;

import com.example.demo.model.Auction.AuctionProduct;
import com.example.demo.model.Login;
import com.example.demo.service.detailsService.AuctionProductDetailService;
import com.example.demo.service.detailsService.LoginDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class MenuController {

    private LoginDetailsService loginDetailsService;

    @Autowired
    private AuctionProductDetailService auctionProductDetailService;


    @Autowired
    public MenuController(LoginDetailsService loginDetailsService) {
        this.loginDetailsService = loginDetailsService;
    }

    @GetMapping("/items")
    public String items(Model model, Principal principal){

        Login user = loginDetailsService.findUserByUsername(principal.getName());
        model.addAttribute("user", user);

        Long id = 53L;
        AuctionProduct auctionProduct = auctionProductDetailService.findById(id);

        user.getSoldProducts().remove(auctionProduct);

        model.addAttribute("items", user.getSoldProducts());



        return "/userController/MyItems";
    }
}
