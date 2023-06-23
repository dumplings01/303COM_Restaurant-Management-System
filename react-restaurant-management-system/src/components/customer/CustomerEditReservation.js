import React, { useState, useEffect } from 'react';
import axios from 'axios';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import DateTimePicker from 'react-datetime-picker/';
import NavBarCustomer from "../NavBarCustomer";
import { useParams, useNavigate } from 'react-router-dom';

function CustomerEditReservation() {

    let {reservationId} = useParams();
    let navigate = useNavigate();

    useEffect(() => {
        
        const currentUser = sessionStorage.getItem("currentUser");
        const userId = sessionStorage.getItem("userId");
        const userName = sessionStorage.getItem("userName");
        const userContact = sessionStorage.getItem("userContact");
        const currentUserType = sessionStorage.getItem("currentUserType");

        if(currentUser == null||currentUserType === "admin"){
            alert("Please login to access!");
            window.location.assign("/login");
        } else {
            setCustomerId(userId);
            setName(userName);
            setContactNumber(userContact);
        }
    }, []);

    const [customerId, setCustomerId] = useState([]);
    const [name, setName] = useState([]);
    const [contactNumber, setContactNumber] = useState([]);
    const [reservationDate, setReservationDate] = useState("");
    const [numberOfPeople, setNumberOfPeople] = useState([]);
    const [remarks, setRemarks] = useState("");

    useEffect(() => {

        const getReservation = async () => {

            try {

                const response = await axios.get(`http://127.0.0.1:8080/reservation/getSingleReservation?reservationId=${reservationId}`);
                //console.log(response);
                setReservationDate(response.data.reservationDate);
                setNumberOfPeople(response.data.numberOfPeople);
                setRemarks(response.data.remarks);
                
            } catch (e) {
                console.log(e);
            }
        };
        getReservation();
    },[reservationId]);

    function handleSubmit(e) {
        e.preventDefault();

        axios.put(`http://127.0.0.1:8080/reservation/updateReservation?reservationId=${reservationId}`,
                    {
                    customerId,
                    customerName: name,
                    customerContact: contactNumber,
                    reservationDate,
                    numberOfPeople,
                    customerRemarks: remarks})
                    .then((res) => {
                        console.log(res);
                        if (res.status === 200) {
                            alert("Reservation updated successfully!");
                            navigate("/customerViewReservation")
                            return res;
                        }
                    }).catch((error) => {
                        console.log(error);
                        alert("Failed to update reservation!");
                    })
                    
    }
        
    return (
        <>
        <NavBarCustomer />

        <h2 className="text-center mt-4 mb-4">Update Reservation</h2>
        <div className="m-auto mt-3 mb-3 col-6 border rounded border-dark">

            <Form onSubmit={handleSubmit}>
            <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Customer ID: </Form.Label>
                    <Form.Control type="text" value={customerId} disabled />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Name: </Form.Label>
                    <Form.Control type="text" value={name} disabled />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Contact Number: </Form.Label>
                    <Form.Control type="text" value={contactNumber} disabled />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label className="pe-4">Date of Reservation: </Form.Label>
                    <DateTimePicker className="border p-3 w-100 rounded-2" required name="reservationDate" format="dd-MM-yyyy h:mm aaaa"
                    minDate={new Date()} onChange={setReservationDate} value={reservationDate} />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Number of People: </Form.Label>
                    <Form.Control type="number" min="1" max="20" value={numberOfPeople}
                    onChange={(event) => setNumberOfPeople(event.target.value)} placeholder="Enter number of people"
                    required/>
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Reservation Remarks: </Form.Label>
                    <Form.Control type="text" onChange={(event) => setRemarks(event.target.value)}
                    value={remarks} placeholder="Enter remarks" />
                </Form.Group>

                <div className="text-center p-4">
                    <Button variant="primary" type="submit" className="me-1">Submit</Button>
                </div>
            </Form>
        </div>
        </>
    )
}

export default CustomerEditReservation;