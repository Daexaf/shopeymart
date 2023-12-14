package com.engima.shopeymart.controller;

import com.engima.shopeymart.constant.AppPath;
import com.engima.shopeymart.dto.request.AuthRequest;
import com.engima.shopeymart.dto.response.LoginResponse;
import com.engima.shopeymart.dto.response.RegisterResponse;
import com.engima.shopeymart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public RegisterResponse registerCustomer(@RequestBody AuthRequest authRequest){
            return authService.registerCustomer(authRequest);
    }

    @PostMapping("/login")
    public LoginResponse loginCustomer(@RequestBody AuthRequest authRequest){
        return authService.login(authRequest);
    }
}
