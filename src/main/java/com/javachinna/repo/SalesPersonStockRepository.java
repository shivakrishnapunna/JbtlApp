/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.javachinna.repo;

import com.javachinna.model.SalesPersonStock;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author punna31
 */
@Repository
public interface SalesPersonStockRepository extends JpaRepository<SalesPersonStock, String> {

    List<SalesPersonStock> findByUserId(String id);

    public SalesPersonStock findByUserIdAndProduct(String userid, String product);

    boolean existsByUserId(String id);
    
    boolean existsByUserIdAndProduct(String userid, String product);

}
