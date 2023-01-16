package com.isl.lionelmaquet.burger2home.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RoleController {

    @Autowired
    RoleService serv;

    @GetMapping("/roles")
    List<Role> getAllRoles(){
        return serv.getAllRoles();
    }

    @GetMapping("/roles/{roleIdentifier}")
    Optional<Role> getSingleRole(@PathVariable Integer roleIdentifier){
        return serv.getSingleRole(roleIdentifier);
    }

    @GetMapping("/users/{userIdentifier}/role")
    @PreAuthorize("#userIdentifier == authentication.principal.id or hasRole('ADMIN')")
    Optional<Role> getRoleByUser(@PathVariable Integer userIdentifier){
        return serv.getRoleByUser(userIdentifier);
    }

    @PostMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    Role createRole(@RequestBody Role role){
        return serv.createRole(role);
    }

    @PutMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    Role modifyRole(@RequestBody Role role){
        return serv.modifyRole(role);
    }
}
