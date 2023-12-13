package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.entity.AppUser;
import com.engima.shopeymart.entity.UserCredential;
import com.engima.shopeymart.repository.UserCredentialRepository;
import com.engima.shopeymart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public AppUser loadUserByUserId(String id) { //method ini untuk verifikasi JWT
        UserCredential userCredential = userCredentialRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("invalid credential"));
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRole().getName())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        //method ini digunakan untuk cek by usernamenya sebagai authentication untuk login
        UserCredential userCredential = userCredentialRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("invalid credential"));
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRole().getName())
                .build();
    }
}
