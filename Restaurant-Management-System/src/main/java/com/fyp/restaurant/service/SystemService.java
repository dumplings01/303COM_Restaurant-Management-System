package com.fyp.restaurant.service;

import com.fyp.restaurant.model.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SystemService implements SystemServiceImpl {

    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    public Admin createStaffAccount (AdminRequestModel adminDetails) {
        Admin value = new Admin();

        value.setName(adminDetails.getName());
        value.setEmail(adminDetails.getEmail());
        value.setPassword(bcrypt.encode(adminDetails.getPassword()));
        value.setRoles(adminDetails.getRoles());

        return value;
    }

    public Customer createCustomerAccount (CustomerRequestModel customerDetails) {
        Customer value = new Customer();

        value.setName(customerDetails.getName());
        value.setEmail(customerDetails.getEmail());
        value.setPassword(bcrypt.encode(customerDetails.getPassword()));
        value.setContactNumber(customerDetails.getContactNumber());

        return value;
    }

    public Reservation createReservation (ReservationRequestModel reservationDetails) {
        Reservation value = new Reservation();

        value.setCustomerId(reservationDetails.getCustomerId());
        value.setCustomerName(reservationDetails.getCustomerName());
        value.setCustomerContact(reservationDetails.getCustomerContact());
        value.setReservationDate(reservationDetails.getReservationDate());
        value.setNumberOfPeople(reservationDetails.getNumberOfPeople());
        value.setCustomerRemarks(reservationDetails.getCustomerRemarks());

        return value;
    }

    public Stock createStock(StockRequestModel stockDetails) {
        Stock value = new Stock();

        value.setName(stockDetails.getName());
        value.setStockType(stockDetails.getStockType());
        value.setStockQuantity(stockDetails.getStockQuantity());
        value.setStockWeight(stockDetails.getStockWeight());
        value.setUnitOfMeasurement(stockDetails.getUnitOfMeasurement());
        value.setUpdatedAt(stockDetails.getUpdatedAt());

        return value;
    }

    public Supplier createSupplierHistory (SupplierRequestModel supplierDetails) {
        Supplier value = new Supplier();

        value.setSupplierName(supplierDetails.getSupplierName());
        value.setStockName(supplierDetails.getStockName());
        value.setStockType(supplierDetails.getStockType());
        value.setStockQuantity(supplierDetails.getStockQuantity());
        value.setStockWeight(supplierDetails.getStockWeight());
        value.setUnitOfMeasurement(supplierDetails.getUnitOfMeasurement());
        value.setEstimatedDeliveryTime(supplierDetails.getEstimatedDeliveryTime());
        value.setTimeDelivered(supplierDetails.getTimeDelivered());
        value.setStockCondition(supplierDetails.getStockCondition());
        value.setStockCost(supplierDetails.getStockCost());

        return value;
    }

}
