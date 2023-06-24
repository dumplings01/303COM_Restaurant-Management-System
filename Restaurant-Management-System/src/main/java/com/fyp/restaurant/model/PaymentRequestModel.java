package com.fyp.restaurant.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class PaymentRequestModel {

    @NotNull
    @Size(max=16, message = "Card number must be lesser than 16 characters")
    private String cardNumber;

    @NotNull
    private String cardholderName;

    @NotNull
    @Size(max=7, message = "Expiry date must be lesser than 7 characters")
    private String expiryDate;

    @NotNull
    @Size(min=3, max=4)
    private String securityCode;

    @NotNull
    private String billingAddress;


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
