/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.javachinna.repo;

import com.javachinna.model.StockHOtoSPDao;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author punna31
 */
@Repository
public interface StockHOtoSPRepository extends JpaRepository<StockHOtoSPDao, String> {
    Optional<StockHOtoSPDao>  findByUserId(String id);
}