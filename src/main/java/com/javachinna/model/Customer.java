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
 * The persistent class for the user database table.
 *
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Customer implements Serializable {

    /**
     * Customer_id Customer_Name Mobile_no Id no Email_id Balance Village_add
     * Region User_id
     */
    private static final long serialVersionUID = 65981149772133526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private int id;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "MOBILE_NO")
    private String mobileNo;

    @Column(name = "TIN_NUMBER")
    private String tinNumber;
    
    @Column(name = "VRN_NUMBER")
    private String vrnNumber;

    private String email;
    
    @Column(name="COMPANY_NAME")
    private String companyName;

    @Column(name = "ENABLED", columnDefinition = "BIT", length = 1)
    private boolean enabled;

    @Column(name = "PLACE_NAME")
    private String placeName;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedDate;
    
    @Column(name = "REGION")
    private String region;
    
    @Column(name = "USER")
    private String user;
    
    @Column(name="OLD_BALANCE")
    private float oldBalance;

}
