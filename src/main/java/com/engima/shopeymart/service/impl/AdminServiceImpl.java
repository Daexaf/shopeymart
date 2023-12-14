package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.dto.request.AuthRequest;
import com.engima.shopeymart.dto.response.AdminResponse;
import com.engima.shopeymart.dto.response.CustomerResponse;
import com.engima.shopeymart.entity.Admin;
import com.engima.shopeymart.entity.Customer;
import com.engima.shopeymart.repository.AdminRepository;
import com.engima.shopeymart.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    @Override
    public AdminResponse createAdmin(AuthRequest authRequest) {
        Admin admin = Admin.builder()
                .name(authRequest.getName())
                .phoneNumber(authRequest.getMobilePhone())
                .build();
        adminRepository.save(admin);
        return getAdminResponse(admin);
    }

    @Override
    public AdminResponse createAdmins(Admin request) {
        Admin admin = adminRepository.saveAndFlush(request);
        return AdminResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .phoneNumber(admin.getPhoneNumber())
                .build();
    }

    private AdminResponse getAdminResponse(Admin admin) {
        return AdminResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .phoneNumber(admin.getPhoneNumber())
                .build();
    }

    @Override
    public AdminResponse update(AuthRequest authRequest) {
        return null;
    }

    @Override
    public void delete(String id) {
        adminRepository.deleteById(id);
    }

    @Override
    public List<AdminResponse> getAll() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(this::getAdminResponse).collect(Collectors.toList());
    }

    @Override
    public AdminResponse getById(String id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin != null) {
            return AdminResponse.builder()
                    .id(admin.getId())
                    .name(admin.getName())
                    .phoneNumber(admin.getPhoneNumber())
                    .build();
        }
        return null;
    }
}
