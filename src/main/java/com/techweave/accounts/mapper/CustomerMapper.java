package com.techweave.accounts.mapper;

import com.techweave.accounts.dto.CustomerDTO;
import com.techweave.accounts.entity.Customer;

public class CustomerMapper {
    public static CustomerDTO mapToACustomerDTO(Customer customer, CustomerDTO customerDTO){
    customerDTO.setEmail(customer.getEmail());
    customerDTO.setName(customer.getName());
    customerDTO.setMobileNumber(customer.getMobileNumber());
    return customerDTO;
    }
    public static Customer mapToCustomer(CustomerDTO customerDTO, Customer customer){
        customer.setEmail(customerDTO.getEmail());
        customer.setName(customerDTO.getName());
        customer.setMobileNumber(customerDTO.getMobileNumber());
        return customer;
    }
}
