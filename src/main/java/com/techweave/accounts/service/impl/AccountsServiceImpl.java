package com.techweave.accounts.service.impl;

import com.techweave.accounts.constants.AccountsConstants;
import com.techweave.accounts.dto.CustomerDTO;
import com.techweave.accounts.entity.Accounts;
import com.techweave.accounts.entity.Customer;
import com.techweave.accounts.exception.CustomerAlreadyExistException;
import com.techweave.accounts.mapper.CustomerMapper;
import com.techweave.accounts.repository.AccountsRepository;
import com.techweave.accounts.repository.CustomerRepository;
import com.techweave.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
    AccountsRepository accountsRepository;
    CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistException("Customer with mobile number already exist");
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Monir");
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountNumber(1000000000L + new Random().nextInt(900000000));
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Monir");
        return newAccount;
    }
}
