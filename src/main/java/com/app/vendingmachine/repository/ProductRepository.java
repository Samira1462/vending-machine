package com.app.vendingmachine.repository;

import com.app.vendingmachine.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
