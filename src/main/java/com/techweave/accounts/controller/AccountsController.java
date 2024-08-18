package com.techweave.accounts.controller;

import com.techweave.accounts.constants.AccountsConstants;
import com.techweave.accounts.dto.CustomerDTO;
import com.techweave.accounts.dto.ResponseDTO;
import com.techweave.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AccountsController {

    private IAccountsService iAccountsService;
    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody CustomerDTO customerDTO){
        iAccountsService.createAccount(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> getAccountsDetails(@RequestParam  String mobileNumber){
        return ResponseEntity.status(HttpStatus.OK)
                .body(iAccountsService.fetchAccoundDetail(mobileNumber));
    }
}
