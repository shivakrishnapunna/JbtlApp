/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@NoArgsConstructor
@Getter
@Setter
public class SalesEntryProducts {

    /**
     * Customer_id Customer_Name Mobile_no Id no Email_id Balance Village_add
     * Region User_id
     */
    private static final long serialVersionUID = 65981149772133526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Products")
    private List<SalesEntry> products;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "Today_Sale_Collection_Amount_Mode")
    private String todaySaleCollectionAmountMode;

    @Column(name = "Today_Sale_Collection_Amount")
    private float todaySaleCollectionAmount;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedDate;

    @Column(name = "Credit_Collection_Mode")
    private String creditCollectionMode;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "Stock_Value")
    private float stockValue;

    @Column(name = "Credit_Collection_Amount")
    private float creditCollectionAmount;

    @Column(name = "Credit_Balance")
    private float creditBalance;

    @Column(name = "Receipt_Number")
    private String receiptNumber;

    @Column(name = "Efd_Number")
    private String efdNumber;

    @Column(name = "Efd_Pic")
    private String efdPic;

}
