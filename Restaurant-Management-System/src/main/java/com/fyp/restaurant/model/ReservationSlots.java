package com.fyp.restaurant.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="reservationSlots",
        uniqueConstraints={
                @UniqueConstraint(columnNames = "slotId")
        })
public class ReservationSlots {

        @Id
        @Column(name="slotId", nullable = false, unique=true)
        private UUID slotId;

        @Column(name="date", nullable=false)
        private Date date;

        @Column(name="time", nullable=false)
        private String time;

        @Column(name="status", length = 20, nullable=false)
        private String status;

        @Column(name="reservationId", nullable=true)
        private UUID reservationId;

        public ReservationSlots() {

        }

        public ReservationSlots(UUID slotId, Date date, String time, String status, UUID reservationId) {
                this.slotId = slotId;
                this.date = date;
                this.time = time;
                this.status = status;
                this.reservationId = reservationId;
        }

        @PrePersist
        private void onCreate() {
                slotId = UUID.randomUUID();
                status = "Available";
        }

        public UUID getSlotId() {
                return slotId;
        }

        public void setSlotId(UUID slotId) {
                this.slotId = slotId;
        }

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
