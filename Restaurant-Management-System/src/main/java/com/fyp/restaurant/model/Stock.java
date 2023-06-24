package com.fyp.restaurant.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="stock",
        uniqueConstraints={
                @UniqueConstraint(columnNames="stockId"),
                @UniqueConstraint(columnNames = "name")
        })

public class Stock {

        @Id
        @Column(name = "stockId", nullable = false, unique = true)
        private UUID stockId;

        @Column(name = "name", length = 50, nullable = false)
        private String name;

        @Column(name = "stockType", length = 50, nullable = false)
        private String stockType;

        @Column(name = "stockQuantity", nullable = true)
        private Integer stockQuantity;

        @Column(name = "stockWeight", nullable = true)
        private Double stockWeight;

        @Column(name = "unitOfMeasurement", length = 50, nullable = true)
        private String unitOfMeasurement;

        @Column(name = "lowStockAlertAt", nullable = false)
        private Integer lowStockAlertAt;

        @Column(name = "updatedAt", nullable = false)
        private Date updatedAt;

        public Stock(){

        }

        public Stock(UUID stockId, String name, String stockType,
                     Integer stockQuantity, Double stockWeight,
                     String unitOfMeasurement, Integer lowStockAlertAt, Date updatedAt) {
                this.stockId = stockId;
                this.name = name;
                this.stockType = stockType;
                this.stockQuantity = stockQuantity;
                this.stockWeight = stockWeight;
                this.unitOfMeasurement = unitOfMeasurement;
                this.lowStockAlertAt = lowStockAlertAt;
                this.updatedAt = updatedAt;
        }

        @PrePersist
        private void onCreate() {
                stockId = UUID.randomUUID();
                updatedAt = new Date();
        }

        public UUID getStockId() {
                return stockId;
        }

        public void setStockId(UUID stockId) {
                this.stockId = stockId;
        }

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
