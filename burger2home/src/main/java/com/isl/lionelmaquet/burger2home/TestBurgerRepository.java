package com.isl.lionelmaquet.burger2home;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestBurgerRepository extends JpaRepository<TestBurger, Integer> {
    List<TestBurger> findByName(String text);
}
