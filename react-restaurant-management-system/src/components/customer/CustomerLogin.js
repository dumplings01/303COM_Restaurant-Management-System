import React, { useState } from 'react';
import { Routes, Route, useNavigate } from 'react-router-dom';
import axios from 'axios';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
// import "../styles/styles.css";

import CustomerRegister from './CustomerRegister';
import Reservation from './CustomerReservation';
import AdminLogin from '../admin/AdminLogin';

function CustomerLogin() {
    
    const [email, setEmail ] = useState("");
    const [password, setPassword] = useState("");

    let navigate = useNavigate();

    const navigateRegister = () => {
        navigate('/customerRegister');
    }

    const navigateAdminLogin = () => {
        navigate('/adminLogin');
    }

    function handleSubmit(e) {
        console.log(email + " " + password)
        e.preventDefault();

        const getLogin = async () => {

            try {
                const result = await axios.post("http://127.0.0.1:8080/customer/login",
                                                { 
                                                    email,
                                                    password 
                                                })
                                                .then((res) => {
                                                    // then shows result from axios call when data is obtained succesfully
                                                    console.log(res);
                                                    if (res.status === 200){
                                                        alert("Login successful!");
                                                        return res;
                                                        // then store result (email) to session storage
                                                    }
                                                    
                                                }).catch((error) => {
                                                    // catch shows the error from axios call when data is failed to obtain
                                                    alert("Email or password is incorrect!");
                                                });
                if (result!=null){
                    var currentUser = sessionStorage.getItem("currentUser");
                    if (currentUser == null){
                        currentUser = email;
                    }
                    sessionStorage.setItem("currentUser", currentUser);
                    sessionStorage.setItem("currentUserType", "customer");
                    
                    
                    navigate("/reservation");


                }

            } catch (e) {
                console.log(e);
            }
        };
        getLogin();
    }

    return (
        <>
        <h2 className="text-center mt-4 mb-4">Login</h2>
        <div className="m-auto mt-3 mb-3 col-6 border rounded border-dark">
            <Form onSubmit={handleSubmit}>
                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Email: </Form.Label>
                    <Form.Control type="email" name="email" placeholder="Enter email" required onChange={
                        (event) => {setEmail(event.target.value)}
                    } />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Password: </Form.Label>
                    <Form.Control type="password" name="password" placeholder="Enter password" required onChange={
                        (event) => {setPassword(event.target.value)}
                    } />
                </Form.Group>

                <div className="text-center p-4">
                    <Button variant="primary" type="submit" className="me-1">Login</Button>
                </div>

                <div className="d-flex flex-column text-center pb-4">
                    <a href onClick={navigateRegister} className="hover-text">Register New User?</a>
                    <a href onClick={navigateAdminLogin} className="hover-text">Admin Login</a>
                </div>

            </Form>

            

            <Routes>
                <Route path="/customerRegister" element={<CustomerRegister />} />
                <Route path="/adminLogin" element={<AdminLogin />} />
                <Route path="/reservation" element={<Reservation />} />
            </Routes>
        </div>
        </>
    )
}

export default CustomerLogin;