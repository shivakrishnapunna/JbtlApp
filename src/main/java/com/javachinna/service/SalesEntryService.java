/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.service;

import com.javachinna.dto.ApiResponse;
import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.Customer;
import com.javachinna.model.SalesCollection;
import com.javachinna.model.SalesEntry;
import com.javachinna.model.SalesEntryProducts;
import com.javachinna.model.SalesPersonStock;
import com.javachinna.model.StockAdjust;
import com.javachinna.repo.SalesEntryRepository;
import com.javachinna.repo.SalesPersonStockRepository;
import com.javachinna.repo.StockAdjustRepository;
import com.nimbusds.jose.shaded.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    SalesCollectionService salesCollectionService;

    @Autowired
    CustomerService customerService;

    @Autowired
    private StockAdjustRepository stockAdjustRepository;

    //    
    @Autowired
    SalesPersonStockService salesPersonStockService;

    @Autowired
    private SalesPersonStockRepository salesPersonStockRepository;

    @Transactional(value = "transactionManager")
    public JSONObject SaleEntry(SalesEntryProducts salesEntry, int customerid, String id, UUID uuid) throws UserAlreadyExistAuthenticationException {
//        System.out.println("*****************" + stockHOtoSPRepository.findByTinNumber(signUpRequest.getTinNumber()));
        JSONObject SPStockt = SPStockt(id, salesEntry);
        Object get = SPStockt.get("status");
        if (get.equals(202)) {
            List<SalesEntry> products = salesEntry.getProducts();
            if (!products.isEmpty()) {
                products.forEach((n) -> AddSaleProduct(n, customerid, id, uuid));

            }

            AddTodaySaleCollection(salesEntry, customerid, id, uuid);
            UpdateCustomerBalnce(salesEntry, customerid);

        }

        return SPStockt;
    }

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

    public ResponseEntity<?> AddSaleProduct(SalesEntry salesEntry, int customerid, String id, UUID uuid) {
        try {
            salesEntry.setUserId(id);
            salesEntry.setCustomerId(customerid);
            salesEntry.setTransId(uuid.toString());
            SalesEntry AddNewSale = AddNewSale(salesEntry);

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

            oldBalance = (oldBalance - givenamount);
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

    //sales persion Stock checking and updating.
    public JSONObject SPStockt(final String userid, SalesEntryProducts addStock) throws UserAlreadyExistAuthenticationException {
        List<SalesEntry> products = addStock.getProducts();

        JSONObject jsonString = new JSONObject();
        jsonString.put("body", "there are no stock..!");
        if (!products.isEmpty()) {

            if (userid != null && salesPersonStockRepository.existsByUserId(userid)) {
                System.out.println("user Exists");
                products.forEach((ps) -> {

                    if (salesPersonStockRepository.existsByUserIdAndProduct(userid, ps.getProduct())) {

                        SalesPersonStock findByUserIdAndProduct = salesPersonStockRepository.findByUserIdAndProduct(userid, ps.getProduct());

                        if (findByUserIdAndProduct.getQuantity() >= ps.getQuantity()) {
                            SalesPersonStock se = new SalesPersonStock();
                            se.setId(findByUserIdAndProduct.getId());
                            se.setQuantity((findByUserIdAndProduct.getQuantity() - ps.getQuantity()));
                            se.setStockValue((findByUserIdAndProduct.getStockValue() - ps.getStockValue()));
                            se.setProduct(findByUserIdAndProduct.getProduct());
                            se.setUserId(findByUserIdAndProduct.getUserId());
                            se.setHoPrice(findByUserIdAndProduct.getHoPrice());
                            se.setCreatedDate(findByUserIdAndProduct.getCreatedDate());

                            SalesPersonStock AddNewSale = UpdateNewSale(se);
                            System.out.println("updated product to SP : " + AddNewSale);
                            jsonString.put("status", 202);
                            jsonString.put("body", "updated stock  from sp to HO..!");
                        } else {
                            jsonString.put("Insufficient status", 51);
                            jsonString.put("Insufficient" + ps.getProduct(), "Insufficient stock " + ps.getQuantity() + " to updated stock  from sp to HO..! ");
                        }

                    }

                });
            } else {
                jsonString.put("status", 401);
                jsonString.put("body", "invaild user to return stock..!");

            }
        }
        return jsonString;
    }

    public SalesPersonStock UpdateNewSale(SalesPersonStock stock) throws UserAlreadyExistAuthenticationException {
        Date now = Calendar.getInstance().getTime();
        stock.setModifiedDate(now);
        SalesPersonStock save = salesPersonStockRepository.save(stock);
        salesPersonStockRepository.flush();
        return save;
    }

    private SalesPersonStock buildStockss(final SalesPersonStock formDTO) {

        SalesPersonStock salesEntry = new SalesPersonStock();
        salesEntry.setProduct(formDTO.getProduct());
        salesEntry.setUserId(formDTO.getUserId());
        salesEntry.setQuantity(formDTO.getQuantity());
        salesEntry.setHoPrice(formDTO.getHoPrice());
        salesEntry.setStockValue(formDTO.getStockValue());

        return salesEntry;
    }

}
