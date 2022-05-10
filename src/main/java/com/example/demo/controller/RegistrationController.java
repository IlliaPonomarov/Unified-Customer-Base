package com.example.demo.controller;


import com.example.demo.model.Login;
import com.example.demo.service.detailsService.LoginDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private LoginDetailsService loginDetailsService;

    @Autowired
    public RegistrationController(LoginDetailsService loginDetailsService) {
        this.loginDetailsService = loginDetailsService;
    }

    @GetMapping("/add")
    public String registrationForm(Model model) throws IOException {

        Login  login = new Login();

        model.addAttribute("user", login);

        try(ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("persons.json"))
        ){
            oos.writeObject(login);

        }
        catch(Exception exception){
            System.out.println(exception.getMessage());
        }

        return "registrationController/registration";
    }

    @PostMapping("/add")
    public String registrationSubmit(@ModelAttribute Login login, Model model){

        login.setRole("ROLE_USER");
        System.out.println(login.getUsername());
        model.addAttribute("user", login);
        loginDetailsService.save(login);
        return "/loginController/test";
    }
}
