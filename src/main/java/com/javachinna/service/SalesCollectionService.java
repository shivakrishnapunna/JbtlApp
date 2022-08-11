/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.service;


import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.SalesCollection;
import com.javachinna.repo.SalesCollectionRepository;

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
public class SalesCollectionService {


    
    @Autowired
    private SalesCollectionRepository salesCollectionRepository;

    @Transactional(value = "transactionManager")
    public SalesCollection AddNewSaleCollection(SalesCollection addStock) throws UserAlreadyExistAuthenticationException {
//        System.out.println("*****************" + stockHOtoSPRepository.findByTinNumber(signUpRequest.getTinNumber()));

        SalesCollection stock = buildStock(addStock);
        Date now = Calendar.getInstance().getTime();
        stock.setCreatedDate(now);
        stock.setModifiedDate(now);
        System.out.println("Collection : "+stock.getCustomerId());
        SalesCollection save = salesCollectionRepository.save(stock);

        salesCollectionRepository.flush();
        return save;
    }

    private SalesCollection buildStock(final SalesCollection formDTO) {

        SalesCollection salesEntry = new SalesCollection();
        salesEntry.setTodaySaleCollectionAmount(formDTO.getTodaySaleCollectionAmount());
        salesEntry.setTodaySaleCollectionAmountMode(formDTO.getTodaySaleCollectionAmountMode());
        salesEntry.setCreditCollectionMode(formDTO.getCreditCollectionMode());
        salesEntry.setCreditCollectionAmount(formDTO.getCreditCollectionAmount());
        salesEntry.setCreditBalance(formDTO.getCreditBalance());
        salesEntry.setEfdNumber(formDTO.getEfdNumber());
        salesEntry.setEfdPic(formDTO.getEfdPic());
        salesEntry.setReceiptNumber(formDTO.getReceiptNumber());
        salesEntry.setUserId(formDTO.getUserId());
        salesEntry.setCustomerId(formDTO.getCustomerId());
        salesEntry.setTransId(formDTO.getTransId());

        return salesEntry;
    }

//    public Customer findCustomerByEmail(final String email) {
//        return customerRepository.findByEmail(email);
//    }
}

