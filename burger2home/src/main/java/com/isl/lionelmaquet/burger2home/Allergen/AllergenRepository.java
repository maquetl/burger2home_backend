package com.isl.lionelmaquet.burger2home.Allergen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergenRepository extends JpaRepository<Allergen, Integer> {


}