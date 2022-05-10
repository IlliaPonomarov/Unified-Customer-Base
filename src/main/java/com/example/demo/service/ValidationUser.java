package com.example.demo.service;

public interface ValidationUser {
    default boolean validfoUsername(String username){
        if (username.equals(null))
            return false;
        return true;
    }

}
