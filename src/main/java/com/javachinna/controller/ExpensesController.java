/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.controller;

import com.javachinna.dto.ApiResponse;
import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.UserExpenses;
import com.javachinna.service.ExpensesService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/{userid}/userexpenseentry")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> UserExpenseEntry(@PathVariable("userid") String id, HttpServletRequest servletRequest, HttpServletResponse response,
            @RequestBody UserExpenses expense) {
        try {
            expense.setUserId(id);
            UserExpenses AddNewExpense = expensesService.AddNewExpense(expense);
            System.out.println("newcustomer" + AddNewExpense);

        } catch (UserAlreadyExistAuthenticationException e) {
            System.err.println("Exception Ocurred" + e);
            return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "AddNewExpense registered successfully"));
    }

    @GetMapping("/{userid}/getexpenseentry")
    @PreAuthorize("hasRole('USER')")
    public List<UserExpenses> GetSalesEntry(@PathVariable("userid") String id) {

        List<UserExpenses> findExpensesByUser = expensesService.findExpensesByUser(id);
        System.out.println("findExpensesByUser" + findExpensesByUser);

        return findExpensesByUser;
    }

}
