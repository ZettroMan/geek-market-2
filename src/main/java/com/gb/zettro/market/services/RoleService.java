package com.gb.zettro.market.services;


import com.gb.zettro.market.entities.Role;
import com.gb.zettro.market.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findRoleByName("ROLE_USER");
    }
}
