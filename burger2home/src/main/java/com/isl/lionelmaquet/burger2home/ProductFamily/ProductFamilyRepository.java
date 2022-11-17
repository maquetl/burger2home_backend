package com.isl.lionelmaquet.burger2home.ProductFamily;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFamilyRepository extends JpaRepository<ProductFamily, Integer> {
}