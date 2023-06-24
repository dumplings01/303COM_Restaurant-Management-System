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
    @Column(name="supplierId", nullable = false, unique = true)
    private UUID supplierId;

    @Column(name="supplierName", length=50, nullable = false)
    private String supplierName;

    @Column(name="stockName", length = 50, nullable = false)
    private String stockName;

    @Column(name = "stockType", length = 50, nullable = false)
    private String stockType;

    @Column(name="stockQuantity", nullable = true)
    private Integer stockQuantity;

    @Column(name = "stockWeight", nullable = true)
    private Double stockWeight;

    @Column(name = "unitOfMeasurement", length = 50, nullable = true)
    private String unitOfMeasurement;

    @Column(name="estimatedDeliveryDate", nullable = false)
    private Date estimatedDeliveryDate;

    @Column(name="dateDelivered", nullable = true)
    private Date dateDelivered;

    @Column(name="stockCondition", length=50, nullable = true)
    private String stockCondition;

    @Column(name="deliveryFeedback", nullable = true)
    private String deliveryFeedback;

    @Column(name="costOfStock", nullable = false)
    private Double costOfStock;

    @Column(name="status", length = 20, nullable = false)
    private String status;

    @Column(name="createdAt", nullable = false)
    private Date createdAt;

    @Column(name="updatedAt", nullable = true)
    private Date updatedAt;

    public Supplier(){

    }

    public Supplier(UUID supplierId, String supplierName, String stockName, String stockType,
                    Integer stockQuantity, Double stockWeight, String unitOfMeasurement,
                    Date estimatedDeliveryDate, Date dateDelivered, String stockCondition,
                    String deliveryFeedback, Double costOfStock, String status, Date createdAt,
                    Date updatedAt){
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.stockName = stockName;
        this.stockType = stockType;
        this.stockQuantity = stockQuantity;
        this.stockWeight = stockWeight;
        this.unitOfMeasurement = unitOfMeasurement;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.dateDelivered = dateDelivered;
        this.stockCondition = stockCondition;
        this.deliveryFeedback = deliveryFeedback;
        this.costOfStock = costOfStock;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    private void onCreate() {
        supplierId = UUID.randomUUID();
        status = "Order Placed";
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

    public Date getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public Date getDateDelivered() {
        return dateDelivered;
    }

    public void setDateDelivered(Date dateDelivered) {
        this.dateDelivered = dateDelivered;
    }

    public String getStockCondition() {
        return stockCondition;
    }

    public void setStockCondition(String stockCondition) {
        this.stockCondition = stockCondition;
    }

    public String getDeliveryFeedback() {
        return deliveryFeedback;
    }

    public void setDeliveryFeedback(String deliveryFeedback) {
        this.deliveryFeedback = deliveryFeedback;
    }

    public Double getCostOfStock() {
        return costOfStock;
    }

    public void setCostOfStock(Double costOfStock) {
        this.costOfStock = costOfStock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
