package com.fyp.restaurant.service;

import com.fyp.restaurant.model.*;

import java.util.List;

public interface SystemServiceImpl {

    Admin createStaffAccount (AdminRequestModel adminDetails);

    Customer createCustomerAccount (CustomerRequestModel customerDetails);

    Reservation createReservation (ReservationRequestModel reservationDetails);

    Stock createStock(StockRequestModel stockDetails);

    Supplier createSupplierHistory (SupplierRequestModel supplierDetails);

    void createSlots (List<ReservationSlots> slots);

    Payment registerPaymentDetails (PaymentRequestModel payment);

}
