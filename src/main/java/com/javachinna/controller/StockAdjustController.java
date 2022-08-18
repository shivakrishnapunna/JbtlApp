/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.controller;

import com.javachinna.dto.ApiResponse;
import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.StockAdjust;
import com.javachinna.model.StockAdjustDao;
import com.javachinna.service.StockAdjustService;
import com.nimbusds.jose.shaded.json.JSONObject;
import java.util.List;
import java.util.Optional;
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
public class StockAdjustController {

    @Autowired
    StockAdjustService stockAdjustService;

    @PostMapping("/{userid}/stockadjust")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> SPStockAdjust(@PathVariable("userid") String id, HttpServletRequest servletRequest, HttpServletResponse response,
            @RequestBody StockAdjust stockAdjust) {
        String res = "";
        try {
            stockAdjust.setUserId(id);
            JSONObject AddNewStock = stockAdjustService.AddNewStock(stockAdjust);
            res = AddNewStock.toJSONString();
            System.out.println("AddNewStock" + AddNewStock);

        } catch (UserAlreadyExistAuthenticationException e) {
            System.err.println("Exception Ocurred" + e);
            return new ResponseEntity<>(new ApiResponse(false, "stockAdjust not able to add to sp!"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(new ApiResponse(true, res));
    }

    @GetMapping("/{userid}/stockadjust")
    @PreAuthorize("hasRole('USER')")
    public Optional<StockAdjustDao> getSPStockAdjust(@PathVariable("userid") String id) {
        Optional<StockAdjustDao> stockAdjustDao = stockAdjustService.getStockAdjustDao(id);

        System.out.println("AddNewStock" + stockAdjustDao);
        return stockAdjustDao;

    }

    @GetMapping("/stockadjusts")
    @PreAuthorize("hasRole('USER')")
    public List<StockAdjustDao> getAllSPStockAdjust() {
        List<StockAdjustDao> stockAdjustDao = stockAdjustService.getList();
        System.out.println("AddNewStock" + stockAdjustDao);
        return stockAdjustDao;

    }

}
