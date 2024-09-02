package com.entity;

//import java.sql.Date;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class Customer {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int customerId;
//
//    private String customerName;
//    private String email;
//    private String phoneNumber;
//    private String password;
//    private Date dateOfBirth;
//	public int getCustomerId() {
//		return customerId;
//	}
//	public void setCustomerId(int customerId) {
//		this.customerId = customerId;
//	}
//	public String getCustomerName() {
//		return customerName;
//	}
//	public void setCustomerName(String customerName) {
//		this.customerName = customerName;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public String getPhoneNumber() {
//		return phoneNumber;
//	}
//	public void setPhoneNumber(String phoneNumber) {
//		this.phoneNumber = phoneNumber;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public Date getDateOfBirth() {
//		return dateOfBirth;
//	}
//	public void setDateOfBirth(Date dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}
//    
//    // Getters and Setters
//}

import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @NotBlank(message = "Customer name cannot be blank")
    @Size(max = 100, message = "Customer name must be less than 100 characters")
    private String customerName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    private String phoneNumber;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be a past date")
    private Date dateOfBirth;

    // Getters and Setters

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

