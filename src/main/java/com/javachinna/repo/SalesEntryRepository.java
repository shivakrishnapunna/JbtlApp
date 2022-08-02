/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.javachinna.repo;

import com.javachinna.model.SalesEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author punna31
 */

@Repository
public interface SalesEntryRepository extends JpaRepository<SalesEntry, String> {

    SalesEntry findByUserId(String id);

}