package com.isl.lionelmaquet.burger2home.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository rep;

    @Override
    public List<User> getAllUsers() {
        return rep.findAll();
    }

    @Override
    public Optional<User> getSingleUser(Integer userIdentifier) {
        return rep.findById(userIdentifier);
    }

    @Override
    public User createUser(User user) {
        return rep.save(user);
    }

    @Override
    public User modifyUser(User user) {
        return rep.save(user);
    }

    public void processOAuthPostLogin(String username) {
        Optional<User> existUser = rep.findByUsername(username);

        if (!existUser.isPresent()) {
            User newUser = new User();
            newUser.setUsername(username);
//            newUser.setProvider(Provider.GOOGLE);
//            newUser.setEnabled(true);

            rep.save(newUser);
        }

    }

    @Override
    public boolean verify(String verificationCode) {
        User user = rep.findByVerificationCode(verificationCode);

        if (user == null || user.getEnabled()){
            return false;
        }
        user.setVerificationCode(null);
        user.setEnabled(true);
        rep.save(user);

        return true;
    }
}
