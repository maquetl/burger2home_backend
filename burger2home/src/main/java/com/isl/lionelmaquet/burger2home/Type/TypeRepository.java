package com.isl.lionelmaquet.burger2home.Type;

import com.isl.lionelmaquet.burger2home.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
}
