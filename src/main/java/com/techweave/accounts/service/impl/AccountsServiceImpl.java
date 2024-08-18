package com.techweave.accounts.service.impl;

import com.techweave.accounts.constants.AccountsConstants;
import com.techweave.accounts.dto.AccountsDTO;
import com.techweave.accounts.dto.CustomerDTO;
import com.techweave.accounts.entity.Accounts;
import com.techweave.accounts.entity.Customer;
import com.techweave.accounts.exception.CustomerAlreadyExistException;
import com.techweave.accounts.exception.NotFoundException;
import com.techweave.accounts.mapper.AccountMapper;
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

    @Override
    public CustomerDTO fetchAccoundDetail(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new NotFoundException("No record found!"));
        CustomerDTO customerDTO = CustomerMapper.mapToACustomerDTO(customer, new CustomerDTO());
        customerDTO.setAccountsDto(AccountMapper.mapToAccountDTO(accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new NotFoundException("Account not found")), new AccountsDTO()));
        return customerDTO;
    }

    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;
        AccountsDTO accountsDTO = customerDTO.getAccountsDto();
        if (accountsDTO != null) {
            Accounts accounts = accountsRepository.findById(accountsDTO.getAccountNumber())
                    .orElseThrow(() -> new NotFoundException("Account information not found"));
            AccountMapper.mapToAccount(accountsDTO, accounts);
            accounts.setUpdatedAt(LocalDateTime.now());
            accounts.setUpdatedBy("Anonymous");
            accounts = accountsRepository.save(accounts);
            Customer customer = customerRepository.findById(accounts.getCustomerId())
                    .orElseThrow(() -> new NotFoundException("Customer not found"));
            CustomerMapper.mapToCustomer(customerDTO, customer);
            customer.setUpdatedAt(LocalDateTime.now());
            customer.setUpdatedBy("Anonymous");
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new NotFoundException("No record found!"));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.delete(customer);
        return true;
    }


}
