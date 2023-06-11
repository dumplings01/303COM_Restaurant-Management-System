package com.fyp.restaurant.model;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

public class ReservationRequestModel {

    @NotNull
    private UUID customerId;

    @NotNull
    @Size(max=50, message = "Name must be lesser than 50 characters")
    private String customerName;

    @NotNull
    @Pattern(regexp="(01\\d-\\d{7}$)",
            message = "Contact number format invalid!")
    private String customerContact;

    @NotNull
    private Date reservationDate;

    @NotNull
    @Size(max = 20, message = "Number of people must be lesser than 20")
    private Integer numberOfPeople;

    @Size(max=256, message = "Remarks must be lesser than 256 characters")
    private String customerRemarks;

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

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
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
}
