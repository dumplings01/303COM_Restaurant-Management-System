package com.fyp.restaurant.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="supplier",
        uniqueConstraints={
            @UniqueConstraint(columnNames = {"supplierId", "supplierName"})
        })
public class Supplier {

    @Id
    @Column(name="supplierId", length=256, nullable = false, unique = true)
    private UUID supplierId;

    @Column(name="supplierName", length=50, nullable = false)
    private String supplierName;

    @Column(name="stockName", length=256, nullable = false)
    private String stockName;

    @Column(name = "stockType", nullable = false)
    private String stockType;

    @Column(name="stockQuantity", nullable = true)
    private Integer stockQuantity;

    @Column(name = "stockWeight", nullable = true)
    private Double stockWeight;

    @Column(name = "unitOfMeasurement", nullable = true)
    private String unitOfMeasurement;

    @Column(name="estimatedDeliveryTime", nullable = false)
    private Date estimatedDeliveryTime;

    @Column(name="timeDelivered", nullable = false)
    private Date timeDelivered;

    @Column(name="stockCondition", length=10, nullable = false)
    private String stockCondition;

    @Column(name="stockCost", nullable = false)
    private Double stockCost;

    @Column(name="createdAt", nullable = false)
    private Date createdAt;

    @Column(name="updatedAt", nullable = true)
    private Date updatedAt;

    public Supplier(){

    }

    public Supplier(UUID supplierId, String supplierName, String stockName, String stockType,
                    Integer stockQuantity, Double stockWeight, String unitOfMeasurement,
                    Date estimatedDeliveryTime, Date timeDelivered, String stockCondition,
                    Double stockCost, Date createdAt, Date updatedAt){
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.stockName = stockName;
        this.stockType = stockType;
        this.stockQuantity = stockQuantity;
        this.stockWeight = stockWeight;
        this.unitOfMeasurement = unitOfMeasurement;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.timeDelivered = timeDelivered;
        this.stockCondition = stockCondition;
        this.stockCost = stockCost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    private void onCreate() {
        supplierId = UUID.randomUUID();
        createdAt = new Date();
    }

    public UUID getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(UUID supplierId) {
        this.supplierId = supplierId;
    }

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
