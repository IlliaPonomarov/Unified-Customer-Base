package com.example.demo.controller;

import com.example.demo.Exceptions.LoginException;
import com.example.demo.model.Login;
import com.example.demo.repo.LoginRepository;
import com.example.demo.service.detailsService.AuctionProductDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.security.Principal;
import java.util.Base64;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private LoginRepository loginRepository;

    @Autowired
    private AuctionProductDetailService auctionProductDetailService;
    private Login admin;


    @Autowired
    public AdminController(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }


    /**
     *
     * The method displays information about the current user.
     */
    @GetMapping("/about")
    public String about(Model model, Principal principal){

        try{
            if (loginRepository.findByUsername(principal.getName()).equals(null))
                throw new LoginException();
            else
                this.admin = loginRepository.findByUsername(principal.getName());
                model.addAttribute("admin", admin);

        }catch (LoginException exception){
            System.err.println(exception.validOfUser());
        }

        return "adminController/about";
    }

    /**
     *
     * Redirects the page for user management
     */
    @GetMapping("/usermg")
    public String userManagement(){



        return "adminController/userManagementController/mainUserMG";
    }

    /**
     *
     * @param model
     * @return
     */

    @GetMapping("/")
    public String test(Model model){


        model.addAttribute("items", auctionProductDetailService.findAll());

        for (int i = 0; i < auctionProductDetailService.findAll().size(); i++) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
                mapper.writeValue(new File("persons.json"), auctionProductDetailService.findAll().get(i));



            }catch (Exception exception){
                exception.printStackTrace();
            }
        }


        return "loginController/test";
    }


    public String currentGetName(Principal principal){
        return principal.getName();
    }
}
