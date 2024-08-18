package com.techweave.accounts.service;

import com.techweave.accounts.dto.CustomerDTO;

public interface IAccountsService {
    void createAccount(CustomerDTO customerDTO);
    CustomerDTO fetchAccoundDetail(String mobileNumber);
}
