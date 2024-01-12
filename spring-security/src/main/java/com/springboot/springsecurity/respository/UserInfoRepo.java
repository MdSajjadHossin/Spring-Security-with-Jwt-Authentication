package com.springboot.springsecurity.respository;

import com.springboot.springsecurity.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository <UserInfo, Integer>{

    Optional<UserInfo>findByName(String userName);
}
