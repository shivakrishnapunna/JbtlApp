/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.javachinna.repo;

import com.javachinna.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author punna31
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByProduct(String product);

    boolean existsByProduct(String id);

    boolean existsById(long id);

    Optional<Product> findById(long id);

    public void deleteById(long id);

}
