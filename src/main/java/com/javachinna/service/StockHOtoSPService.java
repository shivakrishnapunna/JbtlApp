package com.javachinna.service;

import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.StockHOtoSP;
import com.javachinna.repo.StockHOtoSPRepository;
import com.javachinna.repo.UserRepository;
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
public class StockHOtoSPService {

    @Autowired
    private StockHOtoSPRepository stockHOtoSPRepository;

    @Transactional(value = "transactionManager")
    public StockHOtoSP AddNewStock(StockHOtoSP addStock) throws UserAlreadyExistAuthenticationException {
//        System.out.println("*****************" + stockHOtoSPRepository.findByTinNumber(signUpRequest.getTinNumber()));

        StockHOtoSP stock = buildStock(addStock);
        Date now = Calendar.getInstance().getTime();
        stock.setCreatedDate(now);
        stock.setModifiedDate(now);

        StockHOtoSP save = stockHOtoSPRepository.save(stock);

        stockHOtoSPRepository.flush();
        return save;
    }

    private StockHOtoSP buildStock(final StockHOtoSP formDTO) {

        StockHOtoSP stockHOtoSP = new StockHOtoSP();
        stockHOtoSP.setQuantity(formDTO.getQuantity());
        stockHOtoSP.setHoPrice(formDTO.getHoPrice());
        stockHOtoSP.setStockValue(formDTO.getStockValue());
        stockHOtoSP.setDeliveryNoteNumber(formDTO.getDeliveryNoteNumber());

        stockHOtoSP.setDestinationPlace(formDTO.getDestinationPlace());
        stockHOtoSP.setDeliveryVehicalNumber(formDTO.getDeliveryVehicalNumber());
        stockHOtoSP.setSalesPersonID(formDTO.getSalesPersonID());
        stockHOtoSP.setHoToSalesCash(formDTO.getHoToSalesCash());
        stockHOtoSP.setUserId(formDTO.getUserId());

        return stockHOtoSP;
    }

//    public Customer findCustomerByEmail(final String email) {
//        return customerRepository.findByEmail(email);
//    }
}
