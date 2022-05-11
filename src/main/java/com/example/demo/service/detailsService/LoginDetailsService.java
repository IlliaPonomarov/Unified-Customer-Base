package com.example.demo.service.detailsService;

import com.example.demo.model.Auction.AuctionProduct;
import com.example.demo.model.Login;
import com.example.demo.repo.LoginRepository;
import com.example.demo.service.details.LoginDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class LoginDetailsService implements UserDetailsService, SerializationUser.SerializationOneImplementation {

    @Autowired
    private LoginRepository loginRepository;

    public LoginDetailsService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    /**
     * Updating the user in the database
     * @param newUser
     */
    public void update(Login newUser){
        loginRepository.save(newUser);
    }


    /**
     * Deleting the user in the database
     * @param user
     */
    public void delete(Login user){
        loginRepository.delete(user);
    }


    /**
     * Adding the user in the database
     * @param entity
     */
    public void save(Login entity) {

        serialization(new File("persons.json"), entity);
       loginRepository.save(entity);
    }


    /**
     * Load from database by nickname
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Login login = loginRepository.findByUsername(username);

        if (login == null) throw new UsernameNotFoundException("Not found " + username);


        return new LoginDetails(login);
    }

    /**
     * Search by username
     * @param username
     * @return
     */
    public Login findUserByUsername(String username){

        Login login = loginRepository.findByUsername(username);

        if (login == null) throw new UsernameNotFoundException("Not found " + username);


        return login;
    }


    /**
     * Getting all users from the database
     * @return
     */
    public List<Login> findAll(){
        List<Login> logins = new ArrayList<>();
        loginRepository.findAll().forEach(index -> { logins.add(index);});

        return logins;
    }



}
