package com.isl.lionelmaquet.burger2home.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getSingleUser(Integer userIdentifier);

    void createUser(User user);

    void modifyUser(User user);

    void deleteUser(Integer userIdentifier);
}
