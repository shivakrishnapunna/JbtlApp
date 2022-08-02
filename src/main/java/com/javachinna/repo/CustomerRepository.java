/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.javachinna.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.javachinna.model.Customer;

/**
 *
 * @author punna31
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByTinNumber(String id);
    Customer findByEmail(String email);
    
    
}
