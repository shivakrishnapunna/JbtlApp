/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.service;

import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.SalesEntry;

import com.javachinna.model.SalesPersonStock;
import com.javachinna.model.StockAdjust;
import com.javachinna.model.StockAdjustDao;

import com.javachinna.repo.SalesPersonStockRepository;
import com.javachinna.repo.StockAdjustRepository;
import com.nimbusds.jose.shaded.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    //    
    @Autowired
    SalesPersonStockService salesPersonStockService;

    @Autowired
    private SalesPersonStockRepository salesPersonStockRepository;

    @Transactional(value = "transactionManager")
    public StockAdjustDao AddNewStock(StockAdjust addStock) throws UserAlreadyExistAuthenticationException {
//        System.out.println("*****************" + stockHOtoSPRepository.findByTinNumber(signUpRequest.getTinNumber()));
        SPStockt(addStock.getUserId(), addStock);
        StockAdjustDao buildStock = buildStock(addStock);
        Date now = Calendar.getInstance().getTime();
        buildStock.setCreatedDate(now);
        buildStock.setModifiedDate(now);

        StockAdjustDao save = stockAdjustRepository.save(buildStock);

        stockAdjustRepository.flush();
        return save;
    }

    private StockAdjustDao buildStock(final StockAdjust formDTO) {

        StockAdjustDao stockAdjust = new StockAdjustDao();

        stockAdjust.setStockAdjustType(formDTO.getStockAdjustType());
        stockAdjust.setQuantity(formDTO.getQuantity());
        stockAdjust.setHoPrice(formDTO.getHoPrice());
        stockAdjust.setStockValue(formDTO.getStockValue());
        stockAdjust.setDeliveryNoteNumber(formDTO.getDeliveryNoteNumber());
        stockAdjust.setProducts(formDTO.getProducts().toString());
        stockAdjust.setDeliveryVehicalNumber(formDTO.getDeliveryVehicalNumber());
        stockAdjust.setSalesPersonID(formDTO.getSalesPersonID());
        stockAdjust.setSalesType(formDTO.getSalesType());
        stockAdjust.setUserId(formDTO.getUserId());

        return stockAdjust;
    }

    public String SPStockt(final String userid, StockAdjust addStock) throws UserAlreadyExistAuthenticationException {
        List<SalesPersonStock> products = addStock.getProducts();
        if (userid != null && salesPersonStockRepository.existsByUserId(userid)) {
            System.out.println("user Exists");

            if (!products.isEmpty()) {
                products.forEach((n) -> {
                    n.setUserId(userid);
                    SalesPersonStock stock = buildStocks(n);
                    JSONObject jsonString = new JSONObject();
                    SalesPersonStock AddNewSale = UpdateNewSale(stock);
                });

            }

            JSONObject jsonString = new JSONObject();
            jsonString.put("status", 202);
            jsonString.put("body", "updated stock..!");
            return jsonString.toString();

        } else {
            if (!products.isEmpty()) {
                products.forEach((n) -> {
                    n.setUserId(userid);
                    SalesPersonStock stock = buildStocks(n);
                    JSONObject jsonString = new JSONObject();
                    SalesPersonStock AddNewSale = AddNewSale(stock);
                });

            }

            JSONObject jsonString = new JSONObject();
            jsonString.put("status", 202);
            jsonString.put("body", "inserted stock..!");
            return jsonString.toString();

        }
    }

    public SalesPersonStock AddNewSale(SalesPersonStock addStock) throws UserAlreadyExistAuthenticationException {
//        System.out.println("*****************" + stockHOtoSPRepository.findByTinNumber(signUpRequest.getTinNumber()));

        SalesPersonStock stock = buildStocks(addStock);
        Date now = Calendar.getInstance().getTime();
        stock.setCreatedDate(now);
        stock.setModifiedDate(now);
        SalesPersonStock save = salesPersonStockRepository.save(stock);
        salesPersonStockRepository.flush();
        return save;
    }

    public SalesPersonStock UpdateNewSale(SalesPersonStock stock) throws UserAlreadyExistAuthenticationException {
        Date now = Calendar.getInstance().getTime();
        stock.setCreatedDate(stock.getCreatedDate());
        stock.setModifiedDate(now);
        SalesPersonStock save = salesPersonStockRepository.save(stock);
        salesPersonStockRepository.flush();
        return save;
    }

    private SalesPersonStock buildStocks(final SalesPersonStock formDTO) {

        SalesPersonStock salesEntry = new SalesPersonStock();
        salesEntry.setProduct(formDTO.getProduct());
        salesEntry.setUserId(formDTO.getUserId());
        salesEntry.setQuantity(formDTO.getQuantity());
        salesEntry.setHoPrice(formDTO.getHoPrice());
        salesEntry.setStockValue(formDTO.getStockValue());

        return salesEntry;
    }

    
    
    //CRED oprations 
    
    public List<StockAdjustDao> getList() {
        List<StockAdjustDao> topics = new ArrayList();
        stockAdjustRepository.findAll().forEach(topics::add);
        return topics;
    }

    public Optional<StockAdjustDao> getStockAdjustDao(String id) {
        return stockAdjustRepository.findByUserId(id);
    }

//    public StockAdjustDao getByOrgIdAndStockAdjustDaoID(String orgId,String id) {
//          StockAdjustDao orgTen = stockAdjustRepository.findByOrgIdAndStockAdjustDaoID(orgId,id);
//          System.out.println("orgtent : "+ orgTen);
//        return orgTen ;
//    }
    public void deleteStockAdjustDao(String id) {
        stockAdjustRepository.deleteById(id);
    }
}
