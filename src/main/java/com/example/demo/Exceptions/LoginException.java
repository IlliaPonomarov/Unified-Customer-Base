package com.example.demo.Exceptions;

public class LoginException extends Exception{


    public Exception validOfUser() {
        System.err.println("User wasn't found.");
        return new Exception("User wasn't found.");
    }


}
