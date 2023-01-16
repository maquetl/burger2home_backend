package com.isl.lionelmaquet.burger2home.Auth;


import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import com.isl.lionelmaquet.burger2home.Auth.Model.JwtResponse;
import com.isl.lionelmaquet.burger2home.Auth.Model.LoginRequest;
import com.isl.lionelmaquet.burger2home.Auth.Model.MessageResponse;
import com.isl.lionelmaquet.burger2home.Auth.Model.SignupRequest;
import com.isl.lionelmaquet.burger2home.Basket.Basket;
import com.isl.lionelmaquet.burger2home.Basket.BasketService;
import com.isl.lionelmaquet.burger2home.Role.Role;
import com.isl.lionelmaquet.burger2home.Role.RoleRepository;
import com.isl.lionelmaquet.burger2home.Security.jwt.JwtUtils;
import com.isl.lionelmaquet.burger2home.Security.services.UserDetailsImpl;
import com.isl.lionelmaquet.burger2home.User.User;
import com.isl.lionelmaquet.burger2home.User.UserRepository;
import com.isl.lionelmaquet.burger2home.User.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BasketService basketService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());

        /** I removed the possibility to have anything else than a default user at signup.
         * We'll have to modify the user role later with admin rights to have some other role. */
//        String strRole = signUpRequest.getRole();
//        Role role = null;
//
//        if (strRole == null) {
//            Role userRole = roleRepository.findByName("ROLE_USER") // TODO
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            role = userRole;
//        } else {
//            switch (strRole) {
//                case "ROLE_ADMIN":
//                    Role adminRole = roleRepository.findByName("ROLE_ADMIN")
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    role = adminRole;
//                    break;
//                case "ROLE_USER":
//                    Role userRole = roleRepository.findByName("ROLE_USER")
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    role = userRole;
//                    break;
//                case "ROLE_MARKETING":
//                    Role marketingRole = roleRepository.findByName("ROLE_MARKETING")
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    role = marketingRole;
//                    break;
//                case "ROLE_STOCK":
//                    Role stockRole = roleRepository.findByName("ROLE_STOCK")
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    role = stockRole;
//                    break;
//            }
//            ;
//        }

        if (user.getUsername().equals("admin")){
            // Only for user with username admin
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            user.setRole(adminRole);
            user.setEnabled(true);
        } else {
            // For all other users
            Role role = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            user.setRole(role);
            String randomCode = RandomString.make(64);
            user.setVerificationCode(randomCode);
            user.setEnabled(false);

            try {
                sendVerificationEmail(user, signUpRequest.getVerificationUrl());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        User u = userRepository.save(user);

        Basket userBasket = new Basket();
        userBasket.setUserId(u.getId());
        userBasket.setLastUpdate(Instant.now());
        basketService.createBasket(userBasket);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private void sendVerificationEmail(User user, String verificationUrl) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "lionel.maquet@gmail.com";
        String senderName = "Burger2Home";
        String subject = "[BURGER2HOME] Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Burger2home.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstname());
        String verifyURL = verificationUrl + "?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code){
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }
}