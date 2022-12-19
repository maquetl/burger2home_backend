package com.isl.lionelmaquet.burger2home.Product;

import com.isl.lionelmaquet.burger2home.ProductFamily.ProductFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE :productFamilies MEMBER OF p.productFamilies")
    List<Product> findByProductFamilies(List<ProductFamily> productFamilies);
}