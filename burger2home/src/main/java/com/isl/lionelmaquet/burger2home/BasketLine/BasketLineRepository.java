package com.isl.lionelmaquet.burger2home.BasketLine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketLineRepository extends JpaRepository<BasketLine, Integer> {
}