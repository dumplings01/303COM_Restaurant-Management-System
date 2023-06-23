package com.fyp.restaurant.model;

import javax.validation.constraints.NotNull;

public class LoyaltyProgramRequestModel {

    @NotNull
    private Integer loyaltyPoints;

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
