import React, { useEffect, useState } from 'react';
import axios from 'axios';
import NavBarCustomer from "../NavBarCustomer";

function CustomerViewReservation() {

    useEffect(() => {
        const userId = sessionStorage.getItem("userId");
        const currentUser = sessionStorage.getItem("currentUser");
        if(currentUser == null){
            alert("Please login to access!");
            window.location.assign("/login");
        } else {
            setCustomerId(userId);
        }
    }, []);

    const [customerId, setCustomerId] = useState([]);
    const [reservations, setReservations] = useState([]);

    useEffect(() => {
        const getReservations = async () => {

            try {
                const response = await axios.get(`http://127.0.0.1:8080/reservation/getReservationsFromUser?customerId=${customerId}`);
                console.log(response);
                setReservations(response.data.content);
            } catch (e) {
                console.log(e);
            }
        };
        getReservations();
    }, [customerId]);

    return (
        <NavBarCustomer />
    )
}

export default CustomerViewReservation;