package com.fyp.restaurant.service;

import com.fyp.restaurant.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SystemServiceTest {

    @Mock
    private SystemServiceImpl systemServiceImpl;
    AutoCloseable autoCloseable;
    AdminRequestModel adminRequestModel;
    CustomerRequestModel customerRequestModel;
    ReservationRequestModel reservationRequestModel;
    StockRequestModel stockRequestModel;
    SupplierRequestModel supplierRequestModel;
    ReservationSlotsRequestModel reservationSlotsRequestModel;
    PaymentRequestModel paymentRequestModel;

    @BeforeEach
    void setUp() {


        autoCloseable = MockitoAnnotations.openMocks(this);

        systemServiceImpl = new SystemService();

        adminRequestModel = new AdminRequestModel();
        customerRequestModel = new CustomerRequestModel();
        reservationRequestModel = new ReservationRequestModel();
        stockRequestModel = new StockRequestModel();
        supplierRequestModel = new SupplierRequestModel();
        reservationSlotsRequestModel = new ReservationSlotsRequestModel();
        paymentRequestModel = new PaymentRequestModel();

    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void createStaffAccount() {

        adminRequestModel.setName("John");
        adminRequestModel.setEmail("john@example.com");
        adminRequestModel.setPassword("password");
        List<String> roles = new ArrayList<>();
        roles.add("role_1");
        roles.add("role_2");
        adminRequestModel.setRoles(roles);

        Admin result = systemServiceImpl.createStaffAccount(adminRequestModel);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(adminRequestModel.getName());
        assertThat(result.getEmail()).isEqualTo(adminRequestModel.getEmail());
        assertThat(result.getPassword()).isNotNull();
        assertThat(result.getRoles()).containsExactlyElementsOf(adminRequestModel.getRoles());

    }

    @Test
    void createCustomerAccount() {

        customerRequestModel.setName("John");
        customerRequestModel.setEmail("john@example.com");
        customerRequestModel.setPassword("password");

        Customer result = systemServiceImpl.createCustomerAccount(customerRequestModel);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(customerRequestModel.getName());
        assertThat(result.getEmail()).isEqualTo(customerRequestModel.getEmail());
        assertThat(result.getPassword()).isNotNull();

    }

    @Test
    void createReservation() {

        reservationRequestModel.setCustomerName("John");
        reservationRequestModel.setCustomerContact("012-3355335");
        reservationRequestModel.setReservationDate("2023-08-23 10:00AM");
        reservationRequestModel.setNumberOfPeople(10);

        Reservation result = systemServiceImpl.createReservation(reservationRequestModel);

        assertThat(result).isNotNull();
        assertThat(result.getCustomerName()).isEqualTo(reservationRequestModel.getCustomerName());
        assertThat(result.getCustomerContact()).isEqualTo(reservationRequestModel.getCustomerContact());
        assertThat(result.getReservationDate()).isEqualTo(reservationRequestModel.getReservationDate());
        assertThat(result.getNumberOfPeople()).isEqualTo(reservationRequestModel.getNumberOfPeople());
    }

    @Test
    void createStock() {

        stockRequestModel.setName("Rice");
        stockRequestModel.setStockType("Rice, Grain Type");
        stockRequestModel.setStockQuantity(34);
        stockRequestModel.setLowStockAlertAt(10);

        Stock result = systemServiceImpl.createStock(stockRequestModel);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(stockRequestModel.getName());
        assertThat(result.getStockType()).isEqualTo(stockRequestModel.getStockType());
        assertThat(result.getStockQuantity()).isEqualTo(stockRequestModel.getStockQuantity());
        assertThat(result.getLowStockAlertAt()).isEqualTo(stockRequestModel.getLowStockAlertAt());
    }

    @Test
    void createSupplierHistory() {

        supplierRequestModel.setSupplierName("Eggs Supplier");
        supplierRequestModel.setStockName("Eggs");
        supplierRequestModel.setStockType("Eggs Type");
        supplierRequestModel.setStockQuantity(34);
        supplierRequestModel.setEstimatedDeliveryDate(new Date());
        supplierRequestModel.setCostOfStock(23.42);

        Supplier result = systemServiceImpl.createSupplierHistory(supplierRequestModel);

        assertThat(result).isNotNull();
        assertThat(result.getSupplierName()).isEqualTo(supplierRequestModel.getSupplierName());
        assertThat(result.getStockName()).isEqualTo(supplierRequestModel.getStockName());
        assertThat(result.getStockType()).isEqualTo(supplierRequestModel.getStockType());
        assertThat(result.getStockQuantity()).isEqualTo(supplierRequestModel.getStockQuantity());
        assertThat(result.getEstimatedDeliveryDate()).isEqualTo(supplierRequestModel.getEstimatedDeliveryDate());
        assertThat(result.getCostOfStock()).isEqualTo(supplierRequestModel.getCostOfStock());
    }

    @Test
    void registerPaymentDetails() {

        paymentRequestModel.setCardNumber("1223313297639167");
        paymentRequestModel.setCardholderName("John");
        paymentRequestModel.setExpiryDate("02/2035");
        paymentRequestModel.setSecurityCode("124");
        paymentRequestModel.setBillingAddress("Address 12345, Testing");

        Payment result = systemServiceImpl.registerPaymentDetails(paymentRequestModel);

        assertThat(result).isNotNull();
        assertThat(result.getCardNumber()).isEqualTo(paymentRequestModel.getCardNumber());
        assertThat(result.getCardholderName()).isEqualTo(paymentRequestModel.getCardholderName());
        assertThat(result.getExpiryDate()).isEqualTo(paymentRequestModel.getExpiryDate());
        assertThat(result.getSecurityCode()).isEqualTo(paymentRequestModel.getSecurityCode());
        assertThat(result.getBillingAddress()).isEqualTo(paymentRequestModel.getBillingAddress());
    }
}