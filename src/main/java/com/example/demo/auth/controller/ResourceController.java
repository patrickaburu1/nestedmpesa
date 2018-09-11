package com.example.demo.auth.controller;

import com.example.demo.auth.domain.RandomCity;
import com.example.demo.auth.domain.Role;
import com.example.demo.auth.domain.User;
import com.example.demo.auth.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


@RestController
@RequestMapping("/springjwt")
public class ResourceController {
    @Autowired
    private GenericService userService;

    private Logger logger=Logger.getLogger(RestController.class.getSimpleName());

    @RequestMapping(value ="/cities")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('CUSTOMER_USER')")
    public List<RandomCity> getUser(){
        return userService.findAllRandomCities();
    }

    @RequestMapping(value ="/users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public List<User> getUsers(){
        return userService.findAllUsers();
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER') or hasAuthority('CUSTOMER_USER')")
    public List<Role> getRoles(){

        /*trying to read token data but failed*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object details = authentication.getDetails();

        if ( details instanceof OAuth2AuthenticationDetails){
            OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails)details;

            Map<String, Object> decodedDetails = (Map<String, Object>)oAuth2AuthenticationDetails.getDecodedDetails();

            logger.info("\n"+"token data "+oAuth2AuthenticationDetails+"\n");
        }

        return userService.findAllRoles();
    }

}
