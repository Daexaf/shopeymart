package com.engima.shopeymart.service;

import com.engima.shopeymart.dto.request.AuthRequest;
import com.engima.shopeymart.dto.response.LoginResponse;
import com.engima.shopeymart.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(AuthRequest request);

    LoginResponse login(AuthRequest authRequest);
}
