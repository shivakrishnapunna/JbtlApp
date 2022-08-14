/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.service;

import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.SalesEntry;
import com.javachinna.repo.SalesEntryRepository;
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
public class SalesEntryService {
    
    @Autowired
    private SalesEntryRepository salesEntryRepository;
    
    @Transactional(value = "transactionManager")
    public SalesEntry AddNewSale(SalesEntry addStock) throws UserAlreadyExistAuthenticationException {
//        System.out.println("*****************" + stockHOtoSPRepository.findByTinNumber(signUpRequest.getTinNumber()));

        SalesEntry stock = buildStock(addStock);
        Date now = Calendar.getInstance().getTime();
        stock.setCreatedDate(now);
        stock.setModifiedDate(now);
        
        SalesEntry save = salesEntryRepository.save(stock);
        
        salesEntryRepository.flush();
        return save;
    }
    
    private SalesEntry buildStock(final SalesEntry formDTO) {
        
        SalesEntry salesEntry = new SalesEntry();
        salesEntry.setProduct(formDTO.getProduct());
        salesEntry.setQuantity(formDTO.getQuantity());
        salesEntry.setHoPrice(formDTO.getHoPrice());
        salesEntry.setSellingPrice(formDTO.getSellingPrice());
        salesEntry.setTotalAmount(formDTO.getTotalAmount());
        salesEntry.setTransId(formDTO.getTransId());
        salesEntry.setUserId(formDTO.getUserId());
        salesEntry.setCustomerId(formDTO.getCustomerId());
        
        return salesEntry;
    }

}
