import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';

import NavBar from '../NavBar';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

function AdminUpdateReservation() {

    let {reservationId} = useParams();
    let navigate = useNavigate();

    const [customerId, setCustomerId] = useState("");
    const [customerName, setCustomerName] = useState("");
    const [customerContact, setCustomerContact] = useState("");
    const [reservationDate, setReservationDate] = useState("");
    const [numberOfPeople, setNumberOfPeople] = useState("");
    const [customerRemarks, setCustomerRemarks] = useState("");
    const [createdAt, setCreatedAt] = useState("");
    const [status, setStatus] = useState("");

    useEffect(() => {
        var currentUserType = sessionStorage.getItem("currentUserType");
		var adminRoles = sessionStorage.getItem("adminRoles");
		const adminAccess = adminRoles.includes('owner');
		const reservationAccess = adminRoles.includes('manage reservations');
        
        if(currentUserType == null || currentUserType === "customer"){
            window.location.assign("/");
            alert("UNAUTHORIZED!");
            
        } else if (!adminAccess && !reservationAccess) {
            window.location.assign("/dashboard");
			alert("No access to manage reservations!");
		}
    }, []);
    
    useEffect(() => {
        const getReservationDetails = async () => {
            try {
                const response = await axios.get(`http://127.0.0.1:8080/reservation/getSingleReservation?reservationId=${reservationId}`);
                //console.log(response);
                setCustomerId(response.data.customerId);
                setCustomerName(response.data.customerName);
                setCustomerContact(response.data.customerContact);
                setReservationDate(response.data.reservationDate);
                setNumberOfPeople(response.data.numberOfPeople);
                setCustomerRemarks(response.data.customerRemarks);
                setCreatedAt(response.data.createdAt);
                setStatus(response.data.status);
                
            } catch (e) {
                console.log(e);
            }
        };
        getReservationDetails();
    },[reservationId]);

    function handleSubmit(e, value){
        e.preventDefault();

        axios.put(`http://127.0.0.1:8080/reservation/updateReservation?reservationId=${reservationId}`,
        {
            customerId,
            customerName,
            customerContact,
            reservationDate,
            numberOfPeople,
            customerRemarks,
            status
        })
        .then((res) => {
            console.log(res);
            if (res.status === 200) {
                alert("Reservation status updated successfully!");
                navigate("/adminReservationList");
                return res;
            }
        }).catch((error) => {
            console.log(error);
            alert("Failed to update reservation status!\n"+error.response.data);
        })
        
    }
          
    return (
        <>
        <NavBar />
        <h2 className="text-center mt-4 mb-4">Update Reservation Status</h2>
		<div className="m-auto mt-3 mb-3 col-6 border rounded border-dark">
            <Form onSubmit={handleSubmit}>
                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Reservation ID: </Form.Label>
                    <Form.Control type="text" value={reservationId} disabled />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Customer ID: </Form.Label>
                    <Form.Control type="text" value={customerId} disabled />
                </Form.Group>
                
                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Customer Name: </Form.Label>
                    <Form.Control type="text" value={customerName} disabled />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Customer Contact Number: </Form.Label>
                    <Form.Control type="text" value={customerContact} disabled />
                </Form.Group>
                
                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Reservation Date: </Form.Label>
                    <Form.Control type="text" value={reservationDate} disabled />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Number of People: </Form.Label>
                    <Form.Control type="number" min="1" max="20" value={numberOfPeople} disabled />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Customer Remarks: </Form.Label>
                    <Form.Control type="text" value={customerRemarks} disabled />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Created At: </Form.Label>
                    <Form.Control type="text" value={createdAt} disabled />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Status: </Form.Label>
                    <Form.Select value={status} onChange={(e) => {setStatus(e.target.value)}} required>
                        <option value="Deposit Pending">Deposit Pending</option>
                        <option value="Deposit Paid">Deposit Paid</option>
                        <option value="Reservation Completed">Reservation Completed</option>
                    </Form.Select>
                </Form.Group>

                <div className="text-center p-4">
                    <a className="me-1 btn btn-secondary" href="/adminReservationList" role="button">Back</a>
                    <Button variant="primary" type="submit" className="me-1">Update</Button>
                </div>
            </Form>
        </div>
        </>
    )
}

export default AdminUpdateReservation;