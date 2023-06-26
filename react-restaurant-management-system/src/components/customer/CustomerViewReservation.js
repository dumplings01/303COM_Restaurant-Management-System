import React, { useEffect, useState } from 'react';
import axios from 'axios';
import NavBarCustomer from "../NavBarCustomer";

import CustomerReservationCard from './CustomerReservationCard';

function CustomerViewReservation() {

    const [customerId, setCustomerId] = useState([]);
    const [reservations, setReservations] = useState([]);

    useEffect(() => {

        const userId = sessionStorage.getItem("userId");
        const currentUser = sessionStorage.getItem("currentUser");
        const currentUserType = sessionStorage.getItem("currentUserType");
        if(currentUser == null||currentUserType === "admin"){
            alert("Please login to access!");
            window.location.assign("/login");
        } else {
            setCustomerId(userId);
        }

        const getReservations = async () => {

            try {
                const response = await axios.get(`http://127.0.0.1:8080/reservation/getReservationsFromUser?customerId=${sessionStorage.getItem("userId")}`);
                
                setReservations(response.data);
            } catch (e) {
                console.log(e);
            }
        };
        getReservations();

    }, [customerId]);

    const renderedReservations = reservations.map((reservationDetails, index) => {
        //console.log(reservationDetails)
        return (
            <CustomerReservationCard
                key={index}
                reservation = {reservationDetails}
            />)
    })

    console.log(reservations)

    return (
        <>
        <NavBarCustomer />

        <div>
            {renderedReservations.length === 0 ? (
                <div>
                    <p className="text-center fs-2">No reservations yet!</p>
                </div>
            ):(
                <>
                <h2 className="text-center mt-4 mb-2">My Reservations</h2>
                {renderedReservations}
                </>
            )}
            
        </div>

        </>
    )
}

export default CustomerViewReservation;