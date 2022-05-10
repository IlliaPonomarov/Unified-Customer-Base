package com.example.demo.Exceptions;

public class LoginException extends Exception{



    public String validOfUser() {
        return "User wasn't found.";
    }
}
