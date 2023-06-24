package com.fyp.restaurant.model;


import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="reservation",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"reservationId",
                        "customerId"})
        })
public class Reservation {

    @Id
    @Column(name="reservationId", nullable = false, unique=true)
    private UUID reservationId;

    @Column(name="customerId", nullable=false)
    private UUID customerId;

    @Column(name="customerName", length=50, nullable=false)
    private String customerName;

    @Column(name="customerContact", length=11, nullable=false)
    private String customerContact;

    @Column(name="reservationDate", nullable=false)
    private String reservationDate;

    @Column(name="numberOfPeople", length=20, nullable=false)
    private Integer numberOfPeople;

    @Column(name="customerRemarks", nullable=true)
    private String customerRemarks;

    @Column(name="createdAt", nullable = true)
    private Date createdAt;

    @Column(name="status", length=20,nullable = true)
    private String status;

    @Column(name="paymentId", nullable = true)
    private UUID paymentId;

    public Reservation(){

    }

    public Reservation(UUID reservationId, UUID customerId, String customerName,
                       String customerContact, String reservationDate, Integer numberOfPeople,
                       String customerRemarks, Date createdAt, String status, UUID paymentId){
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.reservationDate = reservationDate;
        this.numberOfPeople = numberOfPeople;
        this.customerRemarks = customerRemarks;
        this.createdAt = createdAt;
        this.status = status;
        this.paymentId = paymentId;
    }

    @PrePersist
    private void onCreate() {
        reservationId = UUID.randomUUID();
        createdAt = new Date();
        status = "Deposit pending";
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getCustomerRemarks() {
        return customerRemarks;
    }

    public void setCustomerRemarks(String customerRemarks) {
        this.customerRemarks = customerRemarks;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }
}
