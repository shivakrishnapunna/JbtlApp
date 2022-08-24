/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.controller;

import com.javachinna.model.Product;
import com.javachinna.model.StockAdjustDao;
import com.javachinna.service.ProductService;
import com.nimbusds.jose.shaded.json.JSONObject;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author punna31
 */
@RestController
@RequestMapping("/api/user")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/product")
    @PreAuthorize("hasRole('USER')")
    public JSONObject AddProduct(@RequestBody Product product) {

        JSONObject AddProduct = productService.AddProduct(product);

        return AddProduct;
    }

    @PutMapping("/product/{productid}")
    @PreAuthorize("hasRole('USER')")
    public JSONObject UpdateProduct(@PathVariable("productid") long id, Product product) {

        JSONObject updateProduct = productService.updateProduct(id, product);

        System.out.println("updateProduct" + updateProduct);
        return updateProduct;

    }

    @GetMapping("/products")
    @PreAuthorize("hasRole('USER')")
    public JSONObject getAllProducts() {
        JSONObject GetAllProducts = productService.GetAllProducts();
        System.out.println("AddNewStock" + GetAllProducts);
        return GetAllProducts;

    }

    @GetMapping("/product/{productid}")
    @PreAuthorize("hasRole('USER')")
    public JSONObject getProduct(@PathVariable("productid") long id) {

        JSONObject updateProduct = productService.GetProduct(id);

        System.out.println("updateProduct" + updateProduct);
        return updateProduct;

    }

    @DeleteMapping("/product/{productid}")
    @PreAuthorize("hasRole('USER')")
    public JSONObject DelProduct(@PathVariable("productid") long id) {

        JSONObject deleteProduct = productService.deleteProduct(id);

        System.out.println("deleteProduct" + deleteProduct);
        return deleteProduct;

    }

}
