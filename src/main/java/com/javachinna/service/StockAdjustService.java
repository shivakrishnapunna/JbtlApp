/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.service;

import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.StockAdjust;
import com.javachinna.repo.StockAdjustRepository;
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
public class StockAdjustService {

    @Autowired
    private StockAdjustRepository stockAdjustRepository;

    @Transactional(value = "transactionManager")
    public StockAdjust AddNewStock(StockAdjust addStock) throws UserAlreadyExistAuthenticationException {
//        System.out.println("*****************" + stockHOtoSPRepository.findByTinNumber(signUpRequest.getTinNumber()));

        StockAdjust stock = buildStock(addStock);
        Date now = Calendar.getInstance().getTime();
        stock.setCreatedDate(now);
        stock.setModifiedDate(now);

        StockAdjust save = stockAdjustRepository.save(stock);

        stockAdjustRepository.flush();
        return save;
    }

    private StockAdjust buildStock(final StockAdjust formDTO) {

        StockAdjust stockAdjust = new StockAdjust();
       
         stockAdjust.setStockAdjustType(formDTO.getStockAdjustType());
        stockAdjust.setQuantity(formDTO.getQuantity());
        stockAdjust.setHoPrice(formDTO.getHoPrice());
        stockAdjust.setStockValue(formDTO.getStockValue());
        stockAdjust.setDeliveryNoteNumber(formDTO.getDeliveryNoteNumber());

        stockAdjust.setDeliveryVehicalNumber(formDTO.getDeliveryVehicalNumber());
        stockAdjust.setSalesPersonID(formDTO.getSalesPersonID());
        stockAdjust.setSalesType(formDTO.getSalesType());
        stockAdjust.setUserId(formDTO.getUserId());

        return stockAdjust;
    }

//    public Customer findCustomerByEmail(final String email) {
//        return customerRepository.findByEmail(email);
//    }
}
