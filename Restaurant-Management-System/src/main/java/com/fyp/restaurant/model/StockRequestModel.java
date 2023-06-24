package com.fyp.restaurant.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class StockRequestModel {

    @NotNull
    @Size(max=40, message = "Name must be lesser than 40 characters")
    private String name;

    @NotNull
    private String stockType;

    private Integer stockQuantity;

    private Double stockWeight;

    private String unitOfMeasurement;

    @NotNull
    private Integer lowStockAlertAt;

    private Date updatedAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Double getStockWeight() {
        return stockWeight;
    }

    public void setStockWeight(Double stockWeight) {
        this.stockWeight = stockWeight;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Integer getLowStockAlertAt() {
        return lowStockAlertAt;
    }

    public void setLowStockAlertAt(Integer lowStockAlertAt) {
        this.lowStockAlertAt = lowStockAlertAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
