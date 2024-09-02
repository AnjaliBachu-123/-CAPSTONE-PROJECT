package com.controller;

import com.entity.Customer;
import com.entity.Loan;
import com.entity.LoanApplication;
import com.exception.CustomerAlreadyExistsException;
import com.exception.ResourceNotFoundException;
import com.exception.LoanNotFoundException;
import com.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody @Valid Customer customer) throws CustomerAlreadyExistsException {
        Customer registeredCustomer = customerService.registerCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredCustomer);
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> login(@RequestParam String email, @RequestParam String password) throws ResourceNotFoundException {
        Customer customer = customerService.loginCustomer(email, password);
        return ResponseEntity.ok(customer);
    }

//    @GetMapping("/loans")
//    public ResponseEntity<Loan[]> viewLoanOptions(@RequestParam String loanType) throws ResourceNotFoundException {
//        Loan[] loans = customerService.viewLoanOptions(loanType);
//        return ResponseEntity.ok(loans);
//    }
    
    @GetMapping("/loans")
    public ResponseEntity<List<Loan>> viewLoanOptions(@RequestParam String loanType) throws ResourceNotFoundException {
        List<Loan> loans = customerService.viewLoanOptions(loanType);
        return ResponseEntity.ok(loans);
    }

    @PostMapping("/apply")
    public ResponseEntity<LoanApplication> applyForLoan(@RequestParam Long customerId, @RequestParam Long loanId) throws ResourceNotFoundException, LoanNotFoundException {
        LoanApplication loanApplication = customerService.applyForLoan(customerId, loanId);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanApplication);
    }

    @GetMapping("/{customerId}/applied-loans")
    public ResponseEntity<List<LoanApplication>> viewAppliedLoans(@PathVariable Long customerId) throws ResourceNotFoundException {
        List<LoanApplication> loans = customerService.viewAppliedLoans(customerId);
        return ResponseEntity.ok(loans);
    }
}



