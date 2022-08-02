/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.javachinna.repo;

import com.javachinna.model.StockAdjust;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author punna31
 */
@Repository
public interface StockAdjustRepository extends JpaRepository<StockAdjust, String> {

    StockAdjust findByUserId(String id);

}
