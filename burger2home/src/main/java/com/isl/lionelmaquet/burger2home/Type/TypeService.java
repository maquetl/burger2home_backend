package com.isl.lionelmaquet.burger2home.Type;

import java.util.List;

public interface TypeService {
    List<Type> getAll();

    Type create(Type type);

    Type getById(Integer typeIdentifier);
}
