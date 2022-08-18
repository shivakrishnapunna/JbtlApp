/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author punna31
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class StockAdjustDao implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 65981149772133526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedDate;

    @Column(name = "Products")
    private String products;

    @Column(name = "Stock_Adjust_Type")
    private String stockAdjustType;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "HO_Price")
    private float hoPrice;

    @Column(name = "Stock_Value")
    private float stockValue;

    @Column(name = "Delivery_Note_Number")
    private String deliveryNoteNumber;

    @Column(name = "Delivery_Vehical_Number")
    private String deliveryVehicalNumber;

    @Column(name = "Sales_Person_ID")
    private String salesPersonID;

    @Column(name = "Sales_type")
    private String salesType;

    @Column(name = "USER_ID")
    private String userId;

}
