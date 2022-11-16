package com.isl.lionelmaquet.burger2home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Table;
import java.util.List;

@RestController
@Table(name = "test_burger")
public class TestBurgerController {

    @Autowired
    TestBurgerRepository testBurgerRepository;

    @GetMapping("/testburger")
    public List<TestBurger> index(){
        System.out.println("helloooo");
        return testBurgerRepository.findAll();
    }
}
