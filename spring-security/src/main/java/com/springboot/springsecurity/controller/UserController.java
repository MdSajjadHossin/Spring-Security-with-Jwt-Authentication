package com.springboot.springsecurity.controller;

import com.springboot.springsecurity.entity.AuthRequest;
import com.springboot.springsecurity.entity.UserInfo;
import com.springboot.springsecurity.service.JwtService;
import com.springboot.springsecurity.service.UserInfoService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome ......";
    }

    @PostMapping("/addUser")
    public String adduser(@RequestBody UserInfo userInfo){
        return userInfoService.addUser(userInfo);
    }

    @GetMapping("/login")
    public String login(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

        if(authenticate. isAuthenticated()){
            return jwtService.generateToken(authRequest.getUserName());
        }else{
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    @GetMapping("/getUser")
    public List<UserInfo> getAllUser(){
        return userInfoService.getAllUser();
    }

    @GetMapping("/getUsers/{id}")
    public UserInfo getUser(@PathVariable Integer id){
        return userInfoService.getUser(id);
    }

}
