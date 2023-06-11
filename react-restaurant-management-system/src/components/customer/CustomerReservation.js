import React, { useState } from 'react';
import axios from 'axios';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
//import ListGroup from 'react-bootstrap/ListGroup';
import DateTimePicker from 'react-datetime-picker/';

function CustomerReservation() {

    // useEffect(() => {
    //     var currentUser = sessionStorage.getItem("currentUser");
    //     if(currentUser == null){
    //         alert("Please login to access!");
    //         window.location.assign("/login");
    //     }
    //     setCreatedBy(currentUser);
    // }, []);

    const [name, setName] = useState([]);
    const [contactNumber, setContactNumber] = useState([]);
    const [reservationDate, setReservationDate] = useState(new Date());
    const [numberOfPeople, setNumberOfPeople] = useState([]);
    const [remarks, setRemarks] = useState([]);

    function handleReservationDateChange(value){
        const currentDate = new Date();
        if(value.getTime() > currentDate.getTime()){
            setReservationDate(value);
        }
    }

    function handleSubmit(e) {
        e.preventDefault();
        axios.post(`http://127.0.0.1:8080/reservation/createReservation`,
                    {//customerId,
                    customerName: name,
                    customerContact: contactNumber,
                    reservationDate,
                    numberOfPeople,
                    customerRemarks: remarks})
                    .then((res) => {
                        console.log(res);
                    });
                    alert("Reservation created successfully!");
    }

    return (
        <>
        <h2 className="text-center mt-4 mb-4">Create Reservation</h2>
        <div className="m-auto mt-3 mb-3 col-6 border rounded border-dark">
            <Form>
                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Name: </Form.Label>
                    <Form.Control type="text" onChange={setName} placeholder="Enter name" required />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Contact Number: </Form.Label>
                    <Form.Control type="text" onChange={setContactNumber} placeholder="Enter contact number" required />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label className="pe-4">Date of Reservation: </Form.Label>
                    <DateTimePicker className="border p-3 w-100 rounded-2" name="reservationDate" format="dd-MM-yyyy h:mm aaaa"
                    minDate={new Date()} onChange={handleReservationDateChange} value={new Date(reservationDate)} required />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Number of People: </Form.Label>
                    <Form.Control type="number" onChange={setNumberOfPeople} placeholder="Enter number of people"
                    required/>
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Reservation Remarks: </Form.Label>
                    <Form.Control type="textarea" onChange={setRemarks} placeholder="Enter remarks" required />
                </Form.Group>

                <div className="text-center p-4">
                    <Button variant="primary" type="submit" onClick={handleSubmit} className="me-1">Submit</Button>
                </div>
            </Form>
        </div>
        </>
    )
}

export default CustomerReservation;