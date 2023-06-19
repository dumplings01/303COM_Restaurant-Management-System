import React, { useState, useEffect } from 'react';
import axios from 'axios';

import NavBar from '../NavBar';
import AdminReservationCard from './AdminReservationCard';

import Button from 'react-bootstrap/Button';

function AdminReservation() {
    const [reservations, setReservations] = useState([]);
    const [totalPage, setTotalPage] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);

    useEffect(() => {
        const currentUser = sessionStorage.getItem("currentUser");
        if(currentUser == null){
            alert("Please login to access!");
            window.location.assign("/login");
        }
    }, []);

    useEffect(() => {
        const getAllReservation = async () => {

            try {
                const response = await axios.get(`http://127.0.0.1:8080/reservation/getAllReservation?currentPage=${currentPage}`);
                console.log(response);
                setReservations(response.data.content);
                setTotalPage(response.data.totalPages);
            } catch (e) {
                console.log(e);
            }
        };
        getAllReservation();
    }, [currentPage]);

    const handlePrev = async(e) => {
        if (currentPage > 0) {
            await setCurrentPage(parseInt(currentPage-1,10));
        }
        console.log(currentPage);
    }

    const handleNext = async(e) => {
        console.log(currentPage);
        if (currentPage < totalPage-1) {
            await setCurrentPage(parseInt(currentPage+1,10));
        }
        console.log(currentPage);
    }

    // render profiles list here
    const renderedReservations = reservations.map((reservationDetails, index) => {
        //console.log(reservationDetails)
        return (
            <AdminReservationCard
                key={index}
                reservation = {reservationDetails}
            />)
    })


    return (
        <>
        <NavBar />
        <div>
            {renderedReservations.length === 0 ? (
                <div>
                    <p className="text-center fs-2">There is no reservations right now</p>
                </div>
            ):(
                renderedReservations
            )}
            
        </div>
        <div className="d-flex justify-content-between mt-4 mb-4 ms-4 me-4">
            {currentPage > 0 ?
            <Button className="w-25 me-2 ms-2" onClick={handlePrev}>Prev</Button>
            :
            <Button className="w-25 me-2 ms-2" onClick={handlePrev} disabled>Prev</Button>}
            <span className="w-10 me-2 ms-2" >{currentPage+1}/{totalPage}</span>
            {currentPage < (totalPage-1) ?
            <Button className="w-25 me-2 ms-2" onClick={handleNext}>Next</Button>
            :
            <Button className="w-25 me-2 ms-2" onClick={handleNext} disabled>Next</Button>}
        
        </div>
        </>
    );
}

export default AdminReservation;