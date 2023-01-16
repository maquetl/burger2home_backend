package com.isl.lionelmaquet.burger2home.User;

import com.isl.lionelmaquet.burger2home.Utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    @PreAuthorize("hasRole('ADMIN')")
    List<User> getAllUsers(){
        return serv.getAllUsers();
    }

    @GetMapping("/users/{userIdentifier}")
    @PreAuthorize("#userIdentifier == authentication.principal.id or hasRole('ADMIN')")
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
    @PreAuthorize("#user.id == authentication.principal.id or hasRole('ADMIN')")
    User modifyUser(@RequestBody User user){

        // Only the admin can modify the role
        serv.getSingleUser(user.getId()).ifPresentOrElse(u -> {
            if (u.getRole() != user.getRole() && !AuthUtils.currentUserIsAdmin()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }, () -> {throw new ResponseStatusException(HttpStatus.NO_CONTENT);});

        return serv.modifyUser(user);
    }
}
