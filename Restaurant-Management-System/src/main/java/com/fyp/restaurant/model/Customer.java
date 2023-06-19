package com.fyp.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.UUID;

@Entity
@Table(name="customer",
        uniqueConstraints={
                @UniqueConstraint(columnNames = "customerId")
        })
public class Customer {

    @Id
    @Column(name="customerId", length=256, nullable = false, unique=true)
    private UUID customerId;

    @Column(name="name", length=50, nullable=false)
    private String name;

    @Email
    @Column(name="email", length=256, nullable=false)
    private String email;

    @Column(name="password", length=256, nullable=false)
    private String password;

    @Column(name="contactNumber", length=11, nullable=false)
    private String contactNumber;

    @Column(name="loyaltyPoints", length=256)
    private Integer loyaltyPoints;

    public Customer(){

    }

    public Customer(UUID customerId, String name, String email, String password,
                    String contactNumber, Integer loyaltyPoints) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.loyaltyPoints = loyaltyPoints;
    }

    @PrePersist
    private void onCreate() {
        customerId = UUID.randomUUID();
        loyaltyPoints = 0;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
