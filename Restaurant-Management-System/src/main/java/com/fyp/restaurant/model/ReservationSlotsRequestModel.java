package com.fyp.restaurant.model;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

public class ReservationSlotsRequestModel {

    @NotNull
    private Date date;

    @NotNull
    private String time;

    @NotNull
    private String status;

    private UUID reservationId;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }
}
