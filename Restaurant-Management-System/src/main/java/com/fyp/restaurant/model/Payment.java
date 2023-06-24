package com.fyp.restaurant.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="payment",
        uniqueConstraints={
                @UniqueConstraint(columnNames = "paymentId")
        })
public class Payment {

    @Id
    @Column(name="paymentId", nullable=false, unique=true)
    private UUID paymentId;

    @Column(name="cardNumber", length=16, nullable = false)
    private String cardNumber;

    @Column(name="cardholderName", length=50, nullable = false)
    private String cardholderName;

    @Column(name="expiryDate", length=7, nullable = false)
    private String expiryDate;

    @Column(name="securityCode", length=4, nullable = false)
    private String securityCode;

    @Column(name="billingAddress", nullable = false)
    private String billingAddress;


    public Payment() {

    }

    public Payment(UUID paymentId, String cardNumber, String cardholderName,
                   String expiryDate, String securityCode, String billingAddress,
                   UUID reservationId){
        this.paymentId=paymentId;
        this.cardNumber=cardNumber;
        this.cardholderName=cardholderName;
        this.expiryDate=expiryDate;
        this.securityCode=securityCode;
        this.billingAddress=billingAddress;
    }

    @PrePersist
    private void onCreate() {
        paymentId = UUID.randomUUID();
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

}
