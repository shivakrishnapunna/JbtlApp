package com.javachinna.service;

import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.SalesPersonStock;
import com.javachinna.model.StockAdjust;
import com.javachinna.model.StockAdjustDao;
import com.javachinna.model.StockHOtoSP;
import com.javachinna.model.StockHOtoSPDao;

import com.javachinna.repo.SalesPersonStockRepository;
import com.javachinna.repo.StockHOtoSPRepository;
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
public class StockHOtoSPService {

    @Autowired
    private StockHOtoSPRepository stockHOtoSPRepository;

    @Autowired
    private SalesPersonStockRepository salesPersonStockRepository;

    @Transactional(value = "transactionManager")
    public StockHOtoSPDao AddNewStock(StockHOtoSP addStock) throws UserAlreadyExistAuthenticationException {
//        System.out.println("*****************" + stockHOtoSPRepository.findByTinNumber(signUpRequest.getTinNumber()));
        SPStockt(addStock.getUserId(), addStock);

        StockHOtoSPDao stock = buildStock(addStock);

        Date now = Calendar.getInstance().getTime();
        stock.setCreatedDate(now);
        stock.setModifiedDate(now);

        StockHOtoSPDao save = stockHOtoSPRepository.save(stock);

        stockHOtoSPRepository.flush();
        return save;
    }

    private StockHOtoSPDao buildStock(final StockHOtoSP formDTO) {

        StockHOtoSPDao stockHOtoSP = new StockHOtoSPDao();
        stockHOtoSP.setQuantity(formDTO.getQuantity());
        stockHOtoSP.setHoPrice(formDTO.getHoPrice());
        stockHOtoSP.setStockValue(formDTO.getStockValue());
        stockHOtoSP.setDeliveryNoteNumber(formDTO.getDeliveryNoteNumber());
        stockHOtoSP.setProducts(formDTO.getProducts().toString());
        stockHOtoSP.setDestinationPlace(formDTO.getDestinationPlace());
        stockHOtoSP.setDeliveryVehicalNumber(formDTO.getDeliveryVehicalNumber());
        stockHOtoSP.setSalesPersonID(formDTO.getSalesPersonID());
        stockHOtoSP.setHoToSalesCash(formDTO.getHoToSalesCash());
        stockHOtoSP.setUserId(formDTO.getUserId());

        return stockHOtoSP;
    }

    public String SPStockt(final String userid, StockHOtoSP addStock) throws UserAlreadyExistAuthenticationException {
        List<SalesPersonStock> products = addStock.getProducts();
        if (userid != null && salesPersonStockRepository.existsByUserId(userid)) {
            System.out.println("user Exists");

            if (!products.isEmpty()) {
                products.forEach((n) -> {
                    n.setUserId(userid);
                    SalesPersonStock stock = buildStocks(n);
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
    public List<StockHOtoSPDao> getList() {
        List<StockHOtoSPDao> topics = new ArrayList();
        stockHOtoSPRepository.findAll().forEach(topics::add);
        return topics;
    }

    public Optional<StockHOtoSPDao> getStockAdjustDao(String id) {
        return stockHOtoSPRepository.findByUserId(id);
    }

//    public StockAdjustDao getByOrgIdAndStockAdjustDaoID(String orgId,String id) {
//          StockAdjustDao orgTen = stockAdjustRepository.findByOrgIdAndStockAdjustDaoID(orgId,id);
//          System.out.println("orgtent : "+ orgTen);
//        return orgTen ;
//    }
    public void deleteStockAdjustDao(String id) {
        stockHOtoSPRepository.deleteById(id);
    }
}
