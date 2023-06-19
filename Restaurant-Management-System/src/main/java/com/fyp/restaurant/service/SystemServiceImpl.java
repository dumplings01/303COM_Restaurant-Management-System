package com.fyp.restaurant.service;

import com.fyp.restaurant.model.*;

public interface SystemServiceImpl {

    Admin createStaffAccount (AdminRequestModel adminDetails);

    Customer createCustomerAccount (CustomerRequestModel customerDetails);

    Reservation createReservation (ReservationRequestModel reservationDetails);

    Stock createStock(StockRequestModel stockDetails);

    Supplier createSupplierHistory (SupplierRequestModel supplierDetails);

}
