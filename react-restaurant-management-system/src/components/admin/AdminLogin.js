import React, { useState } from 'react';
import { Routes, Route, useNavigate } from 'react-router-dom';
import axios from 'axios';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
// import "../styles/styles.css";

import CustomerLogin from '../customer/CustomerLogin';
import Dashboard from './AdminDashboard';

function AdminLogin() {
    
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    let navigate = useNavigate();

    const navigateCustomerLogin = () => {
        navigate('/customerLogin');
    }

    function handleSubmit(e) {
        console.log(username + " " + password)
        e.preventDefault();

        const getLogin = async () => {

            try {
                const result = await axios.post("http://127.0.0.1:8080/admin/login",
                                                { 
                                                    username,
                                                    password 
                                                })
                                                .then((res) => {
                                                    // then shows result from axios call when data is obtained succesfully
                                                    console.log(res);
                                                    if (res.status === 200){
                                                        alert("Login successful!");
                                                        return res;
                                                        // then store result (username) to session storage
                                                    }
                                                    
                                                }).catch((error) => {
                                                    // catch shows the error from axios call when data is failed to obtain
                                                    alert("Username or password is incorrect!");
                                                });
                if (result!=null){
                    var currentUser = sessionStorage.getItem("currentUser");
                    if (currentUser == null){
                        currentUser = username;
                    }
                    sessionStorage.setItem("currentUser", currentUser);
                    
                    
                    //navigate("/getList");


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
                    <Form.Label>Username: </Form.Label>
                    <Form.Control type="text" name="username" placeholder="Enter username" required onChange={
                        (event) => {setUsername(event.target.value)}
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

                <div className="text-center pb-4">
                    <a href onClick={navigateCustomerLogin} className="hover-text">Customer Login</a>
                </div>

            </Form>

            <Routes>
                <Route path="/CustomerLogin" element={<CustomerLogin />} />
                <Route path="/dashboard" element={<Dashboard />} />
            </Routes>
        </div>
        </>
    )
}

export default AdminLogin;