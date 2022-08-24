/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.service;

import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.Product;
import com.javachinna.repo.ProductRepository;
import com.nimbusds.jose.shaded.json.JSONObject;
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
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(value = "transactionManager")
    public JSONObject AddProduct(Product productEntry) throws UserAlreadyExistAuthenticationException {
        JSONObject jsonString = new JSONObject();
        if (!productRepository.existsByProduct(productEntry.getProductName())) {
            Product buildProduct = buildProduct(productEntry);
            Product save = productRepository.save(buildProduct);
            productRepository.flush();

            jsonString.put("Status", 200);
            jsonString.put("body", save);
        } else {
            jsonString.put("Status", 409);
            jsonString.put("body", "Product Name already exists");
        }

        return jsonString;
    }

    private Product buildProduct(final Product formDTO) {

        Product product = new Product();
        product.setProductName(formDTO.getProductName());
        product.setHoPrice(formDTO.getHoPrice());
        product.setRemarks(formDTO.getRemarks());

        return product;
    }

    public JSONObject GetProduct(long productId) {
        JSONObject jsonString = new JSONObject();
        try {
            Optional<Product> findById = productRepository.findById(productId);
            jsonString.put("Status", 200);
            jsonString.put("body", findById);
            return jsonString;
        } catch (UserAlreadyExistAuthenticationException e) {
            System.err.println("Exception Ocurred" + e);
            jsonString.put("Status", 402);
            jsonString.put("body", e);
            return jsonString;

        }

    }

    public JSONObject GetAllProducts() {
        JSONObject jsonString = new JSONObject();
        try {
            List<Product> findAll = productRepository.findAll();
            jsonString.put("Status", 200);
            jsonString.put("body", findAll);
            return jsonString;
        } catch (UserAlreadyExistAuthenticationException e) {
            System.err.println("Exception Ocurred" + e);
            jsonString.put("Status", 402);
            jsonString.put("body", e);
            return jsonString;

        }

    }

    public JSONObject updateProduct(long id, Product product) {
        JSONObject jsonString = new JSONObject();
        try {
            if (productRepository.existsById(id)) {
                product.setId(id);
                Product save = productRepository.save(product);
                jsonString.put("Status", 200);
                jsonString.put("body", save);
                return jsonString;
            } else {
                Product save = productRepository.save(product);
                jsonString.put("Status", 404);
                jsonString.put("body", "product not exists to update");
                return jsonString;
            }

        } catch (UserAlreadyExistAuthenticationException e) {
            System.err.println("Exception Ocurred" + e);
            jsonString.put("Status", 402);
            jsonString.put("body", e);
            return jsonString;

        }
    }

    public JSONObject deleteProduct(long id) {
        JSONObject jsonString = new JSONObject();
        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
                jsonString.put("Status", 200);
                jsonString.put("body", "deleted Product successfully");
                return jsonString;
            } else {
                jsonString.put("Status", 404);
                jsonString.put("body", "product not exists to delete");
                return jsonString;
            }

        } catch (UserAlreadyExistAuthenticationException e) {
            System.err.println("Exception Ocurred" + e);
            jsonString.put("Status", 402);
            jsonString.put("body", e);
            return jsonString;

        }

    }

    public Boolean checkProductName(String name) {
        boolean existsByProduct = productRepository.existsByProduct(name);
        return existsByProduct;

    }

}
