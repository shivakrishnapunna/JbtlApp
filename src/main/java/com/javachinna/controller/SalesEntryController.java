/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.controller;

import com.javachinna.dto.ApiResponse;
import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.Customer;
import com.javachinna.model.SalesCollection;
import com.javachinna.model.SalesEntry;
import com.javachinna.model.SalesEntryProducts;
import com.javachinna.service.CustomerService;
import com.javachinna.service.SalesCollectionService;

import com.javachinna.service.SalesEntryService;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.util.ArrayUtils;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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

    @PostMapping("/{userid}/saleentryt1/{customerid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> SalesEntry(@PathVariable("customerid") int customerid, @PathVariable("userid") String id, HttpServletRequest servletRequest, HttpServletResponse response,
            @RequestBody SalesEntry salesEntry) {

        try {
            salesEntry.setUserId(id);
            salesEntry.setCustomerId(customerid);
            SalesEntry AddNewSale = salesEntryService.AddNewSale(salesEntry);
            System.out.println("newcustomer" + AddNewSale);

        } catch (UserAlreadyExistAuthenticationException e) {
            System.err.println("Exception Ocurred" + e);
            return new ResponseEntity<>(new ApiResponse(false, "AddNewSale Address already in use!"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "AddNewSale registered successfully"));
    }

    //nested products saels entry
    @PostMapping("/{userid}/saleentry/{customerid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> SalesEntryTest(@PathVariable("customerid") int customerid, @PathVariable("userid") String id, HttpServletRequest servletRequest, HttpServletResponse response,
            @RequestBody SalesEntryProducts salesEntry) {

        UUID uuid = UUID.randomUUID(); //Generates random UUID  
//        String uuidd=uuid.toString();

        List<SalesEntry> products = salesEntry.getProducts();
        if (!products.isEmpty()) {
            products.forEach((n) -> AddSaleProduct(n, customerid, id, uuid));

        }

        AddTodaySaleCollection(salesEntry, customerid, id, uuid);
        UpdateCustomerBalnce(salesEntry, customerid);

        return ResponseEntity.ok().body(new ApiResponse(true, "AddNewSale registered successfully"));
    }

    public ResponseEntity<?> AddSaleProduct(SalesEntry salesEntry, int customerid, String id, UUID uuid) {
        try {
            salesEntry.setUserId(id);
            salesEntry.setCustomerId(customerid);
            salesEntry.setTransId(uuid.toString());
            SalesEntry AddNewSale = salesEntryService.AddNewSale(salesEntry);

            System.out.println("newcustomer" + AddNewSale);

        } catch (UserAlreadyExistAuthenticationException e) {
            System.err.println("Exception Ocurred" + e);
            return new ResponseEntity<>(new ApiResponse(false, "AddNewSale Address already in use!"), HttpStatus.BAD_REQUEST);
        }
//        System.out.println("Student Name is " + n.getProduct());
        return new ResponseEntity<>(new ApiResponse(false, "Added in sale !"), HttpStatus.ACCEPTED);

    }

    public ResponseEntity<?> AddTodaySaleCollection(SalesEntryProducts salesEntry, int customerid, String id, UUID uuid) {
        System.out.println("req cut : " + customerid);
        System.out.println(" req user : " + id);
        try {
            SalesCollection salesCollection = new SalesCollection();
            salesCollection.setUserId(id);
            salesCollection.setTransId(uuid.toString());
            salesCollection.setCustomerId(customerid);
            salesCollection.setTodaySaleCollectionAmount(salesEntry.getTodaySaleCollectionAmount());
            salesCollection.setTodaySaleCollectionAmountMode(salesEntry.getTodaySaleCollectionAmountMode());
            salesCollection.setCreditCollectionMode(salesEntry.getCreditCollectionMode());
            salesCollection.setCreditCollectionAmount(salesEntry.getCreditCollectionAmount());
            salesCollection.setCreditBalance(salesEntry.getCreditBalance());
            salesCollection.setEfdNumber(salesEntry.getEfdNumber());
            salesCollection.setEfdPic(salesEntry.getEfdPic());
            salesCollection.setReceiptNumber(salesEntry.getReceiptNumber());
            System.out.println("Collection : " + salesCollection.getCustomerId());
            System.out.println("user : " + salesCollection.getUserId());
            SalesCollection AddNewSaleCollection = salesCollectionService.AddNewSaleCollection(salesCollection);

            System.out.println("AddNewSaleCollection : " + AddNewSaleCollection);

        } catch (UserAlreadyExistAuthenticationException e) {
            System.err.println("Exception Ocurred" + e);
            return new ResponseEntity<>(new ApiResponse(false, "AddNewSale Address already in use!"), HttpStatus.BAD_REQUEST);
        }
//        System.out.println("Student Name is " + n.getProduct());
        return new ResponseEntity<>(new ApiResponse(false, "Added in sale !"), HttpStatus.ACCEPTED);

    }

    public ResponseEntity<?> UpdateCustomerBalnce(SalesEntryProducts salesEntry, int customerid) {

        final Float[] TotalSalesamount = new Float[1];
        TotalSalesamount[0] = 0f;
        float oldBalance = 0;
        Customer customer = null;

        try {

            //get customer by id
            Optional<Customer> customerByUserandCustomerID = customerService.getCustomerByUserandCustomerID(customerid);
            if (customerByUserandCustomerID.isPresent()) {
                customer = customerByUserandCustomerID.get();
                oldBalance = customer.getOldBalance();
            }
            //add all total amounts
            List<SalesEntry> products = salesEntry.getProducts();
            if (!products.isEmpty()) {
                products.forEach((n) -> {
                    float totalAmount = n.getTotalAmount();
                    TotalSalesamount[0] = TotalSalesamount[0] + totalAmount;
                });

            }
            System.out.println("total amount" + TotalSalesamount[0]);

            //add total old blance
            System.out.println("old amount" + oldBalance);

            oldBalance = oldBalance + TotalSalesamount[0];
            System.out.println("old amount + sale amount = " + oldBalance);

            //take amount from requestbody +add them and update to old balance
            float creditCollectionAmount = salesEntry.getTodaySaleCollectionAmount();
            float creditCollectionAmount1 = salesEntry.getCreditCollectionAmount();

            float givenamount = creditCollectionAmount + creditCollectionAmount1;
            System.out.println("total given amount = " + givenamount);

            oldBalance =(oldBalance - givenamount);
            System.out.println("new oldBalance = " + oldBalance);

            customer.setOldBalance(oldBalance);
            customerService.updateCustomerID(customer);

        } catch (UserAlreadyExistAuthenticationException e) {
            System.err.println("Exception Ocurred" + e);
            return new ResponseEntity<>(new ApiResponse(false, "oldbalnce update issue!"), HttpStatus.BAD_REQUEST);
        }
//        System.out.println("Student Name is " + n.getProduct());
        return new ResponseEntity<>(new ApiResponse(false, "Added in sale !"), HttpStatus.ACCEPTED);

    }

}
