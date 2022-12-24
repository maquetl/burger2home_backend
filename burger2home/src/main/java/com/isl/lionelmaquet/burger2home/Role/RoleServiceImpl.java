package com.isl.lionelmaquet.burger2home.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository rep;

    @Override
    public List<Role> getAllRoles() {
        return rep.findAll();
    }

    @Override
    public Optional<Role> getSingleRole(Integer roleIdentifier) {
        return rep.findById(roleIdentifier);
    }

    @Override
    public Role createRole(Role role) {
        return rep.save(role);
    }

    @Override
    public Role modifyRole(Role role) {
        return rep.save(role);
    }

    @Override
    public Optional<Role> getRoleByUser(Integer userIdentifier) {
        return rep.findByUserId(userIdentifier);
    }
}
