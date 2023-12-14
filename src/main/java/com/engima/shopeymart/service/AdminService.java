package com.engima.shopeymart.service;

import com.engima.shopeymart.dto.request.AuthRequest;
import com.engima.shopeymart.dto.request.CustomerRequest;
import com.engima.shopeymart.dto.response.AdminResponse;
import com.engima.shopeymart.dto.response.CustomerResponse;
import com.engima.shopeymart.entity.Admin;

import java.util.List;

public interface AdminService {

    AdminResponse createAdmin(AuthRequest authRequest);

    AdminResponse createAdmins(Admin request);

    AdminResponse update(AuthRequest authRequest);

    void delete(String id);

    List<AdminResponse> getAll();

    AdminResponse getById(String id);

}
