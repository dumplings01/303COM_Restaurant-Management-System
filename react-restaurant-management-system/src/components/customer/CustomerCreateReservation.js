import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import 'react-datetime-picker/dist/DateTimePicker.css';
import 'react-calendar/dist/Calendar.css';
import 'react-clock/dist/Clock.css';
import NavBarCustomer from '../NavBarCustomer';

function CustomerCreateReservation() {

    let navigate = useNavigate();

    const [customerId, setCustomerId] = useState([]);
    const [name, setName] = useState([]);
    const [contactNumber, setContactNumber] = useState([]);
    const [reservationDate, setReservationDate] = useState("");
    const [numberOfPeople, setNumberOfPeople] = useState([]);
    const [remarks, setRemarks] = useState("");

    const [slots, setSlots] = useState([]);
    const [selectedSlotId, setSelectedSlotId] = useState([]);

    useEffect(() => {
        const currentUser = sessionStorage.getItem("currentUser");
        const userId = sessionStorage.getItem("userId");
        const userName = sessionStorage.getItem("userName");
        const userContact = sessionStorage.getItem("userContact")
        const currentUserType = sessionStorage.getItem("currentUserType");
        if(currentUser == null||currentUserType === "admin"){
            alert("Please login to access!");
            window.location.assign("/login");
        } else {
            setCustomerId(userId);
            setName(userName);
            setContactNumber(userContact);
        }

        const getSlots = async () => {
            try {
                const response = await axios.get(`http://127.0.0.1:8080/slots/getAllSlots`);
                setSlots(response.data.filter(dateTime => dateTime.status === "Available"));
            } catch (e) {
                console.log(e);
            }
        };
        getSlots();
    }, []);


    function handleSubmit(e) {
        e.preventDefault();

        axios.post(`http://127.0.0.1:8080/reservation/createReservation`,
                    {
                    customerId,
                    customerName: name,
                    customerContact: contactNumber,
                    reservationDate,
                    numberOfPeople,
                    customerRemarks: remarks})
                    .then((res) => {
                        console.log(res);
                        let reservationId = res.data.reservationId;
                        if (res.status === 200) {

                            axios.put(`http://127.0.0.1:8080/slots/updateSlot?slotId=${selectedSlotId}`,
                                {
                                    status: "Taken",
                                    reservationId
                                })
                                .then((res) => {
                                    console.log(res);
                                    if (res.status === 200) {
                                        return res;
                                    }
                                }).catch((error) => {
                                    console.log(error);
                                })

                            alert("Reservation created successfully!");
                            navigate("/customerViewReservation")
                            return res;
                        }
                    }).catch((error) => {
                        console.log(error);
                        alert("Failed to create reservation!\n"+error.response.data);
                    })
    }

    const handleSelect = (event) => {
        const selectedValue = event.target.value;
        const [selectedDate, selectedTime, selectedSlotId] = selectedValue.split(' ');
        setReservationDate(selectedDate+" "+selectedTime);
    
        setSelectedSlotId(selectedSlotId);
      };

    return (
        <>
        <NavBarCustomer />
        <p className="text-center fs-6 p-4 text-danger fw-bold">Note: Each customer can only have maximum two(2) ongoing and future reservations!</p>
        <h2 className="text-center mt-4 mb-4">Create Reservation</h2>
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
                    <select onChange={handleSelect} className='mx-auto'>
                        {slots.map((dateTime, index) => (
                            <option key={index} value={`${dateTime.date} ${dateTime.time} ${dateTime.slotId}`}>
                            {`${dateTime.date} ${dateTime.time}`}
                            </option>
                        ))}
                    </select>
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Number of People: </Form.Label>
                    <Form.Control type="number" min="1" max="20" onChange={(event) => setNumberOfPeople(event.target.value)} placeholder="Enter number of people"
                    required/>
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Reservation Remarks: </Form.Label>
                    <Form.Control type="text" onChange={(event) => setRemarks(event.target.value)} placeholder="Enter remarks" />
                </Form.Group>

                <div className="text-center p-4">
                    <Button variant="primary" type="submit" className="me-1">Submit</Button>
                </div>
            </Form>
        </div>
        </>
    )
}

export default CustomerCreateReservation;