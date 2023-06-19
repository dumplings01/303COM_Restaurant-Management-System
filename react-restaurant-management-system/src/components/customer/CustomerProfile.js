import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {useNavigate} from 'react-router-dom';

import NavBarCustomer from "../NavBarCustomer";

import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';

function CustomerProfile() {

    useEffect(() => {
        const currentUser = sessionStorage.getItem("currentUser");
        if(currentUser == null){
            alert("Please login to access!");
            window.location.assign("/login");
        } else {
            setEmail(currentUser);
        }
        // console.log(sessionStorage.getItem("currentUser"))
    }, []);

    const [customerId, setCustomerId] = useState("");
    const [customerName, setCustomerName] = useState("");
    const [email, setEmail] = useState("");
    const [contactNumber, setContactNumber] = useState("");
    const [loyaltyPoints, setLoyaltyPoints] = useState("");

    useEffect(() => {
        const getProfile = async () => {

            try {
                const response = await axios.get(`http://127.0.0.1:8080/customer/getCustomerProfile?email=${email}`);
                
                setCustomerId(response.data.customerId);
                setCustomerName(response.data.name);
                setContactNumber(response.data.contactNumber);
                setLoyaltyPoints(response.data.loyaltyPoints);
                
                sessionStorage.setItem("userId", response.data.customerId);
                sessionStorage.setItem("userName", response.data.name);
                sessionStorage.setItem("userContact", response.data.contactNumber);
            } catch (e) {
                console.log(e);
            }
        };
        getProfile();
    },[email]);

    function handleDeactivate(e) {
		let answer = window.confirm("Delete profile of "+customerName+"?");
		if (answer) {
			axios.delete(`http://127.0.0.1:8080/customer/deleteCustomerProfile?customerId=${customerId}`)
						.then((res) => {
							console.log(res);
							if (res.status === 200) {
								alert("Profile deleted successfully!");
                                window.location.assign("/logout");
								return res;
							}
						}).catch((error) => {
							console.log(error);
							alert("Failed to delete profile!");
							
						})
					};
					
		}


    const navigate = useNavigate();

    function handleEdit() {
		navigate(`/customerEditProfile/${email}`);
	};

    return (
        <>
        <NavBarCustomer />

        <div className="w-75 h-75 mx-auto">
        <Card className="m-5">
		<Card.Header as="h3">My Profile</Card.Header>
		<Card.Body>
			<Card.Title as="h4">Customer Name: {customerName}</Card.Title>
			<Card.Text>
				<>
					<span><b>Email: </b>{email}</span><br></br>
					<span><b>Contact number: </b>{contactNumber}</span><br></br>
					<span><b>Loyalty Points: </b>{loyaltyPoints}</span><br></br>
				</>
			</Card.Text>
			<div className="d-flex justify-content-between">
				<Button variant="primary" className="w-50 me-2 ms-2" onClick={() => handleEdit(email)}>Edit</Button>
				<Button variant="danger" className="w-50" onClick={handleDeactivate}>Deactivate Account?</Button>
			</div>
		</Card.Body>
		</Card>
        </div>
		</>
    )
}

export default CustomerProfile;