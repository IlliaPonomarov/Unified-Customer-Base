package com.example.demo.controller;


import com.example.demo.model.Auction.AuctionProduct;
import com.example.demo.model.Auction.Product;
import com.example.demo.model.Login;
import com.example.demo.service.details.TimeAuction;
import com.example.demo.service.detailsService.AuctionProductDetailService;
import com.example.demo.service.detailsService.LoginDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.security.Principal;
import java.text.ParseException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LoginDetailsService loginDetailsService;

    @Autowired
    private AuctionProductDetailService auctionProductDetailService;

    private List<Long> finishDate = new ArrayList<>();

    /**
     * The main page of the site, where all auctions are located
     * @param model
     * @param principal
     * @return
     */
    @GetMapping("/")
    public String userPage(Model model, Principal principal){

        Login user = loginDetailsService.findUserByUsername(principal.getName());
        finishDate = TimeAuction.compareTimeOfAllProducts(auctionProductDetailService.findAll());

        System.out.println("Finish: " + finishDate.getClass().getSimpleName());
        if (finishDate.size() > 0 )
            finishDate.forEach(index -> {
                AuctionProduct obj = auctionProductDetailService.findById(index);
                user.getAuctionProducts().remove(obj);
                auctionProductDetailService.delete(auctionProductDetailService.findById(index));
            });



        model.addAttribute("user", user);
        model.addAttribute("items", auctionProductDetailService.findAll());


        return "userController/index";
    }

    /**
     * Jump to the selected auction by id
     *
     * @param id
     * @param model
     * @param principal
     * @return
     * @throws ParseException
     */
    @GetMapping("/product/{id}")
    public String product(@PathVariable Long id, Model model, Principal principal) throws ParseException {

        TimeAuction.compareCurrentProductTime(auctionProductDetailService.findById(id).getFinishDate());

        AuctionProduct currentAuction = auctionProductDetailService.findById(id);

        if (currentAuction.lastElement() != null)
            model.addAttribute("lastUser", currentAuction.lastElement().getValue().getUsername() + " was the last to bet " + currentAuction.lastElement().getKey());
        else
            model.addAttribute("lastUser", "No one participated in the auction!");



        model.addAttribute("endDate", currentAuction.getFinishDate());
        model.addAttribute("item", currentAuction);
        model.addAttribute("currentUser", loginDetailsService.findUserByUsername(principal.getName()));
        model.addAttribute("errorMessage", "You have less money then price of this product");

        return "/userController/single-project";
    }


    /**
     * Participation in the auction, and setting a new price for the goods
     *
     * @param newPrice
     * @param id
     * @param principal
     * @param model
     * @return
     */
    @PostMapping("/product/{id}")
    public String productSetPrice(@RequestParam("newPrice") Double newPrice,
                                  @PathVariable Long id,
                                  Principal principal,
                                  Model model){

        // Getting a current user
        Login currentUser = loginDetailsService.findUserByUsername(principal.getName());
        AuctionProduct auctionProduct = auctionProductDetailService.findById(id);

        Double oldPrice = auctionProduct.getPrice();



        if (currentUser.getMoney() >= oldPrice && oldPrice < newPrice) {

            // Update information about current user
            loginDetailsService.update(currentUser);

            // set new price for current product`
            auctionProduct.setFinishPrice(newPrice);
            double price = auctionProduct.getFinishPrice();
            System.out.println(auctionProduct.getPrice());

            // Update price for current product
            auctionProductDetailService.update(auctionProduct);


                auctionProduct.subscribeToAuction(newPrice, currentUser);
                currentUser.addAuction(auctionProduct);

                loginDetailsService.save(currentUser);
                auctionProductDetailService.save(auctionProduct);

                auctionProduct.iterate();
        }
        else {
            return "redirect:/user/product/" + id;
        }

        return "redirect:/user";
    }











}
