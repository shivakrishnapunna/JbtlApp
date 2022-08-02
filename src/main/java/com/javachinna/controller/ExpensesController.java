/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.controller;

import com.javachinna.dto.ApiResponse;
import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.Expenses;
import com.javachinna.service.ExpensesService;
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
public class ExpensesController {

    @Autowired
    ExpensesService expensesService;

    @PostMapping("/{userid}/expenseentry")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> SalesEntry(@PathVariable("userid") String id,HttpServletRequest servletRequest, HttpServletResponse response,
            @RequestBody Expenses expense) {
        try {
            expense.setUserId(id);
            Expenses AddNewExpense = expensesService.AddNewExpense(expense);
            System.out.println("newcustomer"+AddNewExpense);
           
        } catch (UserAlreadyExistAuthenticationException e) {
            System.err.println("Exception Ocurred"+ e);
            return new ResponseEntity<>(new ApiResponse(false, "AddNewExpense Address already in use!"), HttpStatus.BAD_REQUEST);
        } 
        return ResponseEntity.ok().body(new ApiResponse(true, "AddNewExpense registered successfully"));
    }

}