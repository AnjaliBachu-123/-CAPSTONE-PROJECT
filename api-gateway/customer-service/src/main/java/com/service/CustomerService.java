package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.entity.Customer;
import com.entity.Loan;
import com.entity.LoanApplication;
import com.exception.CustomerAlreadyExistsException;
import com.exception.LoanNotFoundException;
import com.exception.ResourceNotFoundException;
import com.repository.CustomerRepository;
import com.repository.LoanApplicationRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String LOAN_SERVICE_URL = "http://localhost:8081";

    public Customer registerCustomer(Customer customer) throws CustomerAlreadyExistsException {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new CustomerAlreadyExistsException("Customer with this email already exists.");
        }
        return customerRepository.save(customer);
    }

    public Customer loginCustomer(String email, String password) throws ResourceNotFoundException {
        Customer customer = customerRepository.findByEmailAndPassword(email, password);
        if (customer == null) {
            throw new ResourceNotFoundException("Invalid email or password.");
        }
        return customer;
    }

    public List<Loan> viewLoanOptions(String loanType) throws ResourceNotFoundException {
        String url = LOAN_SERVICE_URL + "/loans/type/" + loanType;

        ResponseEntity<List<Loan>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Loan>>() {}
        );

        List<Loan> loans = response.getBody();
        if (loans == null || loans.isEmpty()) {
            throw new ResourceNotFoundException("No loan options found for the given type.");
        }

        return loans;
    }

    public LoanApplication applyForLoan(Long customerId, Long loanId) throws ResourceNotFoundException, LoanNotFoundException {
        String url = LOAN_SERVICE_URL + "/loans/" + loanId;
        Loan loan = restTemplate.getForObject(url, Loan.class);
        if (loan == null) {
            throw new LoanNotFoundException("Loan with the given ID does not exist.");
        }

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setCustomerId(customerId);
        loanApplication.setLoanId(loanId);
        loanApplication.setStatus("APPLIED");
        return loanApplicationRepository.save(loanApplication);
    }

    public List<LoanApplication> viewAppliedLoans(Long customerId) throws ResourceNotFoundException {
        List<LoanApplication> loans = loanApplicationRepository.findByCustomerId(customerId);
        if (loans == null || loans.isEmpty()) {
            throw new ResourceNotFoundException("No applied loans found for the given customer.");
        }
        return loans;
    }
}
