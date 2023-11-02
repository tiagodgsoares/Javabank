package org.academiadecodigo.javabank.controller.json;

import org.academiadecodigo.javabank.command.AccountDto;
import org.academiadecodigo.javabank.command.CustomerDto;
import org.academiadecodigo.javabank.converters.AccountToAccountDto;
import org.academiadecodigo.javabank.converters.CustomerDtoToCustomer;
import org.academiadecodigo.javabank.converters.CustomerToCustomerDto;
import org.academiadecodigo.javabank.persistence.model.Customer;
import org.academiadecodigo.javabank.persistence.model.account.Account;
import org.academiadecodigo.javabank.services.AccountService;
import org.academiadecodigo.javabank.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class RestCustomerController {

    private CustomerService customerService;
    private AccountService accountService;
    private CustomerToCustomerDto customerToCustomerDto;
    private CustomerDtoToCustomer customerDtoToCustomer;
    private AccountToAccountDto accountToAccountDto;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setCustomerToCustomerDto(CustomerToCustomerDto customerToCustomerDto) {
        this.customerToCustomerDto = customerToCustomerDto;
    }

    @Autowired
    public void setCustomerDtoToCustomer(CustomerDtoToCustomer customerDtoToCustomer) {
        this.customerDtoToCustomer = customerDtoToCustomer;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setAccountToAccountDto(AccountToAccountDto accountToAccountDto) {
        this.accountToAccountDto = accountToAccountDto;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List> getCustomersList() {

        return new ResponseEntity<>(customerToCustomerDto.convert(customerService.list()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Integer id) {

        return new ResponseEntity<>(customerToCustomerDto.convert(customerService.get(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/account", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List> getAccountsList(@PathVariable Integer id) {

        return new ResponseEntity<>(accountToAccountDto.convert(customerService.get(id).getAccounts()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{cid}/account/{aid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> getAccount(@PathVariable Integer cid, @PathVariable Integer aid) {

        if(accountService.get(aid).getCustomer().getId() != cid) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(accountToAccountDto.convert(accountService.get(aid)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerDto customerDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(customerService.save(customerDtoToCustomer.convert(customerDto)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> editCustomer(@Valid @RequestBody CustomerDto customerDto, BindingResult bindingResult,@PathVariable Integer id) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        customerDto.setId(id);

        return new ResponseEntity<>(customerService.save(customerDtoToCustomer.convert(customerDto)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Integer id) {

        customerService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{cid}/account/{aid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> deleteAccount(@PathVariable Integer cid, @PathVariable Integer aid){

        if(accountService.get(aid).getCustomer().getId() != cid) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        accountService.delete(aid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
