import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import NavBarCustomer from "../NavBarCustomer";

function CustomerEditProfile() {

    let navigate = useNavigate();

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [contactNumber, setContactNumber] = useState("");
    const [password, setPassword] = useState("");

    useEffect(() => {

        const currentUser = sessionStorage.getItem("currentUser");
        const currentUserType = sessionStorage.getItem("currentUserType");
        if(currentUser == null||currentUserType === "admin"){
            alert("Please login to access!");
            window.location.assign("/login");
        } else {
            setEmail(currentUser);
        }

        setName(sessionStorage.getItem("userName"));
        setEmail(sessionStorage.getItem("currentUser"));
        setContactNumber(sessionStorage.getItem("userContact"));

    },[]);

    function handleSubmit(e) {

        e.preventDefault();
        axios.put(`http://127.0.0.1:8080/customer/updateCustomerProfile?customerId=${sessionStorage.getItem("userId")}`,
                    {
                        name,
                        email,
                        contactNumber,
                        password
                    })
                    .then((res) => {
                        console.log(res);
                        if (res.status === 200) {
                            alert("Profile updated successfully!");
                            sessionStorage.setItem("userName", name);
                            sessionStorage.getItem("userContact", contactNumber);
                            navigate("/customerProfile");
                            return res;
                        }
                    }).catch((error) => {
                        console.log(error);
                        alert("Failed to update profile!");
                    })
                }
        console.log()
    return (
        <>
        <NavBarCustomer />
        <h2 className="text-center mt-4 mb-4">Edit Profile</h2>
        <div className="m-auto mt-3 mb-3 col-6 border rounded border-dark">
            <Form onSubmit={handleSubmit}>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Email: </Form.Label>
                    <Form.Control type="email" name="email" value={email} disabled />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Name: </Form.Label>
                    <Form.Control type="text" name="name" value={name} placeholder="Enter name" required onChange={
                        (event) => {setName(event.target.value)}
                    } />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Contact number: </Form.Label>
                    <Form.Control type="text" name="contactNumber" value={contactNumber}  placeholder="Enter contact number"
                    onChange={(event) => setContactNumber(event.target.value)} pattern="(01\d-\d{7}$)" maxLength="11" required />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Confirm password: </Form.Label>
                    <Form.Control type="password" name="password" placeholder="Enter password" minLength="8" required onChange={
                        (event) => {setPassword(event.target.value)}
                    } />
                </Form.Group>

                <div className="text-center p-4">
                    <Button variant="primary" type="submit" className="me-1">Confirm Changes</Button>
                </div>

                <div className="text-center pb-4">
                    <a href="customerProfile" className="hover-text">Cancel</a>
                </div>

            </Form>
        </div>
        </>
    )
}

export default CustomerEditProfile;