package com.fyp.restaurant.model;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class SupplierRequestModel {

    @NotNull
    @Size(max=50, message = "Supplier name must be lesser than 50 characters")
    private String supplierName;

    @NotNull
    @Size(max=50, message = "Stock name must be lesser than 50 characters")
    private String stockName;

    @NotNull
    private String stockType;

    private Integer stockQuantity;

    private Double stockWeight;

    private String unitOfMeasurement;

    @NotNull
    private Date estimatedDeliveryTime;

    @NotNull
    private Date timeDelivered;

    @NotNull
    @Size(max=10, message = "Name must be lesser than 50 characters")
    private String stockCondition;

    @NotNull
    private Double stockCost;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
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

    public Date getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(Date estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public Date getTimeDelivered() {
        return timeDelivered;
    }

    public void setTimeDelivered(Date timeDelivered) {
        this.timeDelivered = timeDelivered;
    }

    public String getStockCondition() {
        return stockCondition;
    }

    public void setStockCondition(String stockCondition) {
        this.stockCondition = stockCondition;
    }

    public Double getStockCost() {
        return stockCost;
    }

    public void setStockCost(Double stockCost) {
        this.stockCost = stockCost;
    }
}
