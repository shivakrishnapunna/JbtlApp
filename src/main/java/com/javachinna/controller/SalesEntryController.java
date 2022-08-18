/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.controller;

import com.javachinna.dto.ApiResponse;
import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.SalesEntryProducts;
import com.javachinna.service.CustomerService;
import com.javachinna.service.SalesCollectionService;

import com.javachinna.service.SalesEntryService;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.JSONStyle;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author punna31
 */
@RestController
@RequestMapping("/api/user")
public class SalesEntryController {

    @Autowired
    SalesEntryService salesEntryService;

    @Autowired
    SalesCollectionService salesCollectionService;

    @Autowired
    CustomerService customerService;

    //nested products saels entry
    @PostMapping("/{userid}/saleentry/{customerid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> SalesEntryTest(@PathVariable("customerid") int customerid, @PathVariable("userid") String id, HttpServletRequest servletRequest, HttpServletResponse response,
            @RequestBody SalesEntryProducts salesEntry) {

        String res = "";
        try {
            UUID uuid = UUID.randomUUID(); //Generates random UUID 
            JSONObject SaleEntry = salesEntryService.SaleEntry(salesEntry, customerid, id, uuid);
            res= SaleEntry.toString(JSONStyle.NO_COMPRESS);

        } catch (UserAlreadyExistAuthenticationException e) {
            System.err.println("Exception Ocurred" + e);
            return new ResponseEntity<>(new ApiResponse(false, "stockAdjust not able to add to sp!"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(new ApiResponse(true, res));

    }

}
