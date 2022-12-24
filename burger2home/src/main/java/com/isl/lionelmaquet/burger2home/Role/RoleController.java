package com.isl.lionelmaquet.burger2home.Role;

import org.springframework.beans.factory.annotation.Autowired;
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
    Optional<Role> getRoleByUser(@PathVariable Integer userIdentifier){
        return serv.getRoleByUser(userIdentifier);
    }

    @PostMapping("/roles")
    Role createRole(@RequestBody Role role){
        return serv.createRole(role);
    }

    @PutMapping("/roles")
    Role modifyRole(@RequestBody Role role){
        return serv.modifyRole(role);
    }
}
