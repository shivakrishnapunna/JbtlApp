/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javachinna.service;

import com.javachinna.exception.UserAlreadyExistAuthenticationException;

import com.javachinna.model.Customer;
import com.javachinna.repo.CustomerRepository;
import com.javachinna.repo.UserRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author punna31
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(value = "transactionManager")
    public Customer registerNewCoustomer(Customer signUpRequest) throws UserAlreadyExistAuthenticationException {
        System.out.println("*****************" + customerRepository.findByTinNumber(signUpRequest.getTinNumber()));
        if (customerRepository.findByTinNumber(signUpRequest.getTinNumber()) != null) {
            throw new UserAlreadyExistAuthenticationException("Customer with Customer id " + signUpRequest.getId() + " already exist");
        }
        Customer Customer = buildCustomer(signUpRequest);
        Date now = Calendar.getInstance().getTime();
        Customer.setCreatedDate(now);
        Customer.setModifiedDate(now);
        Customer.setOldBalance(0);
        Customer.setEnabled(true);
        Customer = customerRepository.save(Customer);
        customerRepository.flush();
        return Customer;
    }

    private Customer buildCustomer(final Customer formDTO) {

        Customer customer = new Customer();

        customer.setCustomerName(formDTO.getCustomerName());
        customer.setMobileNo(formDTO.getMobileNo());
        customer.setTinNumber(formDTO.getTinNumber());
        customer.setVrnNumber(formDTO.getVrnNumber());
        customer.setEmail(formDTO.getEmail());
        customer.setCompanyName(formDTO.getCompanyName());
        customer.setPlaceName(formDTO.getPlaceName());
        customer.setRegion(formDTO.getRegion());
        customer.setUser(formDTO.getUser());

        return customer;
    }

    public Customer findCustomerByEmail(final String email) {
        return customerRepository.findByEmail(email);
    }

}
