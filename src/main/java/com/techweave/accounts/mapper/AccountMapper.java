package com.techweave.accounts.mapper;

import com.techweave.accounts.dto.AccountsDTO;
import com.techweave.accounts.entity.Accounts;

public class AccountMapper {
    public static AccountsDTO mapToAccountDTO(Accounts accounts, AccountsDTO accountsDTO){
    accountsDTO.setAccountNumber(accounts.getAccountNumber());
    accountsDTO.setAccountType(accounts.getAccountType());
    accountsDTO.setBranchAddress(accounts.getBranchAddress());
    return accountsDTO;
    }
    public static Accounts mapToAccount(AccountsDTO accountsDTO, Accounts accounts){
        accounts.setAccountNumber(accountsDTO.getAccountNumber());
        accounts.setAccountType(accountsDTO.getAccountType());
        accounts.setBranchAddress(accountsDTO.getBranchAddress());
        return accounts;
    }
}
