package com.isl.lionelmaquet.burger2home.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeRepository typeRepository;

    @Override
    public List<Type> getAll() {
        return typeRepository.findAll();
    }

    @Override
    public Type create(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type getById(Integer typeIdentifier) {
        return typeRepository.findById(typeIdentifier).get();
    }
}
