package com.isl.lionelmaquet.burger2home.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TypeController {

    @Autowired
    TypeService typeService;

    @GetMapping("/types")
    List<Type> getAllTypes(){
        return typeService.getAll();
    }

    @GetMapping("/types/{typeIdentifier}")
    Type getType(@PathVariable Integer typeIdentifier){
        return typeService.getById(typeIdentifier);
    }

    @PostMapping("/types")
    Type create(@RequestBody Type type){
        return typeService.create(type);
    }

}
