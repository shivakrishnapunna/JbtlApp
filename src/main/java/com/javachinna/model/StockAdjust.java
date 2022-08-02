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
public class StockAdjust implements Serializable {
//id	Stock Adjust Type	Product	Quantity	HO Price	Stock Value	Delivery Note Number	Delivery Vehical Number	Sales Person Name	Sales type	user id	created date

    private static final long serialVersionUID = 65981149772133526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedDate;

    @Column(name = "Stock_Adjust_Type")
    private String stockAdjustType;

    @Column(name = "Quantity")
    private String quantity;

    @Column(name = "HO_Price")
    private String hoPrice;

    @Column(name = "Stock_Value")
    private String stockValue;

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