package com.springboot.springsecurity.service;

import com.springboot.springsecurity.entity.UserInfo;
import com.springboot.springsecurity.respository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepo.findByName(username);
        return userInfo.map(UserInfoDetails:: new )
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
    }

    public String addUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepo.save(userInfo);
        return "User added successful";
    }

    public List<UserInfo> getAllUser(){
        return userInfoRepo.findAll();
    }

    public UserInfo getUser(Integer id){
        return userInfoRepo.findById(id).get();
    }

}
