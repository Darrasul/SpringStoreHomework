package com.buzas.springstorehomework.services;

import com.buzas.springstorehomework.entities.roles.Role;
import com.buzas.springstorehomework.entities.roles.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepo;

    public List<Role> findAll() {
        return roleRepo.findAll();
    }
}
