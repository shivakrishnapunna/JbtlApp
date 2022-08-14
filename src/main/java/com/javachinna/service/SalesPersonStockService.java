/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.service;

import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.SalesPersonStock;
import com.javachinna.repo.SalesPersonStockRepository;
import com.nimbusds.jose.shaded.json.JSONObject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author punna31
 */
@Service
public class SalesPersonStockService {

    @Autowired
    private SalesPersonStockRepository salesPersonStockRepository;

    @Transactional(value = "transactionManager")
    public String SPStockt(final String userid, SalesPersonStock addStock) throws UserAlreadyExistAuthenticationException {
        if (userid != null && salesPersonStockRepository.existsByUserId(userid)) {
            System.out.println("user Exists");
            JSONObject jsonString = new JSONObject();
            SalesPersonStock AddNewSale = UpdateNewSale(addStock);
            jsonString.put("status", 202);
            jsonString.put("body", AddNewSale);
            return jsonString.toString();

        } else {

            System.out.println("user not Exists");
            JSONObject jsonString = new JSONObject();
            SalesPersonStock AddNewSale = AddNewSale(addStock);
            jsonString.put("status", 200);
            jsonString.put("body", AddNewSale);
            return jsonString.toString();

        }
    }

    public SalesPersonStock AddNewSale(SalesPersonStock addStock) throws UserAlreadyExistAuthenticationException {
//        System.out.println("*****************" + stockHOtoSPRepository.findByTinNumber(signUpRequest.getTinNumber()));

        SalesPersonStock stock = buildStock(addStock);
        Date now = Calendar.getInstance().getTime();
        stock.setCreatedDate(now);
        stock.setModifiedDate(now);
        SalesPersonStock save = salesPersonStockRepository.save(stock);
        salesPersonStockRepository.flush();
        return save;
    }

    public SalesPersonStock UpdateNewSale(SalesPersonStock addStock) throws UserAlreadyExistAuthenticationException {
//        System.out.println("*****************" + stockHOtoSPRepository.findByTinNumber(signUpRequest.getTinNumber()));

        SalesPersonStock stock = buildStock(addStock);
        Date now = Calendar.getInstance().getTime();
        stock.setCreatedDate(addStock.getCreatedDate());
        stock.setModifiedDate(now);
        SalesPersonStock save = salesPersonStockRepository.save(stock);
        salesPersonStockRepository.flush();
        return save;
    }

    private SalesPersonStock buildStock(final SalesPersonStock formDTO) {

        SalesPersonStock salesEntry = new SalesPersonStock();
        salesEntry.setUserId(formDTO.getUserId());
        salesEntry.setProduct(formDTO.getProduct());
        salesEntry.setQuantity(formDTO.getQuantity());
        salesEntry.setHoPrice(formDTO.getHoPrice());
        salesEntry.setStockValue(formDTO.getStockValue());

        return salesEntry;
    }

    public List<SalesPersonStock> findSalesPersonByid(final String userid) {
        return salesPersonStockRepository.findByUserId(userid);
    }

}
