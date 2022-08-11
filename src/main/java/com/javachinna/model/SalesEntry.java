/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author punna31
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class SalesEntry implements Serializable {

    /**
     * Customer_id Customer_Name Mobile_no Id no Email_id Balance Village_add
     * Region User_id
     */
    private static final long serialVersionUID = 65981149772133526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;


    @Column(name = "CUSTOMER_ID")
    private int customerId;
    
    @Column(name = "Trans_ID")
    private String transId;
    
    @Column(name="Products")
    private String product;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "Ho_Price")
    private float hoPrice;

    @Column(name = "Selling_Price")
    private float sellingPrice;

    @Column(name = "Total_Amount")
    private float totalAmount;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedDate;


    @Column(name = "USER_ID")
    private String userId;

}
