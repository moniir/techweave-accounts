package com.techweave.accounts.controller;

import com.techweave.accounts.constants.AccountsConstants;
import com.techweave.accounts.dto.CustomerDTO;
import com.techweave.accounts.dto.ResponseDTO;
import com.techweave.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Accounts in TechWeaves",
        description = "Create, Update, Fetch and Delete"
)
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated      //tells framework to perform validation all api inside accounts controller
public class AccountsController {

    private IAccountsService iAccountsService;

    @Operation(
            summary = "Create accounts REST Api",
            description = "REST API to create Customer & Accounts"
    )
    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO){
        iAccountsService.createAccount(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> getAccountsDetails(@RequestParam
                                                          @Pattern(regexp = "(^$|[0-9]{11})",
                                                                  message = "Mobile number should be 11 digits")
                                                          String mobileNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(iAccountsService.fetchAccoundDetail(mobileNumber));
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccount(@Valid @RequestBody CustomerDTO customerDTO){
        if(iAccountsService.updateAccount(customerDTO)){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccount( @RequestParam @Pattern(regexp = "(^$|[0-9]{11})",
            message = "Mobile number should be 11 digits") String mobileNumber){
        if(iAccountsService.deleteAccount(mobileNumber)){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
    }
}
