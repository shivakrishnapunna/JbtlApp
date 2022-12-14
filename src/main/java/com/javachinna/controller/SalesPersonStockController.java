/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.controller;

import com.javachinna.model.SalesPersonStock;
import com.javachinna.service.SalesPersonStockService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author punna31
 */
@RestController
@RequestMapping("/api/user")
public class SalesPersonStockController {

    @Autowired
    SalesPersonStockService salesPersonStockService;

    @GetMapping("/{userid}/userstock")
    @PreAuthorize("hasRole('USER')")
    public List<SalesPersonStock> salesPersonStock(@PathVariable("userid") String id) {

        List<SalesPersonStock> findSalesPersonByid = salesPersonStockService.findSalesPersonByid(id);

        return findSalesPersonByid;
    }

}
