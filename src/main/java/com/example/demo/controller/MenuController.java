package com.example.demo.controller;

import com.example.demo.model.Auction.AuctionProduct;
import com.example.demo.model.Auction.Product;
import com.example.demo.model.Login;
import com.example.demo.service.detailsService.AuctionProductDetailService;
import com.example.demo.service.detailsService.LoginDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     *
     * Iteratively displays all of our purchased items from the auction
     */
    @GetMapping("/items")
    public String items(Model model, Principal principal){

        Login user = loginDetailsService.findUserByUsername(principal.getName());
        model.addAttribute("user", user);
//        Long id = 62L;
//        AuctionProduct auctionProduct = auctionProductDetailService.findById(id);

        model.addAttribute("items", user.getSoldProducts());


        return "/userController/MyItems";
    }

    /**
     *
     * Getting a product by id.
     */
    @GetMapping("/items/{id}")
    public String soldItems(@PathVariable Long id, Model model){
        AuctionProduct auctionProduct = auctionProductDetailService.findById(id);

        model.addAttribute("item", auctionProduct);

        return "/userController/soldProduct";
    }

    /**
     *
     * Realization of the sale of our current item.
     */
    @PostMapping("/items/{id}")
    public String soldItemsForm(@PathVariable Long id, Model model, Principal principal){

        AuctionProduct auctionProduct = auctionProductDetailService.findById(id);
        Login login = loginDetailsService.findUserByUsername(principal.getName());

        Product product = new AuctionProduct();
        product.setPrice(login.getMoney());


        login.setMoney(login.getMoney() + auctionProduct.getPrice());

        login.deleteById(id);
        loginDetailsService.update(login);

        return "redirect:/user/items";
    }

}
