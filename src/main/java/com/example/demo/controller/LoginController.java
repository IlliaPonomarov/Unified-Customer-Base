package com.example.demo.controller;

import com.example.demo.model.Login;
import com.example.demo.service.detailsService.LoginDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {

    private LoginDetailsService loginDetailsService;


    @Autowired
    public LoginController(LoginDetailsService loginDetailsService) {
        this.loginDetailsService = loginDetailsService;
    }

    private Login login = null;

    /**
     *
     * Redirects to user page
     */
    //login
    @GetMapping("/user")
    public String user() {
        return ("redirect:/user/");
    }

    /**
     *
     * Redirects to login page
     */
    @GetMapping("/login")
    public String login() {

        return ("loginController/login");
    }

    /**
     *
     * Redirects to admin page
     */
    //admin
    @GetMapping("/admin")
    public String admin() {
        return "adminController/adminPage";
    }

}
