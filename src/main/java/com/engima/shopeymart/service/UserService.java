package com.engima.shopeymart.service;

import com.engima.shopeymart.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AppUser loadUserByUserId(String id);


}
