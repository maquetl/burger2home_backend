package com.isl.lionelmaquet.burger2home.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService serv;

    @GetMapping("/users")
    List<User> getAllUsers(){
        return serv.getAllUsers();
    }

    @GetMapping("/users/{userIdentifier}")
    Optional<User> getSingleUser(@PathVariable Integer userIdentifier){
        return serv.getSingleUser(userIdentifier);
    }

    @PostMapping("/users")
    void createUser(@RequestBody User user){
        serv.createUser(user);
    }

    @PutMapping("/users")
    void modifyUser(@RequestBody User user){
        serv.modifyUser(user);
    }

    @DeleteMapping("/users/{userIdentifier}")
    void deleteUser(@PathVariable Integer userIdentifier){
        serv.deleteUser(userIdentifier);
    }
}
