import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';

import NavBarCustomer from "../NavBarCustomer";

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

function CustomerReservationPayment() {

    let {reservationId} = useParams();
    let navigate = useNavigate();

    const [cardNumber, setCardNumber] = useState("");
    const [cardholderName, setCardholderName] = useState("");
    const [expiryDate, setExpiryDate] = useState("");
    const [securityCode, setSecurityCode] = useState("");
    const [billingAddress, setBillingAddress] = useState("");

    useEffect(() => {
        
        const currentUser = sessionStorage.getItem("currentUser");
        const currentUserType = sessionStorage.getItem("currentUserType");

        if(currentUser == null||currentUserType === "admin"){
            alert("Please login to access!");
            window.location.assign("/login");
        }
    }, []);

    function handleSubmit(e) {
        e.preventDefault();
        axios.post(`http://127.0.0.1:8080/payment/registerPaymentDetails?reservationId=${reservationId}`,
                    {
                        cardNumber,
                        cardholderName,
                        expiryDate,
                        securityCode,
                        billingAddress
                    })
                    .then((res) => {
                        console.log(res);
                        if (res.status === 200) {
                            alert("Payment successful!");
                            navigate("/customerViewReservation");
                            return res;
                        }
                    }).catch((error) => {
                        console.log(error);
                    })

        
    }

    return (
        <>
        <NavBarCustomer />
        <div className="m-auto mt-3 mb-3 col-6 border rounded border-dark">
        <h2 className="text-center mt-4 mb-4">Deposit: RM 20</h2>
            <Form onSubmit={handleSubmit}>
                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Card Number: </Form.Label>
                    <Form.Control type="text" name="cardNumber" pattern="[0-9]*" maxLength="16"
                    onChange={(e) => {setCardNumber(e.target.value)}} placeholder='Enter card number' required />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Cardholder Name: </Form.Label>
                    <Form.Control type="text" name="name" onChange={(e) => {setCardholderName(e.target.value)}}
                    placeholder='Enter cardholder name' required />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Expiry Date: </Form.Label>
                    <Form.Control type="text" name="expiryDate" maxLength="7" pattern="(0[1-9]|1[0-2])\/[0-9]{4}"
                    onChange={(e) => {setExpiryDate(e.target.value)}} placeholder='MM/YYYY' required />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Security Code: </Form.Label>
                    <Form.Control type="text" name="securityCode" minLength="3" maxLength="4" pattern="[0-9]{3,4}"
                    onChange={(e) => {setSecurityCode(e.target.value)}} placeholder='Enter security code' required />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Billing Address: </Form.Label>
                    <Form.Control type="textarea" name="billingAddress" onChange={(e) => {setBillingAddress(e.target.value)}}
                    placeholder='Enter billing address' required />
                </Form.Group>

                <div className="text-center p-4">
                    <a className="me-1 btn btn-secondary" href="/customerViewReservation" role="button">Cancel</a>
                    <Button variant="primary" type="submit" className="me-1">Pay Deposit</Button>
                </div>
            </Form>
            </div>
        </>
    )
}

export default CustomerReservationPayment;