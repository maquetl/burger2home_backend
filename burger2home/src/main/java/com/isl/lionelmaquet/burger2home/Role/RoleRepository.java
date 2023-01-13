package com.isl.lionelmaquet.burger2home.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT u.role FROM User u WHERE u.id = ?1")
    Optional<Role> findByUserId(Integer userIdentifier);

    Optional<Role> findByName(String name);
}