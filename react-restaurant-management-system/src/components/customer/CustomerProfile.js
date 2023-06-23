import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {useNavigate} from 'react-router-dom';

import NavBarCustomer from "../NavBarCustomer";

import Button from 'react-bootstrap/Button';

function CustomerProfile() {
    
    let navigate = useNavigate();

    const [customerId, setCustomerId] = useState("");
    const [customerName, setCustomerName] = useState("");
    const [email, setEmail] = useState("");
    const [contactNumber, setContactNumber] = useState("");
    const [loyaltyPoints, setLoyaltyPoints] = useState("");

    useEffect(() => {

        const currentUser = sessionStorage.getItem("currentUser");
        const currentUserType = sessionStorage.getItem("currentUserType");
        if(currentUser == null||currentUserType === "admin"){
            alert("Please login to access!");
            window.location.assign("/login");
        } else {
            setEmail(currentUser);
        }

        const getProfile = async () => {

            try {
                const response = await axios.get(`http://127.0.0.1:8080/customer/getCustomerProfile?email=${sessionStorage.getItem("currentUser")}`);
                
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
		let answer = window.confirm("Deactivate account for "+customerName+"?\n\nAll reservations created by this account will be cancelled with no refunds!");
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

    function handleEdit() {
		navigate("/customerEditProfile");
	};

    return (
        <>
        <NavBarCustomer />

        <div className="w-75 h-75 mx-auto">
            <div className="card m-5" style={{height:'80%'}}>
                <div className="card-body">
                    <h5 className="card-title h3 text-center pb-4">My Profile</h5>
                    <h6 className="card-subtitle mb-2 text-muted p-2">{customerName}</h6>
                    <p className="card-text pb-5 d-flex flex-column">
                        <span className="p-2"><b>Email: </b>{email}</span>
                        <span className="p-2"><b>Contact number: </b>{contactNumber}</span>
                        <span className="p-2"><b>Loyalty Points: </b>{loyaltyPoints}</span>
                    </p>
                    <div className="d-flex justify-content-between">
                        <>
                        <Button variant="primary" className="w-50 me-2 ms-2" onClick={handleEdit}>Edit Profile</Button>
                        <Button variant="danger" className="w-50" onClick={handleDeactivate}>Deactivate Account?</Button>
                        </>
                    </div>
                </div>
            </div>
        </div>
		</>
    )
}

export default CustomerProfile;