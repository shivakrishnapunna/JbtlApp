/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.service;

import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.Expenses;
import com.javachinna.repo.ExpensesRepository;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author punna31
 */

@Service
public class ExpensesService {

    @Autowired
    private ExpensesRepository expensesRepository;

    @Transactional(value = "transactionManager")
    public Expenses AddNewExpense(Expenses addStock) throws UserAlreadyExistAuthenticationException {
//        System.out.println("*****************" + stockHOtoSPRepository.findByTinNumber(signUpRequest.getTinNumber()));

        Expenses stock = buildStock(addStock);
        Date now = Calendar.getInstance().getTime();
        stock.setCreatedDate(now);
        stock.setModifiedDate(now);

        Expenses save = expensesRepository.save(stock);

        expensesRepository.flush();
        return save;
    }

    private Expenses buildStock(final Expenses formDTO) {

        Expenses expenses = new Expenses();
        expenses.setDepartment(formDTO.getDepartment());
        expenses.setExpenseType(formDTO.getExpenseType());
        expenses.setAmount(formDTO.getAmount());
        expenses.setBillNo(formDTO.getBillNo());
        expenses.setBillpic(formDTO.getBillpic());
        expenses.setRemarks(formDTO.getRemarks());
        expenses.setUserId(formDTO.getUserId());

        return expenses;
    }

//    public Customer findCustomerByEmail(final String email) {
//        return customerRepository.findByEmail(email);
//    }
}

