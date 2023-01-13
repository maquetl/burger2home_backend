package com.isl.lionelmaquet.burger2home.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("")
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

    @GetMapping("/login/oauth2/code/google")
    public String callback(@RequestParam Map<String, String> requestParamMap) {
        System.out.println("Code = " + requestParamMap.get("code"));
        return "OK";
    }

    @PostMapping("/users")
    User createUser(@RequestBody User user){
        return serv.createUser(user);
    }

    @PutMapping("/users")
    User modifyUser(@RequestBody User user){
        return serv.modifyUser(user);
    }
}
